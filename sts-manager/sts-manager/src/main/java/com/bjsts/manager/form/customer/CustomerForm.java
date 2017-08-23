package com.bjsts.manager.form.customer;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.customer.CustomerEntity;
import com.bjsts.manager.entity.customer.CustomerItemEntity;

/**
 * @author jinsheng
 * @since 2016-05-12 16:00
 */
public class CustomerForm extends AbstractForm {

    private static final long serialVersionUID = 5859168020901454685L;

    private CustomerEntity customer;

    private CustomerItemEntity customerItem;

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public CustomerItemEntity getCustomerItem() {
        return customerItem;
    }

    public void setCustomerItem(CustomerItemEntity customerItem) {
        this.customerItem = customerItem;
    }
}
