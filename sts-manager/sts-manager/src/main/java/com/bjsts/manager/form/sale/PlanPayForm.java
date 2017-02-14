package com.bjsts.manager.form.sale;

import com.bjsts.manager.core.form.AbstractForm;
import com.bjsts.manager.entity.sale.PlanPayEntity;

/**
 * @author wangzhiliang
 */
public class PlanPayForm extends AbstractForm {

    private static final long serialVersionUID = 3159857202387057231L;

    private PlanPayEntity planPay;

    private Double amount;

    public PlanPayEntity getPlanPay() {
        return planPay;
    }

    public void setPlanPay(PlanPayEntity planPay) {
        this.planPay = planPay;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
