package com.bjsts.manager.controller.user;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.user.UserEntity;
import com.bjsts.manager.query.user.UserSearchable;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 * 通讯录
 * @author wangzhiliang
 */
@Controller
@RequestMapping("/contact")
@SessionAttributes("attendanceSearchable")
public class ContactController extends AbstractController {

    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private UserService userService;

    @RequiresPermissions("sts:contact:list")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(UserSearchable userSearchable, @PageableDefault(size = GlobalConstants.DEFAULT_PAGE_SIZE, sort = "createdTime", direction = Sort.Direction.DESC) Pageable pageable, ModelMap modelMap) {
        ApiResponse<UserEntity> apiResponse = userService.findAll(userSearchable, pageable);
        Page<UserEntity> page = new PageImpl<>(Lists.newArrayList(apiResponse.getPagedData()), pageable, apiResponse.getTotal());
        modelMap.addAttribute("page", page);
        return "user/contact/list";
    }
}
