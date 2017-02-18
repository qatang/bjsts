package com.bjsts.manager.form.purchase;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.purchase.NormalPurchaseEntity;

/**
 * @author jinsheng
 * @since 2016-05-12 16:00
 */
public class NormalPurchaseForm extends AbstractForm {

    private static final long serialVersionUID = 5079624005156872672L;

    private NormalPurchaseEntity normalPurchase;

    private Double amount;

    public NormalPurchaseEntity getNormalPurchase() {
        return normalPurchase;
    }

    public void setNormalPurchase(NormalPurchaseEntity normalPurchase) {
        this.normalPurchase = normalPurchase;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
