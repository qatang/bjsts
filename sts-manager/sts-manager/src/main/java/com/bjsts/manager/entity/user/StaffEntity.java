package com.bjsts.manager.entity.user;

import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.YesNoStatus;
import com.bjsts.core.enums.converter.EnableDisableStatusConverter;
import com.bjsts.core.enums.converter.YesNoStatusConverter;
import com.bjsts.manager.core.entity.AbstractEntity;
import com.bjsts.manager.enums.converter.user.EducationTypeConverter;
import com.bjsts.manager.enums.converter.user.MaleTypeConverter;
import com.bjsts.manager.enums.converter.user.OnJobStatusConverter;
import com.bjsts.manager.enums.converter.user.PolityTypeConverter;
import com.bjsts.manager.enums.user.EducationType;
import com.bjsts.manager.enums.user.MaleType;
import com.bjsts.manager.enums.user.OnJobStatus;
import com.bjsts.manager.enums.user.PolityType;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 雇员
 * @author wangzhiliang
 */
@Entity
@Table(name = "sts_staff")
@DynamicInsert
@DynamicUpdate
public class StaffEntity extends AbstractEntity {

    private static final long serialVersionUID = -5506202072645887173L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "staff_no", nullable = false)
    private String staffNo;

    @Column(name = "real_name", nullable = false)
    private String realName;

    @Column(name = "department_id", nullable = false)
    private Long departmentId;

    @Convert(converter = MaleTypeConverter.class)
    @Column(nullable = false, name = "male_type")
    private MaleType maleType = MaleType.MALE;

    @Column(name = "position")
    private String position;

    @Column(name = "id_card")
    private String idCard;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "entry_time")
    private Date entryTime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "departure_time")
    private Date departureTime;

    @Convert(converter = EducationTypeConverter.class)
    @Column(nullable = false, name = "education_type")
    private EducationType educationType;

    @Convert(converter = PolityTypeConverter.class)
    @Column(nullable = false, name = "polity_type")
    private PolityType polityType;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "birthday")
    private Date birthday;

    @Convert(converter = OnJobStatusConverter.class)
    @Column(nullable = false, name = "on_job")
    private OnJobStatus onJob;

    @Column(length = 32)
    private String mobile;

    @Column(length = 128)
    private String email;

    @Column(length = 256)
    private String memo;

    /**
     * 是否参加社保
     */
    @Convert(converter = YesNoStatusConverter.class)
    @Column(name = "social_security", nullable = false)
    private YesNoStatus socialSecurity = YesNoStatus.YES;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false, nullable = false)
    private Date createdTime = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time")
    private Date updatedTime;

    @Convert(converter = EnableDisableStatusConverter.class)
    @Column(nullable = false)
    private EnableDisableStatus valid = EnableDisableStatus.ENABLE;

    @Transient
    private String departmentName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStaffNo() {
        return staffNo;
    }

    public void setStaffNo(String staffNo) {
        this.staffNo = staffNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public MaleType getMaleType() {
        return maleType;
    }

    public void setMaleType(MaleType maleType) {
        this.maleType = maleType;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public EducationType getEducationType() {
        return educationType;
    }

    public void setEducationType(EducationType educationType) {
        this.educationType = educationType;
    }

    public PolityType getPolityType() {
        return polityType;
    }

    public void setPolityType(PolityType polityType) {
        this.polityType = polityType;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public OnJobStatus getOnJob() {
        return onJob;
    }

    public void setOnJob(OnJobStatus onJob) {
        this.onJob = onJob;
    }

    public EnableDisableStatus getValid() {
        return valid;
    }

    public void setValid(EnableDisableStatus valid) {
        this.valid = valid;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public YesNoStatus getSocialSecurity() {
        return socialSecurity;
    }

    public void setSocialSecurity(YesNoStatus socialSecurity) {
        this.socialSecurity = socialSecurity;
    }
}
