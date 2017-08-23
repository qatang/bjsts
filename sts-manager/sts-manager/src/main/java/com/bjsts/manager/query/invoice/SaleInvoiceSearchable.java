package com.bjsts.manager.query.invoice;

import com.bjsts.manager.core.query.CommonSearchable;
import com.bjsts.manager.enums.invoice.InvoiceCategory;
import com.bjsts.manager.enums.invoice.InvoiceStatus;

/**
 * @author wangzhiliang
 */
public class SaleInvoiceSearchable extends CommonSearchable {

    private static final long serialVersionUID = 8431584724047630419L;

    private InvoiceCategory invoiceCategory;

    private InvoiceStatus invoiceStatus;

    private String planContent;

    private String customer;

    public InvoiceCategory getInvoiceCategory() {
        return invoiceCategory;
    }

    public void setInvoiceCategory(InvoiceCategory invoiceCategory) {
        this.invoiceCategory = invoiceCategory;
    }

    public InvoiceStatus getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
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
