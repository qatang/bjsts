package com.bjsts.manager.form.stock;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.stock.ProductInBoundEntity;

/**
 * @author wangzhiliang
 */
public class ProductInBoundForm extends AbstractForm {

    private static final long serialVersionUID = 4141287162804119849L;

    private ProductInBoundEntity productInBound;

    public ProductInBoundEntity getProductInBound() {
        return productInBound;
    }

    public void setProductInBound(ProductInBoundEntity productInBound) {
        this.productInBound = productInBound;
    }
}
