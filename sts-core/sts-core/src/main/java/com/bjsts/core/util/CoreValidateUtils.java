package com.bjsts.core.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sunshow
 * @since 2015-07-20 11:05
 */
public class CoreValidateUtils {
    protected static final transient Logger logger = LoggerFactory.getLogger(CoreValidateUtils.class);

    public static int calculateLuhn(String number) {
        int sum = 0;

        for (int i = 0; i < number.length(); i++) {
            int n = Integer.parseInt(StringUtils.substring(number, number.length() - 1 - i, number.length() - i));
            if (i % 2 == 0) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
        }

        return 10 - (sum % 10);
    }
}
