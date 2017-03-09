package com.bjsts.manager.entity.sale;

import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.converter.EnableDisableStatusConverter;
import com.bjsts.manager.core.entity.AbstractEntity;
import com.bjsts.manager.enums.converter.sale.ContractStatusConverter;
import com.bjsts.manager.enums.sale.ContractStatus;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 合同
 * @author jinsheng
 * @since 2016-04-28 13:48
 */
@Entity
@Table(name = "sts_contract")
@DynamicInsert
@DynamicUpdate
public class ContractEntity extends AbstractEntity {

    private static final long serialVersionUID = -299721038872026718L;

    public static final String SEQ_ID_GENERATOR = "contract";

    public static final String SEQ_ID_PREFIX_AUTO = "STSC";

    public static final String SEQ_ID_PREFIX = "STSCM";

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 项目编码，格式：sts20161016001
     */
    @Column(name = "plan_no")
    private String planNo;

    /**
     * 项目名称
     */
    @Column(name = "plan_name")
    private String planName;

    @Column(name = "contract_no", nullable = false)
    private String contractNo;

    @Convert(converter = ContractStatusConverter.class)
    @Column(nullable = false)
    private ContractStatus status;

    /**
     * 客户名称
     */
    @Column(nullable = false)
    private String company;

    /**
     * 联系人
     */
    @Column(nullable = false)
    private String linkman;

    /**
     * 签订时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sign_time")
    private Date signTime;

    /**
     * 质保日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "quality_time")
    private Date qualityTime;

    /**
     * 合同金额
     */
    private Long amount;

    @Column(name = "contract_url")
    private Long contractUrl;

    /**
     * 签订人
     */
    private String sign;

    /**
     * 质保金额
     */
    @Column(name = "quality_amount")
    private Long qualityAmount;

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
     * 已付金额
     */
    @Transient
    private Long payedAmount;

    /**
     * 开票金额
     */
    @Transient
    private Long invoiceAmount;

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

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public ContractStatus getStatus() {
        return status;
    }

    public void setStatus(ContractStatus status) {
        this.status = status;
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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Date getSignTime() {
        return signTime;
    }

    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }

    public Date getQualityTime() {
        return qualityTime;
    }

    public void setQualityTime(Date qualityTime) {
        this.qualityTime = qualityTime;
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

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public Long getContractUrl() {
        return contractUrl;
    }

    public void setContractUrl(Long contractUrl) {
        this.contractUrl = contractUrl;
    }

    public EnableDisableStatus getValid() {
        return valid;
    }

    public void setValid(EnableDisableStatus valid) {
        this.valid = valid;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Long getQualityAmount() {
        return qualityAmount;
    }

    public void setQualityAmount(Long qualityAmount) {
        this.qualityAmount = qualityAmount;
    }

    public Long getPayedAmount() {
        return payedAmount;
    }

    public void setPayedAmount(Long payedAmount) {
        this.payedAmount = payedAmount;
    }

    public Long getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(Long invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }
}
