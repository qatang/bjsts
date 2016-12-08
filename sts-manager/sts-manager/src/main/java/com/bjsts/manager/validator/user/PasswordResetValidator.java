package com.bjsts.manager.validator.user;

import com.bjsts.manager.core.exception.ValidateFailedException;
import com.bjsts.manager.core.validator.AbstractValidator;
import com.bjsts.manager.entity.user.UserEntity;
import com.bjsts.manager.form.user.UserForm;
import com.bjsts.manager.service.user.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author jinsheng
 * @since 2016-05-13 13:53
 */
@Component
public class PasswordResetValidator extends AbstractValidator<UserForm> {

    @Autowired
    private UserService userService;

    @Override
    public boolean validate(UserForm userForm) throws ValidateFailedException {
        if (userForm == null || userForm.getUserInfo() == null || userForm.getUserInfo().getId() == null) {
            String msg = String.format("userForm参数不能为空");
            logger.error(msg);
            throw new ValidateFailedException("userForm", msg);
        }
        UserEntity userInfo = userService.get(userForm.getUserInfo().getId());
        if (userInfo == null) {
            String msg = String.format("userInfo不能为空");
            logger.error(msg);
            throw new ValidateFailedException("userInfo", msg);
        }
        if (StringUtils.isEmpty(userForm.getNewPassword())) {
            String msg = String.format("新密码不能为空");
            logger.error(msg);
            throw new ValidateFailedException("newPassword", msg);
        }
        if (userForm.getNewPassword().length() < 6 || userForm.getNewPassword().length() > 16) {
            String msg = String.format("新密码长度必须在6-16个字符之间");
            logger.error(msg);
            throw new ValidateFailedException("newPassword", msg);
        }
        if (StringUtils.isEmpty(userForm.getConPassword())) {
            String msg = String.format("确认密码不能为空");
            logger.error(msg);
            throw new ValidateFailedException("conPassword", msg);
        }
        if (userForm.getConPassword().length() < 6 || userForm.getConPassword().length() > 16) {
            String msg = String.format("确认密码长度必须在6-16个字符之间");
            logger.error(msg);
            throw new ValidateFailedException("conPassword", msg);
        }
        if (!userForm.getNewPassword().equals(userForm.getConPassword())) {
            String msg = String.format("两次输入密码不一致");
            logger.error(msg);
            throw new ValidateFailedException("newPassword", msg);
        }
        return true;
    }
}
