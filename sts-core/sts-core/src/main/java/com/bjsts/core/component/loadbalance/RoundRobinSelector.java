package com.bjsts.core.component.loadbalance;

/**
 * author: sunshow.
 */
public class RoundRobinSelector implements LoadBalanceSelector {

    private WeightedRoundRobinSelector selector;

    public RoundRobinSelector(int n) {
        int[] weights = new int[n];
        for (int i = 0; i < n; i++) {
            weights[i] = 1;
        }
        this.selector = new WeightedRoundRobinSelector(weights);
    }

    @Override
    public int selectNext() {
        return selector.selectNext();
    }

}
