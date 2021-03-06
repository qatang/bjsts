package com.bjsts.manager.entity.purchase;

import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.YesNoStatus;
import com.bjsts.core.enums.converter.EnableDisableStatusConverter;
import com.bjsts.core.enums.converter.YesNoStatusConverter;
import com.bjsts.manager.core.entity.AbstractEntity;
import com.bjsts.manager.entity.stock.InBoundEntity;
import com.bjsts.manager.enums.converter.invoice.MakeOutInvoiceStatusConverter;
import com.bjsts.manager.enums.invoice.MakeOutInvoiceStatus;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 物料采购单
 * @author wangzhiliang
 */
@Entity
@Table(name = "sts_purchase")
@DynamicInsert
@DynamicUpdate
public class PurchaseEntity extends AbstractEntity {

    private static final long serialVersionUID = -299721038872026718L;

    public static final String SEQ_ID_GENERATOR = "purchase";

    public static final String SEQ_ID_PREFIX = "STSMP";

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 采购单编号
     */
    @Column(name = "purchase_no", nullable = false)
    private String purchaseNo;

    /**
     * 采购申请人
     */
    private String  proposer;

    /**
     * 采购负责人
     */
    private String operator;

    /**
     * 供应商信息
     */
    @Column(name = "supplier_id", nullable = false)
    private Long supplierId;

    /**
     * 采购日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "purchase_time")
    private Date purchaseTime;

    /**
     * 采购金额
     */
    @Column(nullable = false, name = "total_amount")
    private Long totalAmount;

    /**
     * 已付金额
     */
    @Column(name = "payed_amount")
    private Long payedAmount;

    /**
     * 未付金额
     */
    @Column(name = "un_payed_amount")
    private Long unPayedAmount;

    @Convert(converter = MakeOutInvoiceStatusConverter.class)
    @Column(name = "invoice_status", nullable = false)
    private MakeOutInvoiceStatus makeOutInvoiceStatus;

    @Column(name = "purchase_contract_url")
    private Long purchaseContractUrl;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false, nullable = false)
    private Date createdTime = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time", nullable = false)
    private Date updatedTime;

    @Convert(converter = EnableDisableStatusConverter.class)
    @Column(nullable = false)
    private EnableDisableStatus valid = EnableDisableStatus.ENABLE;

    /**
     * 是否已入库
     */
    @Convert(converter = YesNoStatusConverter.class)
    @Column(name = "in_bound", nullable = false)
    private YesNoStatus inBound = YesNoStatus.NO;

    @Transient
    private InBoundEntity inBoundEntity;

    @Transient
    private SupplierEntity supplier;

    @Transient
    private List<PurchaseItemEntity> purchaseItemEntityList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
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

    public Long getPayedAmount() {
        return payedAmount;
    }

    public void setPayedAmount(Long payedAmount) {
        this.payedAmount = payedAmount;
    }

    public Long getUnPayedAmount() {
        return unPayedAmount;
    }

    public void setUnPayedAmount(Long unPayedAmount) {
        this.unPayedAmount = unPayedAmount;
    }

    public String getProposer() {
        return proposer;
    }

    public void setProposer(String proposer) {
        this.proposer = proposer;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(Date purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public Long getPurchaseContractUrl() {
        return purchaseContractUrl;
    }

    public void setPurchaseContractUrl(Long purchaseContractUrl) {
        this.purchaseContractUrl = purchaseContractUrl;
    }

    public String getPurchaseNo() {
        return purchaseNo;
    }

    public void setPurchaseNo(String purchaseNo) {
        this.purchaseNo = purchaseNo;
    }

    public MakeOutInvoiceStatus getMakeOutInvoiceStatus() {
        return makeOutInvoiceStatus;
    }

    public void setMakeOutInvoiceStatus(MakeOutInvoiceStatus makeOutInvoiceStatus) {
        this.makeOutInvoiceStatus = makeOutInvoiceStatus;
    }

    public YesNoStatus getInBound() {
        return inBound;
    }

    public void setInBound(YesNoStatus inBound) {
        this.inBound = inBound;
    }

    public InBoundEntity getInBoundEntity() {
        return inBoundEntity;
    }

    public void setInBoundEntity(InBoundEntity inBoundEntity) {
        this.inBoundEntity = inBoundEntity;
    }

    public Long getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public void setSupplier(SupplierEntity supplier) {
        this.supplier = supplier;
    }

    public SupplierEntity getSupplier() {
        return supplier;
    }

    public List<PurchaseItemEntity> getPurchaseItemEntityList() {
        return purchaseItemEntityList;
    }

    public void setPurchaseItemEntityList(List<PurchaseItemEntity> purchaseItemEntityList) {
        this.purchaseItemEntityList = purchaseItemEntityList;
    }
}
