package com.bjsts.manager.form.invoice;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.invoice.SaleInvoiceEntity;

/**
 * @author wangzhiliang
 */
public class SaleInvoiceForm extends AbstractForm {

    private static final long serialVersionUID = 1916926399546029133L;

    private SaleInvoiceEntity saleInvoice;

    private DocumentEntity document = new DocumentEntity();

    private Double amount;

    public SaleInvoiceEntity getSaleInvoice() {
        return saleInvoice;
    }

    public void setSaleInvoice(SaleInvoiceEntity saleInvoice) {
        this.saleInvoice = saleInvoice;
    }

    public DocumentEntity getDocument() {
        return document;
    }

    public void setDocument(DocumentEntity document) {
        this.document = document;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
