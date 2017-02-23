package com.bjsts.manager.entity.stock;

import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.converter.EnableDisableStatusConverter;
import com.bjsts.manager.core.entity.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 产品入库
 * @author wangzhiliang
 */
@Entity
@Table(name = "sts_product_in_bound")
@DynamicInsert
@DynamicUpdate
public class ProductInBoundEntity extends AbstractEntity {

    private static final long serialVersionUID = 5330934740520345217L;
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, name = "plan_no")
    private String planNo;

    @Column(nullable = false, name = "plan_name")
    private String planName;

    /**
     * 客户名称
     */
    private String company;

    @Column(nullable = false, name = "contract_no")
    private String contractNo;

    @Column(nullable = false, name = "product_name")
    private String productName;

    private Long quantity;

    private String unit;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "in_bound_time")
    private Date inBoundTime;

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

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Date getInBoundTime() {
        return inBoundTime;
    }

    public void setInBoundTime(Date inBoundTime) {
        this.inBoundTime = inBoundTime;
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
}
