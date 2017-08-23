package com.bjsts.manager.entity.stock;

import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.converter.EnableDisableStatusConverter;
import com.bjsts.manager.core.entity.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 库存
 * @author wangzhiliang
 */
@Entity
@Table(name = "sts_stock")
@DynamicInsert
@DynamicUpdate
public class StockEntity extends AbstractEntity {

    private static final long serialVersionUID = -299721038872026718L;

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 供应商产品条目
     */
    @Column(name = "supplier_item_id", nullable = false)
    private Long supplierItemId;

    /**
     * 采购单价
     */
    @Column(name = "unit_price", nullable = false)
    private Long unitPrice;

    /**
     * 库存单价
     */
    @Column(name = "in_bound_unit_price", nullable = false)
    private Long inBoundUnitPrice;

    @Column(nullable = false)
    private Long quantity;

    private String memo;

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

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public EnableDisableStatus getValid() {
        return valid;
    }

    public void setValid(EnableDisableStatus valid) {
        this.valid = valid;
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

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Long getInBoundUnitPrice() {
        return inBoundUnitPrice;
    }

    public void setInBoundUnitPrice(Long inBoundUnitPrice) {
        this.inBoundUnitPrice = inBoundUnitPrice;
    }

    public Long getSupplierItemId() {
        return supplierItemId;
    }

    public void setSupplierItemId(Long supplierItemId) {
        this.supplierItemId = supplierItemId;
    }

    public Long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Long unitPrice) {
        this.unitPrice = unitPrice;
    }
}
