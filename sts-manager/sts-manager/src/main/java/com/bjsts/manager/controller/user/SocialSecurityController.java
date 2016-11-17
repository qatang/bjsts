package com.bjsts.manager.controller.user;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.user.SocialSecurityEntity;
import com.bjsts.manager.entity.user.UserEntity;
import com.bjsts.manager.form.user.SocialSecurityForm;
import com.bjsts.manager.query.user.SocialSecuritySearchable;
import com.bjsts.manager.service.user.DepartmentService;
import com.bjsts.manager.service.user.SocialSecurityService;
import com.bjsts.manager.service.user.UserService;
import com.google.common.collect.Lists;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

/**
 * 考勤
 * @author wangzhiliang
 */
@Controller
@RequestMapping("/socialSecurity")
@SessionAttributes("socialSecuritySearchable")
public class SocialSecurityController extends AbstractController {

    @Autowired
    private SocialSecurityService socialSecurityService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UserService userService;

    @RequiresPermissions("sts:social_security:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(SocialSecuritySearchable socialSecuritySearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<SocialSecurityEntity> apiResponse = socialSecurityService.findAll(socialSecuritySearchable, pageable);
        Page<SocialSecurityEntity> page = new PageImpl<>(Lists.newArrayList(apiResponse.getPagedData()), pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        return "user/socialSecurity/list";
    }

    @RequiresPermissions("sts:social_security:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute SocialSecurityForm socialSecurityForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".socialSecurityForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(socialSecurityForm.getSocialSecurity())) {
            socialSecurityForm.setSocialSecurity(new SocialSecurityEntity());
        }
        List<UserEntity> userEntityList = userService.findAll();
        modelMap.put("action", "create");
        modelMap.put("userList", userEntityList);
        return "user/socialSecurity/edit";
    }

    @RequiresPermissions("sts:social_security:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(SocialSecurityForm socialSecurityForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(socialSecurityForm);
            return "redirect:/socialSecurity/create";
        }
        SocialSecurityEntity socialSecurityEntity = socialSecurityForm.getSocialSecurity();
        UserEntity userEntity = userService.get(socialSecurityEntity.getUserId());
        socialSecurityEntity.setRealName(userEntity.getRealName());
        socialSecurityEntity.setDepartmentId(userEntity.getDepartmentId());
        socialSecurityService.save(socialSecurityEntity);
        return "result";
    }

    @RequiresPermissions("sts:social_security:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, @ModelAttribute SocialSecurityForm socialSecurityForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".userForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        SocialSecurityEntity socialSecurityEntity = socialSecurityService.get(id);
        if (Objects.isNull(socialSecurityEntity)) {
            logger.error("修改部门,未查询[id={}]的部门信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }
        socialSecurityForm.setSocialSecurity(socialSecurityEntity);
        List<UserEntity> userEntityList = userService.findAll();
        modelMap.put("userList", userEntityList);
        modelMap.put("action", "update");
        return "user/socialSecurity/edit";
    }

    @RequiresPermissions("sts:social_security:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(SocialSecurityForm socialSecurityForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(socialSecurityForm);
            return "redirect:/socialSecurity/update/" + socialSecurityForm.getSocialSecurity().getId();
        }
        SocialSecurityEntity socialSecurity = socialSecurityForm.getSocialSecurity();
        SocialSecurityEntity socialSecurityEntity = socialSecurityService.get(socialSecurity.getId());
        UserEntity userEntity = userService.get(socialSecurityEntity.getUserId());
        socialSecurityEntity.setRealName(userEntity.getRealName());
        socialSecurityEntity.setDepartmentId(userEntity.getDepartmentId());
        socialSecurityService.update(socialSecurityEntity);
        return "result";
    }

    @RequiresPermissions("sts:social_security:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        modelMap.put("socialSecurity", socialSecurityService.get(id));
        return "user/socialSecurity/view";
    }
}
