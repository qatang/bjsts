package com.bjsts.manager.form.purchase;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.purchase.PurchasePayEntity;
import com.bjsts.manager.entity.purchase.PurchasePayItemEntity;

/**
 * @author wangzhiliang
 */
public class PurchasePayForm extends AbstractForm {

    private PurchasePayEntity purchasePay;

    private PurchasePayItemEntity purchasePayItem;

    private Double amount;

    private Double invoiceAmount;

    public PurchasePayEntity getPurchasePay() {
        return purchasePay;
    }

    public void setPurchasePay(PurchasePayEntity purchasePay) {
        this.purchasePay = purchasePay;
    }

    public PurchasePayItemEntity getPurchasePayItem() {
        return purchasePayItem;
    }

    public void setPurchasePayItem(PurchasePayItemEntity purchasePayItem) {
        this.purchasePayItem = purchasePayItem;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(Double invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }
}
