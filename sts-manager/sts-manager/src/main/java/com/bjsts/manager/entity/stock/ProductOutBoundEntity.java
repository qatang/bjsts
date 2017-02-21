package com.bjsts.manager.entity.stock;

import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.converter.EnableDisableStatusConverter;
import com.bjsts.manager.core.entity.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 产品出库
 * @author wangzhiliang
 */
@Entity
@Table(name = "sts_product_out_bound")
@DynamicInsert
@DynamicUpdate
public class ProductOutBoundEntity extends AbstractEntity {

    private static final long serialVersionUID = 1098481353419212763L;

    public static final String SEQ_ID_GENERATOR = "productOutBound";

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "product_out_bound_no", nullable = false)
    private String productOutBoundNo;

    @Column(nullable = false)
    private String company;

    @Column(name = "plan_name", nullable = false)
    private String planName;

    @Column(nullable = false)
    private String linkman;

    @Column(nullable = false)
    private String mobile;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "delivery_date", nullable = false)
    private Date deliveryDate;

    @Column(nullable = false, name = "product_name")
    private String productName;

    @Column(nullable = false, name = "product_model")
    private String productModel;

    @Column(nullable = false)
    private Long quantity;

    @Column(nullable = false)
    private String unit;

    @Column(nullable = false, name = "single_amount")
    private Long singleAmount;

    @Column(nullable = false, name = "total_amount")
    private Long totalAmount;

    @Column(nullable = false)
    private String operator;

    @Column(nullable = false)
    private String shipper;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Long getSingleAmount() {
        return singleAmount;
    }

    public void setSingleAmount(Long singleAmount) {
        this.singleAmount = singleAmount;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProductOutBoundNo() {
        return productOutBoundNo;
    }

    public void setProductOutBoundNo(String productOutBoundNo) {
        this.productOutBoundNo = productOutBoundNo;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
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
