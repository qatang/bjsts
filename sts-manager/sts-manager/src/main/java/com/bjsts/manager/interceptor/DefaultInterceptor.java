package com.bjsts.manager.interceptor;

import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.entity.system.LogEntity;
import com.bjsts.manager.entity.system.ResourceEntity;
import com.bjsts.manager.entity.system.UserEntity;
import com.bjsts.manager.enums.system.ResourceType;
import com.bjsts.manager.service.system.LogService;
import com.bjsts.manager.service.system.ResourceService;
import com.bjsts.manager.service.system.RoleService;
import com.bjsts.manager.service.system.UserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @author jinsheng
 * @since 2016-04-27 11:56
 */
@Component
public class DefaultInterceptor extends HandlerInterceptorAdapter implements ServletContextAware {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String CONTEXT_PATH = "ctx";

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private LogService logService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(CONTEXT_PATH, request.getContextPath());

        LogEntity log = new LogEntity();
        UserEntity userInfo = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        if (userInfo == null) {
            return true;
        }
/*        userInfo = userService.get(userInfo.getId());
        if (userInfo == null) {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            response.sendRedirect("/signin");
            logger.error("未发现当前用户，无法记录操作日志");
            return false;
        }*/

        try {
            log.setUserId(userInfo.getId());
            log.setUrl(request.getRequestURI().substring(request.getContextPath().length()));
            log.setParams(convertParamsToString(request.getParameterMap()));
            logService.save(log);
        } catch (Exception e) {
            logger.error("保存日志失败，请求继续处理。");
            logger.error(e.getMessage(), e);
        }
        return true;
    }

    private String convertParamsToString(Map<String, String[]> parameters) {
        StringBuilder sb = new StringBuilder();
        for (String key : parameters.keySet()) {
            String[] values = parameters.get(key);

            StringBuilder subSb = new StringBuilder();
            for (String value1 : values) {
                subSb.append(value1).append("|");
            }
            if (subSb.length() > 0) {
                subSb.deleteCharAt(subSb.length() - 1);
            }
            sb.append(key).append("=").append(subSb.toString()).append("&");

        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        if (modelAndView == null) {
            return;
        }

        UserEntity userInfo = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        if (userInfo == null) {
            return;
        }
        userInfo = userService.get(userInfo.getId());
        if (userInfo == null) {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            response.sendRedirect("/signin");
            return;
        }

        Set<ResourceEntity> resourceSet = Sets.newHashSet();

        List<Long> roleIdList = userService.findRoleIdByUserId(userInfo.getId());
        List<Long> resourceIdList = roleService.findResourceIdByRoleIdIn(roleIdList);
        resourceService.findAll(resourceIdList).forEach(resourceEntity -> {
            if (Objects.equals(resourceEntity.getValid(), EnableDisableStatus.ENABLE) &&
                Objects.equals(resourceEntity.getType(), ResourceType.MENU)) {
                resourceSet.add(resourceEntity);
            }
        });

        List<ResourceEntity> menuList = Lists.newArrayList();

        for (ResourceEntity resource : resourceSet) {
            if (resource.getType() == ResourceType.FUNCTION) {
                continue;
            }

            ResourceEntity parent = null;

            if (!Objects.isNull(resource.getParentId())) {
                parent = resourceService.get(resource.getParentId());
            }

            if (parent == null) {
                if (!menuList.contains(resource)) {
                    resource.setChildren(Lists.newArrayList());
                    menuList.add(resource);
                }
                continue;
            }

            if (parent.getType() == ResourceType.FUNCTION) {
                continue;
            }

            if (!menuList.contains(parent)) {
                parent.setChildren(Lists.newArrayList());
                menuList.add(parent);
            }

            List<ResourceEntity> children = parent.getChildren();
            children.add(resource);
        }

        for (ResourceEntity menu : menuList) {
            sort(menu.getChildren());
        }
        sort(menuList);

        modelAndView.addObject("menus", menuList.size() == 0 ? null : menuList);
    }

    private void sort(List<ResourceEntity> menuList) {
        Collections.sort(menuList, (o1, o2) -> {
            if (!Objects.equals(o1.getPriority(), o2.getPriority())) {
                return o2.getPriority() - o1.getPriority();
            }
            return (int)(o1.getId() - o2.getId());
        });
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        logger.error("sts-manager started!");
    }
}
