package com.bjsts.manager.entity.user;

import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.YesNoStatus;
import com.bjsts.core.enums.converter.EnableDisableStatusConverter;
import com.bjsts.core.enums.converter.YesNoStatusConverter;
import com.bjsts.manager.core.entity.AbstractEntity;
import com.bjsts.manager.enums.EducationType;
import com.bjsts.manager.enums.MaleType;
import com.bjsts.manager.enums.PolityType;
import com.bjsts.manager.enums.converter.EducationTypeConverter;
import com.bjsts.manager.enums.converter.MaleTypeConverter;
import com.bjsts.manager.enums.converter.PolityTypeConverter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author qatang
 * @since 2014-12-19 15:01
 */
@Entity
@Table(name = "sts_user")
@DynamicInsert
@DynamicUpdate
public class UserEntity extends AbstractEntity {
    private static final long serialVersionUID = 1494723713506838837L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(updatable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 64)
    private String salt;

    @Column(nullable = false, length = 128)
    private String email;

    @Column(length = 32)
    private String mobile;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false, nullable = false)
    private Date createdTime = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_time")
    private Date updatedTime;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "real_name", nullable = false)
    private String realName;

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

    @Convert(converter = YesNoStatusConverter.class)
    @Column(nullable = false, name = "on_job")
    private YesNoStatus onJob;

    @Convert(converter = EnableDisableStatusConverter.class)
    @Column(nullable = false)
    private EnableDisableStatus valid = EnableDisableStatus.ENABLE;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
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

    public YesNoStatus getOnJob() {
        return onJob;
    }

    public void setOnJob(YesNoStatus onJob) {
        this.onJob = onJob;
    }

    public EnableDisableStatus getValid() {
        return valid;
    }

    public void setValid(EnableDisableStatus valid) {
        this.valid = valid;
    }
}
