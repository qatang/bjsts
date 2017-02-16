package com.bjsts.manager.controller.user;

import com.bjsts.core.api.component.request.ApiPageRequestHelper;
import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.PageOrderType;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.user.DepartmentEntity;
import com.bjsts.manager.entity.user.StaffEntity;
import com.bjsts.manager.enums.user.EducationType;
import com.bjsts.manager.enums.user.MaleType;
import com.bjsts.manager.enums.user.OnJobStatus;
import com.bjsts.manager.enums.user.PolityType;
import com.bjsts.manager.form.user.StaffForm;
import com.bjsts.manager.query.user.StaffSearchable;
import com.bjsts.manager.service.user.DepartmentService;
import com.bjsts.manager.service.user.StaffService;
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
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Objects;

/**
 * @author wangzhiliang
 */
@Controller
@RequestMapping("/staff")
@SessionAttributes("staffSearchable")
public class StaffController extends AbstractController {

    @Autowired
    private StaffService staffService;

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

    @ModelAttribute("onJobList")
    public List<OnJobStatus> getOnJobList() {
        return OnJobStatus.list();
    }

    @RequiresPermissions("sts:staff:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(StaffSearchable staffSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<StaffEntity> apiResponse = staffService.findAll(staffSearchable, pageable);

        List<StaffEntity> staffEntities = Lists.newArrayList(apiResponse.getPagedData());
        staffEntities.forEach(staffEntity -> staffEntity.setDepartmentName(departmentService.get(staffEntity.getDepartmentId()).getName()));

        Page<StaffEntity> page = new PageImpl<>(staffEntities, pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        return "user/staff/list";
    }

    @RequiresPermissions("sts:staff:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute StaffForm staffForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".staffForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(staffForm.getStaff())) {
            staffForm.setStaff(new StaffEntity());
        }

        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance().paging(0, 100)
                .addOrder("id", PageOrderType.ASC);

        List<DepartmentEntity> departmentEntities = ApiPageRequestHelper.request(apiRequest, apiRequestPage, departmentService::findAll);
        modelMap.put("departmentList", departmentEntities);
        modelMap.put("action", "create");
        return "user/staff/edit";
    }

    @RequiresPermissions("sts:staff:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(StaffForm staffForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(staffForm);
            return "redirect:/staff/create";
        }

        StaffEntity staffEntity = staffForm.getStaff();

        Long departmentId = staffEntity.getDepartmentId();
        if (departmentId == null) {
            result.addError(new ObjectError("departmentId", "请选择部门"));
            redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result.getAllErrors());
            redirectAttributes.addFlashAttribute(staffForm);
            return "redirect:/staff/create";
        }

        StaffEntity dbStaffEntity = staffService.get(staffEntity.getId());
        if (dbStaffEntity != null) {
         result.addError(new ObjectError("staffId", "职工编号已存在"));
         redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result.getAllErrors());
         redirectAttributes.addFlashAttribute(staffForm);
         return "redirect:/staff/create";
        }

        staffService.save(staffEntity);
        return "result";
    }

    @RequiresPermissions("sts:staff:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, @ModelAttribute StaffForm staffForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".staffForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        StaffEntity staffEntity = staffService.get(id);
        if (Objects.isNull(staffEntity)) {
            logger.error("修改员工信息,未查询[id={}]的员工信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }
        staffForm.setStaff(staffEntity);

        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance().paging(0, 100)
                .addOrder("id", PageOrderType.ASC);

        List<DepartmentEntity> departmentEntities = ApiPageRequestHelper.request(apiRequest, apiRequestPage, departmentService::findAll);

        modelMap.put("departmentList", departmentEntities);
        modelMap.put("action", "update");
        return "user/staff/edit";
    }

    @RequiresPermissions("sts:staff:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(StaffForm staffForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(staffForm);
            return "redirect:/staff/update/" + staffForm.getStaff().getId();
        }

        StaffEntity staff = staffForm.getStaff();
        Long departmentId = staff.getDepartmentId();
        if (departmentId == null) {
            result.addError(new ObjectError("departmentId", "请选择部门"));
            redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result.getAllErrors());
            redirectAttributes.addFlashAttribute(staffForm);
            return "redirect:/staff/update/" + staffForm.getStaff().getId();
        }

        StaffEntity staffEntity = staffService.get(staff.getId());
        staffEntity.setDepartmentId(staff.getDepartmentId());
        staffEntity.setStaffNo(staff.getStaffNo());
        staffEntity.setRealName(staff.getRealName());
        staffEntity.setMaleType(staff.getMaleType());
        staffEntity.setPosition(staff.getPosition());
        staffEntity.setIdCard(staff.getIdCard());
        staffEntity.setEntryTime(staff.getEntryTime());
        staffEntity.setDepartureTime(staff.getDepartureTime());
        staffEntity.setEducationType(staff.getEducationType());
        staffEntity.setPolityType(staff.getPolityType());
        staffEntity.setBirthday(staff.getBirthday());
        staffEntity.setOnJob(staff.getOnJob());
        staffEntity.setMobile(staff.getMobile());
        staffEntity.setEmail(staff.getEmail());
        staffService.update(staffEntity);
        return "result";
    }

    @RequiresPermissions("sts:staff:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        StaffEntity staffEntity = staffService.get(id);
        staffEntity.setDepartmentName(departmentService.get(staffEntity.getDepartmentId()).getName());
        modelMap.put("staff", staffEntity);
        return "user/staff/view";
    }

    @RequiresPermissions("sts:staff:disable")
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public String disable(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        StaffEntity staffEntity = staffService.get(id);
        if (Objects.isNull(staffEntity)) {
            logger.error("删除员工信息,未查询[id={}]的员工信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }
        staffEntity.setValid(EnableDisableStatus.DISABLE);
        staffService.update(staffEntity);
        return "redirect:/staff/list";
    }
}
