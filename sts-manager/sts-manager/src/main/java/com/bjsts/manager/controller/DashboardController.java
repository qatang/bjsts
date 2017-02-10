package com.bjsts.manager.controller;

import com.bjsts.manager.core.controller.AbstractController;
import com.bjsts.manager.entity.system.UserEntity;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author jinsheng
 * @since 2016-04-27 11:42
 */
@Controller
public class DashboardController extends AbstractController {

    @RequestMapping("/dashboard")
    public String dashboard(ModelMap modelMap) {
        UserEntity currentUser = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        modelMap.addAttribute("currentUser", currentUser);
        return "dashboard";
    }
}
