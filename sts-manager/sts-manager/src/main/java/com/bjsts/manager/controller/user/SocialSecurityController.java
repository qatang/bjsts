package com.bjsts.manager.controller.user;

import com.bjsts.core.api.component.request.ApiPageRequestHelper;
import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.PageOrderType;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.user.SocialSecurityEntity;
import com.bjsts.manager.entity.user.StaffEntity;
import com.bjsts.manager.form.user.SocialSecurityForm;
import com.bjsts.manager.query.user.SocialSecuritySearchable;
import com.bjsts.manager.service.user.SocialSecurityService;
import com.bjsts.manager.service.user.StaffService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
import java.util.Map;
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
    private StaffService staffService;

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

        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance().paging(0, 100)
                .addOrder("id", PageOrderType.ASC);

        List<StaffEntity> staffEntityList = ApiPageRequestHelper.request(apiRequest, apiRequestPage, staffService::findAll);

        modelMap.put("action", "create");
        modelMap.put("staffList", staffEntityList);
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
        Long staffId = socialSecurityEntity.getStaffId();
        if (staffId == null) {
            result.addError(new ObjectError("staffId", "请选择职工"));
            redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result.getAllErrors());
            redirectAttributes.addFlashAttribute(socialSecurityForm);
            return "redirect:/socialSecurity/create";
        }

        StaffEntity staffEntity = staffService.get(staffId);

        SocialSecurityEntity db = socialSecurityService.findByStaffId(socialSecurityEntity.getStaffId());
        if (db != null) {
            result.addError(new ObjectError("staffId", "职工已存在"));
            redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result.getAllErrors());
            redirectAttributes.addFlashAttribute(socialSecurityForm);
            return "redirect:/socialSecurity/create";
        }
        socialSecurityEntity.setRealName(staffEntity.getRealName());
        socialSecurityEntity.setDepartmentId(staffEntity.getDepartmentId());
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
            logger.error("修改社保信息,未查询[id={}]的社保信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "未查询[id=" +id+ "]的社保信息!");
            return "redirect:/error";
        }
        socialSecurityForm.setSocialSecurity(socialSecurityEntity);

        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance().paging(0, 100)
                .addOrder("id", PageOrderType.ASC);

        List<StaffEntity> staffEntityList = ApiPageRequestHelper.request(apiRequest, apiRequestPage, staffService::findAll);

        modelMap.put("staffList", staffEntityList);
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
        socialSecurityEntity.setIdCard(socialSecurity.getIdCard());
        socialSecurityEntity.setMobile(socialSecurity.getMobile());
        socialSecurityEntity.setMemo(socialSecurity.getMemo());
        socialSecurityService.save(socialSecurityEntity);
        return "result";
    }

    @RequiresPermissions("sts:social_security:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        modelMap.put("socialSecurity", socialSecurityService.get(id));
        return "user/socialSecurity/view";
    }

    @RequiresPermissions("sts:social_security:findStaff")
    @RequestMapping("/findStaff/{staffId}")
    @ResponseBody
    public Map<String,String> findStaff(@PathVariable Long staffId) {
        Map<String, String> result = Maps.newHashMap();
        try {
            StaffEntity staffEntity = staffService.get(staffId);
            if (staffEntity != null) {
                result.put("realName", staffEntity.getRealName());
            }
        } catch (Exception e) {
            logger.error("调用UserService获取用户信息接口出错！");
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    @RequiresPermissions("sts:social_security:disable")
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public String disable(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        SocialSecurityEntity socialSecurityEntity = socialSecurityService.get(id);
        if (Objects.isNull(socialSecurityEntity)) {
            logger.error("删除社保信息,未查询[id={}]的社保信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "未查询[id={}]的社保信息!");
            return "redirect:/error";
        }
        socialSecurityService.delete(id);
        return "redirect:/socialSecurity/list";
    }
}
