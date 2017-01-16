package com.bjsts.manager.form.sale;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.sale.PlanEntity;

/**
 * @author jinsheng
 * @since 2016-05-12 16:00
 */
public class ProductOrderForm extends AbstractForm {

    private static final long serialVersionUID = 5859168020901454685L;

    private PlanEntity productOrder;

    private String customerFileUrl;

    public PlanEntity getProductOrder() {
        return productOrder;
    }

    public void setProductOrder(PlanEntity productOrder) {
        this.productOrder = productOrder;
    }

    public String getCustomerFileUrl() {
        return customerFileUrl;
    }

    public void setCustomerFileUrl(String customerFileUrl) {
        this.customerFileUrl = customerFileUrl;
    }
}
