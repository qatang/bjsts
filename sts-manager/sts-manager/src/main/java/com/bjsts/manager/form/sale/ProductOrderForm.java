package com.bjsts.manager.form.sale;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.sale.ContractEntity;
import com.bjsts.manager.entity.sale.ProductOrderEntity;

/**
 * @author jinsheng
 * @since 2016-05-12 16:00
 */
public class ProductOrderForm extends AbstractForm {

    private static final long serialVersionUID = 5859168020901454685L;

    private ProductOrderEntity productOrder;

    public ProductOrderEntity getProductOrder() {
        return productOrder;
    }

    public void setProductOrder(ProductOrderEntity productOrder) {
        this.productOrder = productOrder;
    }
}
