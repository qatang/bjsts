package com.bjsts.manager.form.stock;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.stock.InBoundEntity;

/**
 * @author wangzhiliang
 */
public class InBoundForm extends AbstractForm {

    private static final long serialVersionUID = -7617934752861075442L;

    private InBoundEntity inBound;

    public InBoundEntity getInBound() {
        return inBound;
    }

    public void setInBound(InBoundEntity inBound) {
        this.inBound = inBound;
    }
}
