package com.bjsts.manager.entity.stock;

import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.YesNoStatus;
import com.bjsts.core.enums.converter.EnableDisableStatusConverter;
import com.bjsts.core.enums.converter.YesNoStatusConverter;
import com.bjsts.manager.core.entity.AbstractEntity;
import com.bjsts.manager.entity.purchase.PurchaseEntity;
import com.bjsts.manager.entity.purchase.PurchaseItemEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 物料入库单
 * @author wangzhiliang
 */
@Entity
@Table(name = "sts_in_bound")
@DynamicInsert
@DynamicUpdate
public class InBoundEntity extends AbstractEntity {

    private static final long serialVersionUID = 5530548617185646587L;

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 采购单编号
     */
    @Column(name = "purchase_no", nullable = false)
    private String purchaseNo;

    /**
     * 接收人
     */
    private String sendee;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sendee_time")
    private Date sendeeTime;

    /**
     * 接收人
     */
    private String memo;

    /**
     * 是否核对
     */
    @Convert(converter = YesNoStatusConverter.class)
    @Column(nullable = false)
    private YesNoStatus verify = YesNoStatus.NO;

    @Convert(converter = EnableDisableStatusConverter.class)
    @Column(nullable = false)
    private EnableDisableStatus valid = EnableDisableStatus.ENABLE;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false, nullable = false)
    private Date createdTime = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time")
    private Date updatedTime;

    @Transient
    private PurchaseEntity purchase;

    @Transient
    private List<PurchaseItemEntity> purchaseItemList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    public String getSendee() {
        return sendee;
    }

    public void setSendee(String sendee) {
        this.sendee = sendee;
    }

    public Date getSendeeTime() {
        return sendeeTime;
    }

    public void setSendeeTime(Date sendeeTime) {
        this.sendeeTime = sendeeTime;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public PurchaseEntity getPurchase() {
        return purchase;
    }

    public void setPurchase(PurchaseEntity purchase) {
        this.purchase = purchase;
    }

    public YesNoStatus getVerify() {
        return verify;
    }

    public void setVerify(YesNoStatus verify) {
        this.verify = verify;
    }

    public EnableDisableStatus getValid() {
        return valid;
    }

    public void setValid(EnableDisableStatus valid) {
        this.valid = valid;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }
}
