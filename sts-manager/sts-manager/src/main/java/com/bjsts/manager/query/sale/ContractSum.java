package com.bjsts.manager.query.sale;

import com.bjsts.core.api.annotation.request.RequestApiBean;
import com.bjsts.core.api.bean.AbstractBaseApiBean;

/**
 * 票订单汇总结果
 * @author wangzhiliang
 */
@RequestApiBean
public class ContractSum extends AbstractBaseApiBean {

    private static final long serialVersionUID = 5239766973669754631L;
    /**
     * 金额，单位：分
     */
    private Long amount = 0L;

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }
}
