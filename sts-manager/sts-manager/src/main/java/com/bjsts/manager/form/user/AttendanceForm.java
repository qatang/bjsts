package com.bjsts.manager.form.user;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.user.AttendanceEntity;

import java.util.List;

/**
 * @author wangzhiliang
 */
public class AttendanceForm extends AbstractForm {
    private static final long serialVersionUID = 7248902758556128866L;

    private AttendanceEntity attendance;

    private List<Long> staffIds;

    private Integer year;

    private Integer month;

    public AttendanceEntity getAttendance() {
        return attendance;
    }

    public void setAttendance(AttendanceEntity attendance) {
        this.attendance = attendance;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<Long> getStaffIds() {
        return staffIds;
    }

    public void setStaffIds(List<Long> staffIds) {
        this.staffIds = staffIds;
    }
}
