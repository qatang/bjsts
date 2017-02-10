package com.bjsts.manager.controller.user;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.user.StaffEntity;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

/**
 * 通讯录
 * @author wangzhiliang
 */
@Controller
@RequestMapping("/contact")
@SessionAttributes("staffSearchable")
public class ContactController extends AbstractController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private StaffService staffService;

    @RequiresPermissions("sts:contact:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(StaffSearchable staffSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<StaffEntity> apiResponse = staffService.findAll(staffSearchable, pageable);

        List<StaffEntity> staffEntities = Lists.newArrayList(apiResponse.getPagedData());
        staffEntities.forEach(staffEntity -> staffEntity.setDepartmentName(departmentService.get(staffEntity.getDepartmentId()).getName()));

        Page<StaffEntity> page = new PageImpl<>(staffEntities, pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        return "user/contact/list";
    }
}
