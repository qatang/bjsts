package com.bjsts.manager.form.sale;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.sale.PlanEntity;

/**
 * @author jinsheng
 * @since 2016-05-12 16:00
 */
public class ProductOrderForm extends AbstractForm {

    private static final long serialVersionUID = 5859168020901454685L;

    private PlanEntity productOrder;

    private DocumentEntity document;

    public PlanEntity getProductOrder() {
        return productOrder;
    }

    public void setProductOrder(PlanEntity productOrder) {
        this.productOrder = productOrder;
    }

    public DocumentEntity getDocument() {
        return document;
    }

    public void setDocument(DocumentEntity document) {
        this.document = document;
    }
}
