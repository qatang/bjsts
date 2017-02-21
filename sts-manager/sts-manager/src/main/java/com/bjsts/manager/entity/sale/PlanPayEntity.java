package com.bjsts.manager.entity.sale;

import com.bjsts.manager.core.entity.AbstractEntity;
import com.bjsts.manager.enums.converter.invoice.MakeOutInvoiceStatusConverter;
import com.bjsts.manager.enums.invoice.MakeOutInvoiceStatus;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 方案付款记录
 * @author wangzhiliang
 */
@Entity
@Table(name = "sts_plan_pay")
@DynamicInsert
@DynamicUpdate
public class PlanPayEntity extends AbstractEntity {

    private static final long serialVersionUID = -299721038872026718L;

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 项目编码，格式：sts20161016001
     */
    @Column(name = "plan_no", nullable = false)
    private String planNo;

    /**
     * 合同编号，格式：sts20161016001
     */
    @Column(name = "contract_no", nullable = false)
    private String contractNo;

    @Convert(converter = MakeOutInvoiceStatusConverter.class)
    @Column(name = "invoice_status", nullable = false)
    private MakeOutInvoiceStatus makeOutInvoiceStatus;

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
     * 经办人
     */
    @Column(nullable = false)
    private String operator;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false, nullable = false)
    private Date createdTime = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time")
    private Date updatedTime;

    @Transient
    private String planName;

    @Transient
    private String company;

    @Transient
    private Long contractAmount;

    @Transient
    private Long payedAmount;

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

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public MakeOutInvoiceStatus getMakeOutInvoiceStatus() {
        return makeOutInvoiceStatus;
    }

    public void setMakeOutInvoiceStatus(MakeOutInvoiceStatus makeOutInvoiceStatus) {
        this.makeOutInvoiceStatus = makeOutInvoiceStatus;
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

    public Long getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Long contractAmount) {
        this.contractAmount = contractAmount;
    }

    public Long getPayedAmount() {
        return payedAmount;
    }

    public void setPayedAmount(Long payedAmount) {
        this.payedAmount = payedAmount;
    }
}
