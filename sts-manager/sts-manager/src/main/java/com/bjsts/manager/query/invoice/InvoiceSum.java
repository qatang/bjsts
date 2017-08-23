package com.bjsts.manager.query.invoice;

import com.bjsts.core.api.annotation.request.RequestApiBean;
import com.bjsts.core.api.bean.AbstractBaseApiBean;

/**
 * 采购发票汇总结果
 * @author wangzhiliang
 */
@RequestApiBean
public class InvoiceSum extends AbstractBaseApiBean {

    private static final long serialVersionUID = -49313902641034139L;
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
