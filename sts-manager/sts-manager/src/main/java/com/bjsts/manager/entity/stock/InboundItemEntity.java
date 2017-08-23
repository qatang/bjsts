package com.bjsts.manager.entity.stock;

import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.converter.EnableDisableStatusConverter;
import com.bjsts.manager.core.entity.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 物料入库记录
 * @author wangzhiliang
 */
@Entity
@Table(name = "sts_in_bound_item")
@DynamicInsert
@DynamicUpdate
public class InboundItemEntity extends AbstractEntity {

    private static final long serialVersionUID = 8267503402134769660L;

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 入库单
     */
    @Column(name = "in_bound_id", nullable = false)
    private Long inBoundId;

    /**
     * 采购单条目
     */
    @Column(name = "purchase_item_id", nullable = false)
    private Long purchaseItemId;

    /**
     * 供应商产品条目条目
     */
    @Column(name = "supplier_item_id", nullable = false)
    private Long supplierItemId;

    /**
     * 入库单价
     */
    @Column(name = "in_bound_unit_price", nullable = false)
    private Long inBoundInitPrice;

    @Convert(converter = EnableDisableStatusConverter.class)
    @Column(nullable = false)
    private EnableDisableStatus valid = EnableDisableStatus.ENABLE;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false, nullable = false)
    private Date createdTime = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time")
    private Date updatedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInBoundId() {
        return inBoundId;
    }

    public void setInBoundId(Long inBoundId) {
        this.inBoundId = inBoundId;
    }

    public Long getPurchaseItemId() {
        return purchaseItemId;
    }

    public void setPurchaseItemId(Long purchaseItemId) {
        this.purchaseItemId = purchaseItemId;
    }

    public Long getInBoundInitPrice() {
        return inBoundInitPrice;
    }

    public void setInBoundInitPrice(Long inBoundInitPrice) {
        this.inBoundInitPrice = inBoundInitPrice;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Long getSupplierItemId() {
        return supplierItemId;
    }

    public void setSupplierItemId(Long supplierItemId) {
        this.supplierItemId = supplierItemId;
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
