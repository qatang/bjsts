package com.bjsts.manager.query.invoice;

import com.bjsts.manager.core.query.CommonSearchable;
import com.bjsts.manager.enums.invoice.InvoiceCategory;

/**
 * @author wangzhiliang
 */
public class InvoiceSearchable extends CommonSearchable {

    private static final long serialVersionUID = -8323150831452401842L;

    private InvoiceCategory invoiceCategory;

    private String planContent;

    private String customer;

    public InvoiceCategory getInvoiceCategory() {
        return invoiceCategory;
    }

    public void setInvoiceCategory(InvoiceCategory invoiceCategory) {
        this.invoiceCategory = invoiceCategory;
    }

    public String getPlanContent() {
        return planContent;
    }

    public void setPlanContent(String planContent) {
        this.planContent = planContent;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
