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
@Table(name = "sts_purchase_pay")
@DynamicInsert
@DynamicUpdate
public class PurchasePayEntity extends AbstractEntity {

    private static final long serialVersionUID = -7673848044186373913L;

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 采购单编号，格式：sts20161016001
     */
    @Column(name = "purchase_no", nullable = false)
    private String purchaseNo;

    /**
     * 总付款金额
     */
    @Column(nullable = false)
    private Long amount;

    /**
     * 已付金额
     */
    @Column(name = "payed_amount", nullable = false)
    private Long payedAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false, nullable = false)
    private Date createdTime = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time")
    private Date updatedTime;

    @Convert(converter = EnableDisableStatusConverter.class)
    @Column(nullable = false)
    private EnableDisableStatus valid = EnableDisableStatus.ENABLE;

    @Transient
    private Long unPayedAmount;

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

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    public Long getPayedAmount() {
        return payedAmount;
    }

    public void setPayedAmount(Long payedAmount) {
        this.payedAmount = payedAmount;
    }

    public EnableDisableStatus getValid() {
        return valid;
    }

    public void setValid(EnableDisableStatus valid) {
        this.valid = valid;
    }

    public Long getUnPayedAmount() {
        return amount - payedAmount;
    }
}
