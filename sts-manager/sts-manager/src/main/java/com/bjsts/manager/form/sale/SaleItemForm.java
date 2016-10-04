package com.bjsts.manager.form.sale;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.sale.CustomerEntity;
import com.bjsts.manager.entity.sale.SaleItemEntity;

/**
 * @author jinsheng
 * @since 2016-05-12 16:00
 */
public class SaleItemForm extends AbstractForm {

    private static final long serialVersionUID = 5859168020901454685L;

    private SaleItemEntity saleItem;

    public SaleItemEntity getSaleItem() {
        return saleItem;
    }

    public void setSaleItem(SaleItemEntity saleItem) {
        this.saleItem = saleItem;
    }
}
