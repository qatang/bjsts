package com.bjsts.manager.form.purchase;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.purchase.PurchaseEntity;
import com.bjsts.manager.entity.purchase.PurchaseItemEntity;

import java.util.List;

/**
 * @author jinsheng
 * @since 2016-05-12 16:00
 */
public class PurchaseForm extends AbstractForm {

    private static final long serialVersionUID = 5859168020901454685L;

    private PurchaseEntity purchase;

    private DocumentEntity document = new DocumentEntity();

    private Double totalAmount;

    private Double payedAmount;

    private Double unPayedAmount;

    private PurchaseItemEntity purchaseItem;

    public PurchaseEntity getPurchase() {
        return purchase;
    }

    public List<PurchaseItemEntity> purchaseItemList;

    public void setPurchase(PurchaseEntity purchase) {
        this.purchase = purchase;
    }

    public DocumentEntity getDocument() {
        return document;
    }

    public void setDocument(DocumentEntity document) {
        this.document = document;
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

    public PurchaseItemEntity getPurchaseItem() {
        return purchaseItem;
    }

    public void setPurchaseItem(PurchaseItemEntity purchaseItem) {
        this.purchaseItem = purchaseItem;
    }

    public List<PurchaseItemEntity> getPurchaseItemList() {
        return purchaseItemList;
    }

    public void setPurchaseItemList(List<PurchaseItemEntity> purchaseItemList) {
        this.purchaseItemList = purchaseItemList;
    }
}
