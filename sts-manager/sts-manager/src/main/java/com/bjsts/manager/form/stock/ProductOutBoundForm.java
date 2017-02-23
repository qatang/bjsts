package com.bjsts.manager.form.stock;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.stock.ProductOutBoundEntity;

/**
 * @author wangzhiliang
 */
public class ProductOutBoundForm extends AbstractForm {

    private static final long serialVersionUID = 1171900766726601463L;

    private ProductOutBoundEntity productOutBound;

    private Double singleAmount;

    public ProductOutBoundEntity getProductOutBound() {
        return productOutBound;
    }

    public void setProductOutBound(ProductOutBoundEntity productOutBound) {
        this.productOutBound = productOutBound;
    }

    public Double getSingleAmount() {
        return singleAmount;
    }

    public void setSingleAmount(Double singleAmount) {
        this.singleAmount = singleAmount;
    }
}
