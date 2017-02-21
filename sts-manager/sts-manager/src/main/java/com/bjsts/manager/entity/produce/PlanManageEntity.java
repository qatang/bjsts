package com.bjsts.manager.entity.produce;

import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.converter.EnableDisableStatusConverter;
import com.bjsts.manager.core.entity.AbstractEntity;
import com.bjsts.manager.enums.converter.produce.PlanExecuteStatusConverter;
import com.bjsts.manager.enums.produce.PlanExecuteStatus;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 项目管理
 * @author wangzhiliang
 */
@Entity
@Table(name = "sts_plan_manage")
@DynamicInsert
@DynamicUpdate
public class PlanManageEntity extends AbstractEntity {

    private static final long serialVersionUID = -39428506756196059L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, name = "plan_no")
    private String planNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expect_date")
    private Date expectDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "actual_date")
    private Date actualDate;

    @Convert(converter = PlanExecuteStatusConverter.class)
    @Column(name = "plan_execute_status", nullable = false)
    private PlanExecuteStatus planExecuteStatus;

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

    public Date getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(Date expectDate) {
        this.expectDate = expectDate;
    }

    public Date getActualDate() {
        return actualDate;
    }

    public void setActualDate(Date actualDate) {
        this.actualDate = actualDate;
    }

    public PlanExecuteStatus getPlanExecuteStatus() {
        return planExecuteStatus;
    }

    public void setPlanExecuteStatus(PlanExecuteStatus planExecuteStatus) {
        this.planExecuteStatus = planExecuteStatus;
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
