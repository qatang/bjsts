package com.bjsts.core.component.loadbalance;

import org.junit.Test;

/**
 * author: sunshow.
 */
public class LoadBalanceSelectorTest {

    @Test
    public void testWeightedRoundRobinSelectNext() {
        WeightedRoundRobinSelector selector = new WeightedRoundRobinSelector(new int[] {1, 2, 3, 4});

        for (int i = 0; i < 100; i++) {
            System.out.println(selector.selectNext());
        }
    }

    @Test
    public void testRoundRobinSelectNext() {
        RoundRobinSelector selector = new RoundRobinSelector(4);

        for (int i = 0; i < 100; i++) {
            System.out.println(selector.selectNext());
        }
    }
}
