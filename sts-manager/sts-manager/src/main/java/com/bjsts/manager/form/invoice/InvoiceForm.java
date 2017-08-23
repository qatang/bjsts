package com.bjsts.manager.form.invoice;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.invoice.InvoiceEntity;

/**
 * @author wangzhiliang
 */
public class InvoiceForm extends AbstractForm {

    private static final long serialVersionUID = -6737151436573852841L;

    private InvoiceEntity invoice;

    private DocumentEntity document = new DocumentEntity();

    private Double amount;

    public InvoiceEntity getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceEntity invoice) {
        this.invoice = invoice;
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
