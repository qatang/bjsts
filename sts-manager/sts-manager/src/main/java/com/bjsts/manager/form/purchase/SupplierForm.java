package com.bjsts.manager.form.purchase;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.purchase.SupplierEntity;

/**
 * @author wangzhiliang
 */
public class SupplierForm extends AbstractForm {

    private static final long serialVersionUID = -1507725501160247240L;
    private SupplierEntity supplier;

    public SupplierEntity getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierEntity supplier) {
        this.supplier = supplier;
    }
}
