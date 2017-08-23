package com.bjsts.manager.controller.user;

import com.bjsts.core.api.component.request.ApiPageRequestHelper;
import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.PageOrderType;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.user.AttendanceEntity;
import com.bjsts.manager.entity.user.StaffEntity;
import com.bjsts.manager.form.user.AttendanceForm;
import com.bjsts.manager.query.user.StaffSearchable;
import com.bjsts.manager.service.user.AttendanceService;
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
 * 考勤
 * @author wangzhiliang
 */
@Controller
@RequestMapping("/attendance")
@SessionAttributes("attendanceSearchable")
public class AttendanceController extends AbstractController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private DepartmentService departmentService;

    @RequiresPermissions("sts:attendance:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(StaffSearchable staffSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<StaffEntity> apiResponse = staffService.findAll(staffSearchable, pageable);

        List<StaffEntity> staffEntities = Lists.newArrayList(apiResponse.getPagedData());
        staffEntities.forEach(staffEntity -> staffEntity.setDepartmentName(departmentService.get(staffEntity.getDepartmentId()).getName()));

        Page<StaffEntity> page = new PageImpl<>(staffEntities, pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);

        return "user/attendance/list";
    }

    @RequiresPermissions("sts:attendance:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute AttendanceForm attendanceForm, ModelMap modelMap, BindingResult result, RedirectAttributes redirectAttributes) {
        return "user/attendance/edit";
    }

    /*@RequiresPermissions("sts:attendance:create")
    @RequestMapping(value = "/create/{id}", method = RequestMethod.GET)
    public String create(@PathVariable Long id, @ModelAttribute AttendanceForm attendanceForm, ModelMap modelMap, BindingResult result, RedirectAttributes redirectAttributes) {
*//*        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".attendanceForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(attendanceForm.getAttendance())) {
            attendanceForm.setAttendance(new AttendanceEntity());
        }

        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance().paging(0, 100)
                .addOrder("id", PageOrderType.ASC);

        List<StaffEntity> staffEntityList = ApiPageRequestHelper.request(apiRequest, apiRequestPage, staffService::findAll);

        modelMap.put("staffList", staffEntityList);*//*
        StaffEntity staffEntity = staffService.get(id);
        if (staffEntity == null) {
            result.addError(new ObjectError("staffId", "错误的请求"));
            redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result.getAllErrors());
            redirectAttributes.addFlashAttribute(attendanceForm);
            return "redirect:/attendance/list";
        }

        Integer year = attendanceForm.getYear();
        Integer month = attendanceForm.getMonth();
        if (year == null || year == 0) {
            year = LocalDate.now().getYear();
        }
        if (month == null || month == 0) {
            month = LocalDate.now().getMonthValue();
        }

        LocalDateTime beginTime = LocalDateTime.of(year, Month.of(month), 1, 0, 0, 0);
        LocalDateTime endTime = beginTime.plusMonths(1).minusDays(1);
        Date beginDate = CoreDateUtils.parseDate(CoreDateUtils.formatLocalDateTime(beginTime, CoreDateUtils.DATETIME), CoreDateUtils.DATETIME);
        Date endDate = CoreDateUtils.parseDate(CoreDateUtils.formatLocalDateTime(endTime, CoreDateUtils.DATETIME), CoreDateUtils.DATETIME);
        AttendanceSearchable attendanceSearchable = new AttendanceSearchable();
        attendanceSearchable.setBeginCreatedTime(beginDate);
        attendanceSearchable.setEndCreatedTime(endDate);

        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterBetween("startTime", beginDate, endDate);
        apiRequest.filterEqual("staffId", id);
        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance()
                .paging(0, 100)
                .addOrder("startTime", PageOrderType.ASC)
                .addOrder("timeType", PageOrderType.ASC);
        List<AttendanceEntity> attendanceEntities = ApiPageRequestHelper.request(apiRequest, apiRequestPage, attendanceService::findAll);
        modelMap.put("staff", staffEntity);
        modelMap.put("attendanceList", attendanceEntities);
        return "user/attendance/edit";
    }*/

    @RequiresPermissions("sts:attendance:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(AttendanceForm attendanceForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(attendanceForm);
            return "redirect:/attendance/create";
        }
        AttendanceEntity attendanceEntity = attendanceForm.getAttendance();

        Long staffId = attendanceEntity.getStaffId();
        if (staffId == null) {
            result.addError(new ObjectError("staffId", "请选择职工"));
            redirectAttributes.addFlashAttribute(BINDING_RESULT_KEY, result.getAllErrors());
            redirectAttributes.addFlashAttribute(attendanceForm);
            return "redirect:/attendance/create";
        }

        StaffEntity staffEntity = staffService.get(attendanceEntity.getStaffId());
        attendanceEntity.setRealName(staffEntity.getRealName());
        attendanceEntity.setDepartmentId(staffEntity.getDepartmentId());
        attendanceService.save(attendanceEntity);
        return "result";
    }

    @RequiresPermissions("sts:attendance:update")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable Long id, @ModelAttribute AttendanceForm attendanceForm, RedirectAttributes redirectAttributes, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".userForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        AttendanceEntity attendanceEntity = attendanceService.get(id);
        if (Objects.isNull(attendanceEntity)) {
            logger.error("修改部门,未查询[id={}]的部门信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "无效数据!");
            return "redirect:/error";
        }
        attendanceForm.setAttendance(attendanceEntity);

        ApiRequest apiRequest = ApiRequest.newInstance();
        apiRequest.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage apiRequestPage = ApiRequestPage.newInstance().paging(0, 100)
                .addOrder("id", PageOrderType.ASC);

        List<StaffEntity> staffEntityList = ApiPageRequestHelper.request(apiRequest, apiRequestPage, staffService::findAll);

        modelMap.put("staffList", staffEntityList);
        modelMap.put("action", "update");
        return "user/attendance/edit";
    }

    @RequiresPermissions("sts:attendance:update")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(AttendanceForm attendanceForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(attendanceForm);
            return "redirect:/attendance/update/" + attendanceForm.getAttendance().getId();
        }
        AttendanceEntity attendance = attendanceForm.getAttendance();
        AttendanceEntity attendanceEntity = attendanceService.get(attendance.getId());
        StaffEntity staffEntity = staffService.get(attendanceEntity.getStaffId());
        attendanceEntity.setRealName(staffEntity.getRealName());
        attendanceEntity.setDepartmentId(staffEntity.getDepartmentId());
        attendanceEntity.setStartTime(attendance.getStartTime());
        //attendanceEntity.setEndTime(attendance.getEndTime());
        attendanceService.update(attendanceEntity);
        return "result";
    }

    @RequiresPermissions("sts:attendance:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        modelMap.put("attendance", attendanceService.get(id));
        return "user/attendance/view";
    }

    @RequiresPermissions("sts:department:disable")
    @RequestMapping(value = "/disable/{id}", method = RequestMethod.GET)
    public String disable(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        AttendanceEntity attendanceEntity = attendanceService.get(id);
        if (Objects.isNull(attendanceEntity)) {
            logger.error("删除考勤信息,未查询[id={}]的考勤信息", id);
            redirectAttributes.addFlashAttribute(ERROR_MESSAGE_KEY, "未查询[id={}]的考勤信息!");
            return "redirect:/error";
        }
        attendanceService.delete(id);
        return "redirect:/attendance/list";
    }
}
