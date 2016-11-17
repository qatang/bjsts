package com.bjsts.manager.form.user;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.user.SocialSecurityEntity;

/**
 * @author wangzhiliang
 */
public class SocialSecurityForm extends AbstractForm {

    private static final long serialVersionUID = 3451527378113742058L;

    private SocialSecurityEntity socialSecurity;

    public SocialSecurityEntity getSocialSecurity() {
        return socialSecurity;
    }

    public void setSocialSecurity(SocialSecurityEntity socialSecurity) {
        this.socialSecurity = socialSecurity;
    }
}
