package com.bjsts.manager.form.purchase;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.purchase.SupplierEntity;
import com.bjsts.manager.entity.purchase.SupplierItemEntity;

/**
 * @author wangzhiliang
 */
public class SupplierForm extends AbstractForm {

    private static final long serialVersionUID = -1507725501160247240L;
    private SupplierEntity supplier;

    private SupplierItemEntity supplierItem;

    public SupplierEntity getSupplier() {
        return supplier;
    }

    public void setSupplier(SupplierEntity supplier) {
        this.supplier = supplier;
    }

    public SupplierItemEntity getSupplierItem() {
        return supplierItem;
    }

    public void setSupplierItem(SupplierItemEntity supplierItem) {
        this.supplierItem = supplierItem;
    }
}
