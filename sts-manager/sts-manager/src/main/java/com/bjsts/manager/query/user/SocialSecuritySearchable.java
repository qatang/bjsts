package com.bjsts.manager.query.user;

import com.bjsts.manager.core.query.CommonSearchable;

/**
 * @author wangzhiliang
 */
public class SocialSecuritySearchable extends CommonSearchable {

    private static final long serialVersionUID = 1428872828683159631L;

    private String realName;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
