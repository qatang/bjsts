package com.bjsts.manager.query.sale;

import com.bjsts.manager.core.query.CommonSearchable;
import com.bjsts.manager.enums.sale.PlanStatus;

import java.util.Date;
import java.util.List;

/**
 * @author wangzhiliang
 */
public class QuoteSearchable extends CommonSearchable {

    private static final long serialVersionUID = 7246566994502707777L;

    private PlanStatus planStatus;

    private List<PlanStatus> planStatusList;

    private Date beginPriceTime;

    private Date endPriceTime;

    private String booker;

    private String linkman;

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

    public Date getBeginPriceTime() {
        return beginPriceTime;
    }

    public void setBeginPriceTime(Date beginPriceTime) {
        this.beginPriceTime = beginPriceTime;
    }

    public Date getEndPriceTime() {
        return endPriceTime;
    }

    public void setEndPriceTime(Date endPriceTime) {
        this.endPriceTime = endPriceTime;
    }

    public String getBooker() {
        return booker;
    }

    public void setBooker(String booker) {
        this.booker = booker;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }
}
