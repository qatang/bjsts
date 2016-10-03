package com.bjsts.manager.form.user;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.user.DepartmentEntity;

/**
 * @author jinsheng
 * @since 2016-05-12 16:00
 */
public class DepartmentForm extends AbstractForm {

    private static final long serialVersionUID = 5859168020901454685L;

    private DepartmentEntity department;

    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        this.department = department;
    }
}
