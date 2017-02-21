package com.bjsts.manager.form.stock;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.stock.OutBoundEntity;

/**
 * @author wangzhiliang
 */
public class OutBoundForm extends AbstractForm {

    private static final long serialVersionUID = 8094809913000215402L;

    private OutBoundEntity outBound;

    public OutBoundEntity getOutBound() {
        return outBound;
    }

    public void setOutBound(OutBoundEntity outBound) {
        this.outBound = outBound;
    }
}
