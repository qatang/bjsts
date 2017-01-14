package com.bjsts.manager.shiro.realm;

import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.entity.user.UserEntity;
import com.bjsts.manager.service.system.ResourceService;
import com.bjsts.manager.service.system.RoleService;
import com.bjsts.manager.service.user.UserService;
import com.bjsts.manager.shiro.authentication.PasswordHelper;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author jinsheng
 * @since 2016-04-27 15:50
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private PasswordHelper passwordHelper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> stringPermissions = Sets.newHashSet();

        UserEntity userInfo = (UserEntity) principals.getPrimaryPrincipal();
        List<Long> roleIdList = userService.findRoleIdByUserId(userInfo.getId());
        List<Long> resourceIdList = roleService.findResourceIdByRoleIdIn(roleIdList);
        resourceService.findAll(resourceIdList).forEach(resourceEntity -> {
            if (Objects.nonNull(resourceEntity) && StringUtils.isNotEmpty(resourceEntity.getIdentifier())) {
                stringPermissions.add(resourceEntity.getIdentifier());
            }
        });

        authorizationInfo.setStringPermissions(stringPermissions);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();

        UserEntity userInfo = userService.findByUsername(username);

        if (userInfo == null) {
            throw new UnknownAccountException("帐号或密码错误！");
        }

        if (Objects.equals(userInfo.getValid(), EnableDisableStatus.DISABLE)) {
            throw new LockedAccountException("帐号无效！");
        }

        return new SimpleAuthenticationInfo(
                userInfo,
                userInfo.getPassword(),
                ByteSource.Util.bytes(passwordHelper.getSalt(userInfo)),
                getName()
        );
    }
}
