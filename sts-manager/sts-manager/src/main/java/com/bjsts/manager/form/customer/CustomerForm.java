package com.bjsts.manager.form.customer;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.customer.CustomerEntity;

/**
 * @author jinsheng
 * @since 2016-05-12 16:00
 */
public class CustomerForm extends AbstractForm {

    private static final long serialVersionUID = 5859168020901454685L;

    private CustomerEntity customer;

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }
}
