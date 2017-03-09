package com.bjsts.manager.entity.sale;

import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.converter.EnableDisableStatusConverter;
import com.bjsts.manager.core.entity.AbstractEntity;
import com.bjsts.manager.enums.converter.sale.PlanStatusConverter;
import com.bjsts.manager.enums.converter.sale.PlanTypeConverter;
import com.bjsts.manager.enums.converter.sale.SourceTypeConverter;
import com.bjsts.manager.enums.sale.PlanStatus;
import com.bjsts.manager.enums.sale.PlanType;
import com.bjsts.manager.enums.sale.SourceType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 项目方案跟踪信息
 *
 * @author jinsheng
 * @since 2016-04-28 13:48
 */
@Entity
@Table(name = "sts_plan")
@DynamicInsert
@DynamicUpdate
public class PlanEntity extends AbstractEntity {

    private static final long serialVersionUID = -299721038872026718L;

    public static final String SEQ_ID_GENERATOR = "plan";

    public static final String SEQ_ID_PREFIX = "STSM";

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 项目编码，格式：sts20161016001
     */
    @Column(name = "plan_no", nullable = false)
    private String planNo;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目地点
     */
    private String location;

    /**
     * 项目类型
     */
    @Convert(converter = PlanTypeConverter.class)
    @Column(name = "plan_type", nullable = false)
    private PlanType planType;

    /**
     * 信息来源
     */
    @Convert(converter = SourceTypeConverter.class)
    @Column(name = "source_type", nullable = false)
    private SourceType sourceType;

    /**
     * 询价日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "price_time")
    private Date priceTime;

    /**
     * 联系人
     */
    private String linkman;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 联系单位
     */
    private String company;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 项目说明
     */
    private String description;

    /**
     * 客户提供的项目资料
     */
    @Column(name = "document_id")
    private Long documentId;

    /**
     * 项目状态
     */
    @Convert(converter = PlanStatusConverter.class)
    @Column(name = "plan_status", nullable = false)
    private PlanStatus planStatus;

    /**
     * 备案登记人
     */
    private String booker;

    /**
     * 备案登记人用户编码
     */
    @Column(name = "booker_id", nullable = false)
    private Long bookerId;

    /************************************************************************************
     *
     * 报价相关
     *
     ************************************************************************************/

    /**
     * 报价员
     */
    private String quoter;

    /**
     * 报价员
     */
    @Column(name = "quote_id", nullable = false)
    private Long quoterId;

    /**
     * 报价时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "quote_time")
    private Date quoteTime;

    /**
     * 报价材料 文档编码
     */
    @Column(name = "quote_file_id")
    private Long quoteFileId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false, nullable = false)
    private Date createdTime = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time")
    private Date updatedTime;

    /**
     * 合同总金额
     */
    @Column(name = "contract_amount")
    private Long contractAmount;

    /**
     * 付款总金额
     */
    @Column(name = "pay_amount")
    private Long payAmount;

    /**
     * 发票总金额
     */
    @Column(name = "receipt_amount")
    private Long receiptAmount;

    /**
     * 预计交付日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expect_time")
    private Date expectTime;

    /**
     * 实际完成日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "complete_time")
    private Date completeTime;

    /**
     * 出厂交付日期
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "deliver_time")
    private Date deliverTime;

    @Convert(converter = EnableDisableStatusConverter.class)
    @Column(nullable = false)
    private EnableDisableStatus valid = EnableDisableStatus.ENABLE;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public PlanStatus getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(PlanStatus planStatus) {
        this.planStatus = planStatus;
    }

    public PlanType getPlanType() {
        return planType;
    }

    public void setPlanType(PlanType planType) {
        this.planType = planType;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    public Date getPriceTime() {
        return priceTime;
    }

    public void setPriceTime(Date priceTime) {
        this.priceTime = priceTime;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(Long contractAmount) {
        this.contractAmount = contractAmount;
    }

    public Long getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Long payAmount) {
        this.payAmount = payAmount;
    }

    public Long getReceiptAmount() {
        return receiptAmount;
    }

    public void setReceiptAmount(Long receiptAmount) {
        this.receiptAmount = receiptAmount;
    }

    public Date getExpectTime() {
        return expectTime;
    }

    public void setExpectTime(Date expectTime) {
        this.expectTime = expectTime;
    }

    public Date getCompleteTime() {
        return completeTime;
    }

    public void setCompleteTime(Date completeTime) {
        this.completeTime = completeTime;
    }

    public Date getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    public EnableDisableStatus getValid() {
        return valid;
    }

    public void setValid(EnableDisableStatus valid) {
        this.valid = valid;
    }

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getQuoter() {
        return quoter;
    }

    public void setQuoter(String quoter) {
        this.quoter = quoter;
    }

    public Date getQuoteTime() {
        return quoteTime;
    }

    public void setQuoteTime(Date quoteTime) {
        this.quoteTime = quoteTime;
    }

    public Long getQuoteFileId() {
        return quoteFileId;
    }

    public void setQuoteFileId(Long quoteFileId) {
        this.quoteFileId = quoteFileId;
    }

    public String getBooker() {
        return booker;
    }

    public void setBooker(String booker) {
        this.booker = booker;
    }

    public Long getBookerId() {
        return bookerId;
    }

    public void setBookerId(Long bookerId) {
        this.bookerId = bookerId;
    }

    public Long getQuoterId() {
        return quoterId;
    }

    public void setQuoterId(Long quoterId) {
        this.quoterId = quoterId;
    }
}
