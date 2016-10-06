package com.bjsts.manager.form.purchase;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.purchase.PurchaseEntity;
import com.bjsts.manager.entity.sale.ContractEntity;

/**
 * @author jinsheng
 * @since 2016-05-12 16:00
 */
public class PurchaseForm extends AbstractForm {

    private static final long serialVersionUID = 5859168020901454685L;

    private PurchaseEntity purchase;

    public PurchaseEntity getPurchase() {
        return purchase;
    }

    public void setPurchase(PurchaseEntity purchase) {
        this.purchase = purchase;
    }
}
