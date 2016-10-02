package com.bjsts.core.component.loadbalance;

import com.bjsts.core.util.CoreMathUtils;

/**
 * author: sunshow.
 */
public class WeightedRoundRobinSelector implements LoadBalanceSelector {

    /**
     * 上次选择的索引
     */
    private int lastIndex = -1;

    /**
     * 所有权重和对应的选择目标数组
     */
    private int[] weights;

    /**
     * 最大的权重值
     */
    private int maxWeight = 1;

    /**
     * 当前调度的权值
     */
    private int currentWeight = 0;

    /**
     * 可选择目标的数量
     */
    private int selectCount = 0;

    /**
     * 可选择目标的权重值的最大公约数
     */
    private int selectWeightGcd = 1;

    public WeightedRoundRobinSelector(int[] weights) {
        this.weights = new int[weights.length];

        // 把所有权重小于1的权重都认为是1, 并计算最大的权重值
        for (int i = 0; i < weights.length; i++) {
            int weight = weights[i];
            if (weight < 1) {
                weight = 1;
            }

            if (weight > maxWeight) {
                maxWeight = weight;
            }

            this.weights[i] = weight;
        }

        this.selectCount = this.weights.length;
        this.selectWeightGcd = CoreMathUtils.gcd(this.weights);
    }

    public WeightedRoundRobinSelector(int[] weights, int lastIndex, int currentWeight) {
        this(weights);
        if (lastIndex >= 0) {
            this.lastIndex = lastIndex;
            this.currentWeight = currentWeight;
        }
    }

    @Override
    public int selectNext() {
        int n = this.selectCount;
        int gcd = this.selectWeightGcd;

        while (true) {
            lastIndex = (lastIndex + 1) % n;

            if (lastIndex == 0) {
                currentWeight = currentWeight - gcd;
                if (currentWeight <= 0) {
                    currentWeight = maxWeight;
                    if (currentWeight == 0) {
                        return -1;
                    }
                }
            }
            if (weights[lastIndex] >= currentWeight) {
                return lastIndex;
            }
        }
    }

    /**
     * @return 上次选择的索引
     */
    public int getLastIndex() {
        return this.lastIndex;
    }

    /**
     * @return 当前调度的权值
     */
    public int getCurrentWeight() {
        return this.currentWeight;
    }
}
