package com.bjsts.manager.query.customer;

import com.bjsts.manager.core.query.CommonSearchable;

/**
 * @author jinsheng
 * @since 2016-05-09 10:55
 */
public class CustomerSearchable extends CommonSearchable {

    private static final long serialVersionUID = -2048350258192544932L;

    private String companyName;

    private String tel;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }
}
