package com.bjsts.manager.form.sale;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.sale.PlanTraceEntity;

/**
 * @author jinsheng
 * @since 2016-05-12 16:00
 */
public class SaleItemForm extends AbstractForm {

    private static final long serialVersionUID = 5859168020901454685L;

    private PlanTraceEntity saleItem;

    public PlanTraceEntity getSaleItem() {
        return saleItem;
    }

    public void setSaleItem(PlanTraceEntity saleItem) {
        this.saleItem = saleItem;
    }
}
