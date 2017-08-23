package com.bjsts.manager.form.sale;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.sale.PlanEntity;

/**
 * @author jinsheng
 * @since 2016-05-12 16:00
 */
public class QuoteForm extends AbstractForm {

    private static final long serialVersionUID = 4977265214686541763L;

    private PlanEntity productOrder;

    private DocumentEntity planDocument;

    private DocumentEntity document = new DocumentEntity();

    public PlanEntity getProductOrder() {
        return productOrder;
    }

    public void setProductOrder(PlanEntity productOrder) {
        this.productOrder = productOrder;
    }

    public DocumentEntity getPlanDocument() {
        return planDocument;
    }

    public void setPlanDocument(DocumentEntity planDocument) {
        this.planDocument = planDocument;
    }

    public DocumentEntity getDocument() {
        return document;
    }

    public void setDocument(DocumentEntity document) {
        this.document = document;
    }
}
