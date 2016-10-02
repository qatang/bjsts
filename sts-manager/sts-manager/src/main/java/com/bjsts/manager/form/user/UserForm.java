package com.bjsts.manager.form.user;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.user.UserEntity;

import java.util.List;

/**
 * @author jinsheng
 * @since 2016-04-27 16:31
 */
public class UserForm extends AbstractForm {

    private static final long serialVersionUID = 4249491583631617431L;

    private UserEntity userInfo;
    private String newPassword;
    private String conPassword;
    private String captcha;
    private boolean rememberMe;
    private List<Long> roleIdList;

    public UserEntity getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserEntity userInfo) {
        this.userInfo = userInfo;
    }

    public String getConPassword() {
        return conPassword;
    }

    public void setConPassword(String conPassword) {
        this.conPassword = conPassword;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public List<Long> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Long> roleIdList) {
        this.roleIdList = roleIdList;
    }
}
