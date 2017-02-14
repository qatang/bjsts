package com.bjsts.manager.form.purchase;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.purchase.PurchaseEntity;

/**
 * @author jinsheng
 * @since 2016-05-12 16:00
 */
public class PurchaseForm extends AbstractForm {

    private static final long serialVersionUID = 5859168020901454685L;

    private PurchaseEntity purchase;

    private String purchaseContractUrl;

    private Double totalAmount;

    private Double payedAmount;

    private Double unPayedAmount;

    public PurchaseEntity getPurchase() {
        return purchase;
    }

    public void setPurchase(PurchaseEntity purchase) {
        this.purchase = purchase;
    }

    public String getPurchaseContractUrl() {
        return purchaseContractUrl;
    }

    public void setPurchaseContractUrl(String purchaseContractUrl) {
        this.purchaseContractUrl = purchaseContractUrl;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Double getPayedAmount() {
        return payedAmount;
    }

    public void setPayedAmount(Double payedAmount) {
        this.payedAmount = payedAmount;
    }

    public Double getUnPayedAmount() {
        return unPayedAmount;
    }

    public void setUnPayedAmount(Double unPayedAmount) {
        this.unPayedAmount = unPayedAmount;
    }
}
