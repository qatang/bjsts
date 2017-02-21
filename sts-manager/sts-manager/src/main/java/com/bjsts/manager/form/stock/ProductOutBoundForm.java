package com.bjsts.manager.form.stock;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.stock.ProductOutBoundEntity;

/**
 * @author wangzhiliang
 */
public class ProductOutBoundForm extends AbstractForm {

    private static final long serialVersionUID = 1171900766726601463L;

    private ProductOutBoundEntity productOutBound;

    public ProductOutBoundEntity getProductOutBound() {
        return productOutBound;
    }

    public void setProductOutBound(ProductOutBoundEntity productOutBound) {
        this.productOutBound = productOutBound;
    }
}
