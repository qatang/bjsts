package com.bjsts.manager.form.produce;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.produce.PlanManageEntity;

/**
 * @author wangzhiliang
 */
public class PlanManageForm extends AbstractForm {

    private static final long serialVersionUID = 81416655041450840L;

    private PlanManageEntity planManage;

    public PlanManageEntity getPlanManage() {
        return planManage;
    }

    public void setPlanManage(PlanManageEntity planManage) {
        this.planManage = planManage;
    }
}
