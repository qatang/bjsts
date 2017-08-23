package com.bjsts.manager.entity.purchase;

import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.converter.EnableDisableStatusConverter;
import com.bjsts.manager.core.entity.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 付款记录
 * @author wangzhiliang
 */
@Entity
@Table(name = "sts_purchase_pay_item")
@DynamicInsert
@DynamicUpdate
public class PurchasePayItemEntity extends AbstractEntity {

    private static final long serialVersionUID = -5158570353453638928L;

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 付款编号
     */
    @Column(name = "purchase_pay_id", nullable = false)
    private Long purchasePayId;

    /**
     * 采购编号，格式：sts20161016001
     */
    @Column(name = "purchase_no", nullable = false)
    private String purchaseNo;

    /**
     * 本次付款金额
     */
    @Column(nullable = false)
    private Long amount;

    /**
     * 本次付款方式
     */
    @Column(name = "pay_model", nullable = false)
    private String payModel;

    /**
     * 本次付款时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "pay_time", nullable = false)
    private Date payTime;


    /**
     * 开票金额
     */
    @Column(name = "invoice_amount", nullable = false)
    private Long invoiceAmount;


    /**
     * 经办人
     */
    @Column(name = "operator_id", nullable = false)
    private Long operatorId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false, nullable = false)
    private Date createdTime = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time")
    private Date updatedTime;

    @Convert(converter = EnableDisableStatusConverter.class)
    @Column(nullable = false)
    private EnableDisableStatus valid = EnableDisableStatus.ENABLE;

    /**
     * 经办人姓名
     */
    @Transient
    private String operator;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getPayModel() {
        return payModel;
    }

    public void setPayModel(String payModel) {
        this.payModel = payModel;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Long getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    public Long getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(Long invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    public Long getPurchasePayId() {
        return purchasePayId;
    }

    public void setPurchasePayId(Long purchasePayId) {
        this.purchasePayId = purchasePayId;
    }

    public EnableDisableStatus getValid() {
        return valid;
    }

    public void setValid(EnableDisableStatus valid) {
        this.valid = valid;
    }
}
