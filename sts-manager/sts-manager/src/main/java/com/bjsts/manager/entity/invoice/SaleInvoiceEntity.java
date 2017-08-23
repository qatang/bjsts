package com.bjsts.manager.entity.invoice;

import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.converter.EnableDisableStatusConverter;
import com.bjsts.manager.core.entity.AbstractEntity;
import com.bjsts.manager.enums.converter.invoice.InvoiceCategoryConverter;
import com.bjsts.manager.enums.converter.invoice.InvoiceStatusConverter;
import com.bjsts.manager.enums.invoice.InvoiceCategory;
import com.bjsts.manager.enums.invoice.InvoiceStatus;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 销售发票登记
 * @author wangzhiliang
 */
@Entity
@Table(name = "sts_sale_invoice")
@DynamicInsert
@DynamicUpdate
public class SaleInvoiceEntity extends AbstractEntity {

    private static final long serialVersionUID = 10694714495319751L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, name = "plan_no")
    private String planNo;

    @Column(name = "plan_content")
    private String planContent;

    @Convert(converter = InvoiceCategoryConverter.class)
    @Column(name = "invoice_category", nullable = false)
    private InvoiceCategory invoiceCategory;

    private String customer;

    @Column(nullable = false, name = "invoice_no")
    private String invoiceNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "invoice_date")
    private Date invoiceDate;

    private Long amount;

    private String content;

    @Convert(converter = InvoiceStatusConverter.class)
    @Column(name = "invoice_status", nullable = false)
    private InvoiceStatus invoiceStatus;

    @Column(name = "invoice_url")
    private Long invoiceUrl;

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

    public String getPlanContent() {
        return planContent;
    }

    public void setPlanContent(String planContent) {
        this.planContent = planContent;
    }

    public InvoiceCategory getInvoiceCategory() {
        return invoiceCategory;
    }

    public void setInvoiceCategory(InvoiceCategory invoiceCategory) {
        this.invoiceCategory = invoiceCategory;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public InvoiceStatus getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public Long getInvoiceUrl() {
        return invoiceUrl;
    }

    public void setInvoiceUrl(Long invoiceUrl) {
        this.invoiceUrl = invoiceUrl;
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

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }
}
