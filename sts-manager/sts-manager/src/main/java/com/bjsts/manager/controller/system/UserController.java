package com.bjsts.manager.controller.system;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.core.exception.ValidateFailedException;
import com.bjsts.manager.entity.system.UserEntity;
import com.bjsts.manager.entity.user.DepartmentEntity;
import com.bjsts.manager.enums.user.EducationType;
import com.bjsts.manager.enums.user.MaleType;
import com.bjsts.manager.enums.user.PolityType;
import com.bjsts.manager.form.system.UserForm;
import com.bjsts.manager.query.system.UserSearchable;
import com.bjsts.manager.service.system.RoleService;
import com.bjsts.manager.service.system.UserService;
import com.bjsts.manager.service.user.DepartmentService;
import com.bjsts.manager.shiro.authentication.PasswordHelper;
import com.bjsts.manager.validator.user.PasswordChangeValidator;
import com.bjsts.manager.validator.user.PasswordResetValidator;
import com.google.common.collect.Lists;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

/**
 * @author jinsheng
 * @since 2016-04-26 16:56
 */
@Controller
@RequestMapping("/user")
@SessionAttributes("userSearchable")
public class UserController extends AbstractController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordHelper passwordHelper;

    @Autowired
    private PasswordChangeValidator passwordChangeValidator;

    @Autowired
    private PasswordResetValidator passwordResetValidator;

    @Autowired
    private DepartmentService departmentService;

    @ModelAttribute("maleTypeList")
    public List<MaleType> getMaleTypeList() {
        return MaleType.list();
    }

    @ModelAttribute("educationTypeList")
    public List<EducationType> getEducationTypeList() {
        return EducationType.list();
    }

    @ModelAttribute("polityTypeList")
    public List<PolityType> getPolityTypeList() {
        return PolityType.list();
    }

    @RequiresPermissions("sts:user:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(UserSearchable userSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<UserEntity> apiResponse = userService.findAll(userSearchable, pageable);
        Page<UserEntity> page = new PageImpl<>(Lists.newArrayList(apiResponse.getPagedData()), pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        return "system/user/list";
    }

    @RequiresPermissions("sts:user:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute UserForm userForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".userForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(userForm.getUserInfo())) {
            userForm.setUserInfo(new UserEntity());
        }
        List<DepartmentEntity> departmentEntities = departmentService.findAll();
        modelMap.put("departmentList", departmentEntities);
        modelMap.put("action", "create");
        return "system/user/edit";
    }

    @RequiresPermissions("sts:user:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(UserForm userForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(userForm);
            return "redirect:/department/create";
        }
        UserEntity userEntity = userForm.getUserInfo();
        passwordHelper.encryptPassword(userEntity);
        userService.save(userEntity);
        return "result";
    }

    @RequiresPermissions("sts:user:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, @ModelAttribute UserForm userForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".userForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        UserEntity userEntity = userService.get(id);
        if (Objects.isNull(userEntity)) {
            logger.error("修改部门,未查询[id={}]的部门信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }
        userForm.setUserInfo(userEntity);
        List<DepartmentEntity> departmentEntities = departmentService.findAll();
        modelMap.put("departmentList", departmentEntities);
        modelMap.put("action", "update");
        return "system/user/edit";
    }

    @RequiresPermissions("sts:user:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(UserForm userForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(userForm);
            return "redirect:/user/update/" + userForm.getUserInfo().getId();
        }
        UserEntity user = userForm.getUserInfo();
        UserEntity userEntity = userService.get(user.getId());
        userEntity.setRealName(user.getRealName());
        userEntity.setEmail(user.getEmail());
        userEntity.setMobile(user.getMobile());
        userService.update(userEntity);
        return "result";
    }

    @RequiresPermissions("sts:user:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        modelMap.put("userInfo", userService.get(id));
        return "system/user/view";
    }

    @RequiresPermissions("sts:user:changePassword")
    @RequestMapping(value = "/password/change", method = RequestMethod.GET)
    public String changePassword(@ModelAttribute UserForm userForm, ModelMap modelMap) {
        UserEntity userInfo = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        if (userInfo == null) {
            return "redirect:/signin";
        }
        Long userId = userInfo.getId();
        userInfo = userService.get(userId);
        if (userInfo == null) {
            logger.error("修改密码失败：未查询到id={}的用户", userId);
            return "redirect:/signin";
        }

        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".userForm"), modelMap.get(BINDING_RESULT_KEY));
        }

        userForm.setUserInfo(userInfo);
        return "system/user/passwordChange";
    }

    @RequiresPermissions("sts:user:changePassword")
    @RequestMapping(value = "/password/change", method = RequestMethod.POST)
    public String changePassword(UserForm userForm, BindingResult result, RedirectAttributes redirectAttributes) {
        try {
            passwordChangeValidator.validate(userForm);
        } catch (ValidateFailedException e) {
            logger.error(e.getMessage(), e);
            result.addError(new ObjectError(e.getField(), e.getMessage()));
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(userForm);
            return "redirect:/user/password/change";
        }
        UserEntity currentUser = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        currentUser.setPassword(userForm.getNewPassword());
        passwordHelper.encryptPassword(currentUser);
        userService.update(currentUser);

        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, "修改密码成功！");
        return "result";
    }

    @RequiresPermissions("sts:user:role")
    @RequestMapping(value = "/role/allot/{id}", method = RequestMethod.GET)
    public String allotRole(@PathVariable Long id, @ModelAttribute UserForm userForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".userForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        userForm.setUserInfo(userService.get(id));
        userForm.setRoleIdList(userService.findRoleIdByUserId(id));
        modelMap.addAttribute("roles", roleService.findAll());
        return "system/user/allot";
    }

    @RequiresPermissions("sts:user:role")
    @RequestMapping(value = "/role/allot", method = RequestMethod.POST)
    public String allotRole(UserForm userForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(userForm);
            return "redirect:/user/role/allot/" + userForm.getUserInfo().getId();
        }
        userService.bindRole(userForm.getUserInfo().getId(), userForm.getRoleIdList());
        return "result";
    }

    @RequiresPermissions("sts:user:resetPassword")
    @RequestMapping(value = "/password/reset", method = RequestMethod.GET)
    public String resetPassword(@ModelAttribute UserForm userForm, ModelMap modelMap) {
        if (Objects.isNull(userForm) || Objects.isNull(userForm.getUserInfo()) || Objects.isNull(userForm.getUserInfo().getId())) {
            return "redirect:/user/list";
        }
        UserEntity userInfo = userService.get(userForm.getUserInfo().getId());
        if (userInfo == null) {
            logger.error("重置密码失败：未查询到id={}的用户", userForm.getUserInfo().getId());
            return "redirect:/user/list";
        }

        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".userForm"), modelMap.get(BINDING_RESULT_KEY));
        }

        userForm.setUserInfo(userInfo);
        return "system/user/passwordReset";
    }

    @RequiresPermissions("sts:user:resetPassword")
    @RequestMapping(value = "/password/reset", method = RequestMethod.POST)
    public String resetPassword(UserForm userForm, BindingResult result, RedirectAttributes redirectAttributes) {
        try {
            passwordResetValidator.validate(userForm);
        } catch (ValidateFailedException e) {
            logger.error(e.getMessage(), e);
            result.addError(new ObjectError(e.getField(), e.getMessage()));
        }

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(userForm);
            return "redirect:/user/password/reset";
        }
        UserEntity userInfo = userService.get(userForm.getUserInfo().getId());
        userInfo.setPassword(userForm.getNewPassword());
        passwordHelper.encryptPassword(userInfo);
        userService.update(userInfo);

        redirectAttributes.addFlashAttribute(SUCCESS_MESSAGE_KEY, "重置密码成功！");
        return "result";
    }

    @RequiresPermissions("sts:user:disable")
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public String disable(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        UserEntity userEntity = userService.get(id);
        if (Objects.isNull(userEntity)) {
            logger.error("删除项目信息,未查询[id={}]的用户信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "未查询[id={"+id+"}]的用户信息!");
            return "redirect:/error";
        }
        userEntity.setValid(EnableDisableStatus.DISABLE);
        userService.update(userEntity);
        return "redirect:/user/list";
    }
}
