package com.bjsts.manager.form.purchase;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.purchase.ExpressEntity;

/**
 * @author wangzhiliang
 */
public class ExpressForm extends AbstractForm {

    private static final long serialVersionUID = -3245457483926207250L;

    private ExpressEntity express;

    private Double cost;

    public ExpressEntity getExpress() {
        return express;
    }

    public void setExpress(ExpressEntity express) {
        this.express = express;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
