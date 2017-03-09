package com.bjsts.manager.query.user;

import com.bjsts.manager.core.query.CommonSearchable;

/**
 * @author wangzhiliang
 */
public class StaffSearchable extends CommonSearchable {

    private static final long serialVersionUID = -5429683998557425502L;

    private String realName;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
