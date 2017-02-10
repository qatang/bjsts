
package com.bjsts.manager.form.user;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.user.StaffEntity;

/**
 * @author wangzhiliang
 */
public class StaffForm extends AbstractForm {

    private static final long serialVersionUID = -5837010514610158284L;

    private StaffEntity staff;

    public StaffEntity getStaff() {
        return staff;
    }

    public void setStaff(StaffEntity staff) {
        this.staff = staff;
    }
}
