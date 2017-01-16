package com.bjsts.manager.query.sale;

import com.bjsts.manager.core.query.CommonSearchable;
import com.bjsts.manager.enums.sale.PlanStatus;

import java.util.List;

/**
 * @author wangzhiliang
 */
public class QuoteSearchable extends CommonSearchable {

    private static final long serialVersionUID = 7246566994502707777L;

    private PlanStatus planStatus;

    private List<PlanStatus> planStatusList;

    public PlanStatus getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(PlanStatus planStatus) {
        this.planStatus = planStatus;
    }

    public List<PlanStatus> getPlanStatusList() {
        return planStatusList;
    }

    public void setPlanStatusList(List<PlanStatus> planStatusList) {
        this.planStatusList = planStatusList;
    }
}
