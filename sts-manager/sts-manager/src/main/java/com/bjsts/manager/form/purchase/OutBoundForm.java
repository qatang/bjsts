package com.bjsts.manager.form.purchase;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.purchase.OutBoundEntity;

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
