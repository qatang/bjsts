package com.bjsts.manager.controller.user;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.user.AttendanceEntity;
import com.bjsts.manager.entity.user.UserEntity;
import com.bjsts.manager.form.user.AttendanceForm;
import com.bjsts.manager.query.user.AttendanceSearchable;
import com.bjsts.manager.service.user.AttendanceService;
import com.bjsts.manager.service.user.DepartmentService;
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
@RequestMapping("/attendance")
@SessionAttributes("attendanceSearchable")
public class AttendanceController extends AbstractController {

    @Autowired
    private AttendanceService attendanceService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UserService userService;

    @RequiresPermissions("sts:attendance:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(AttendanceSearchable attendanceSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<AttendanceEntity> apiResponse = attendanceService.findAll(attendanceSearchable, pageable);
        Page<AttendanceEntity> page = new PageImpl<>(Lists.newArrayList(apiResponse.getPagedData()), pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        return "user/attendance/list";
    }

    @RequiresPermissions("sts:attendance:create")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(@ModelAttribute AttendanceForm attendanceForm, ModelMap modelMap) {
        if (modelMap.containsKey(BINDING_RESULT_KEY)) {
            modelMap.addAttribute(BindingResult.class.getName().concat(".attendanceForm"), modelMap.get(BINDING_RESULT_KEY));
        }
        if (Objects.isNull(attendanceForm.getAttendance())) {
            attendanceForm.setAttendance(new AttendanceEntity());
        }
        List<UserEntity> userEntityList = userService.findAll();
        modelMap.put("action", "create");
        modelMap.put("userList", userEntityList);
        return "user/attendance/edit";
    }

    @RequiresPermissions("sts:attendance:create")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(AttendanceForm attendanceForm, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute(attendanceForm);
            return "redirect:/attendance/create";
        }
        AttendanceEntity attendanceEntity = attendanceForm.getAttendance();
        UserEntity userEntity = userService.get(attendanceEntity.getUserId());
        attendanceEntity.setRealName(userEntity.getRealName());
        attendanceEntity.setDepartmentId(userEntity.getDepartmentId());
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
        List<UserEntity> userEntityList = userService.findAll();
        modelMap.put("userList", userEntityList);
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
        UserEntity userEntity = userService.get(attendanceEntity.getUserId());
        attendanceEntity.setRealName(userEntity.getRealName());
        attendanceEntity.setDepartmentId(userEntity.getDepartmentId());
        attendanceService.update(attendanceEntity);
        return "result";
    }

    @RequiresPermissions("sts:attendance:view")
    @RequestMapping(value = "/view/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, ModelMap modelMap) {
        modelMap.put("attendance", attendanceService.get(id));
        return "user/attendance/view";
    }
}
