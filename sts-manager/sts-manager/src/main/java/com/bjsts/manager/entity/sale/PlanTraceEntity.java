package com.bjsts.manager.entity.sale;

import com.bjsts.manager.core.entity.AbstractEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 项目进度
 * @author jinsheng
 * @since 2016-04-28 13:48
 */
@Entity
@Table(name = "sts_plan_trace")
@DynamicInsert
@DynamicUpdate
public class PlanTraceEntity extends AbstractEntity {

    private static final long serialVersionUID = -299721038872026718L;

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 项目编码，格式：sts20161016001
     */
    @Column(name = "plan_no", nullable = false)
    private String planNo;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "real_name")
    private String realName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "trace_time", updatable = false, nullable = false)
    private Date traceTime = new Date();

    private String description;

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

    public String getPlanNo() {
        return planNo;
    }

    public void setPlanNo(String planNo) {
        this.planNo = planNo;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getTraceTime() {
        return traceTime;
    }

    public void setTraceTime(Date traceTime) {
        this.traceTime = traceTime;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
