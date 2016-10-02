package com.bjsts.core.component.loadbalance;

/**
 * author: sunshow.
 */
public interface LoadBalanceSelector {
    /**
     * 选择下一个目标(注: 非线程安全)
     * @return 选择目标的索引
     */
    int selectNext();
}
