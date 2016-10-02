package com.bjsts.core.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * author: sunshow.
 */
public class CoreValidateUtilsTest {

    @Test
    public void testCalculateLuhn() throws Exception {
        int n = CoreValidateUtils.calculateLuhn("7992739871");
        Assert.assertEquals(n, 3);
    }
}
