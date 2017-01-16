package com.bjsts.manager.form.sale;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.sale.PlanEntity;

/**
 * @author jinsheng
 * @since 2016-05-12 16:00
 */
public class QuoteForm extends AbstractForm {

    private static final long serialVersionUID = 4977265214686541763L;

    private PlanEntity productOrder;

    private String customerFileUrl;

    private String quoteFileUrl;

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

    public String getQuoteFileUrl() {
        return quoteFileUrl;
    }

    public void setQuoteFileUrl(String quoteFileUrl) {
        this.quoteFileUrl = quoteFileUrl;
    }
}
