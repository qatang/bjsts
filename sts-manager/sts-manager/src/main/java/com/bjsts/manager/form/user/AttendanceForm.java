package com.bjsts.manager.form.user;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.user.AttendanceEntity;

/**
 * @author wangzhiliang
 */
public class AttendanceForm extends AbstractForm {
    private static final long serialVersionUID = 7248902758556128866L;

    private AttendanceEntity attendance;

    public AttendanceEntity getAttendance() {
        return attendance;
    }

    public void setAttendance(AttendanceEntity attendance) {
        this.attendance = attendance;
    }
}
