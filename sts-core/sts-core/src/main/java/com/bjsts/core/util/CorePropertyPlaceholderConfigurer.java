package com.bjsts.core.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * author: sunshow.
 */
public class CorePropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {

    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    protected final static String DYNAMIC_PATTERN_PREFIX = "!!";

    protected final static String DYNAMIC_PATTERN_PREFIX_ENV = "env.";

    @Override
    protected String convertPropertyValue(String originalValue) {
        if (StringUtils.isNotBlank(originalValue) && StringUtils.startsWith(originalValue, DYNAMIC_PATTERN_PREFIX)) {
            logger.warn("try to resolve dynamic pattern: {}", originalValue);
            String pattern = StringUtils.substringAfter(originalValue, DYNAMIC_PATTERN_PREFIX);
            if (StringUtils.isNotBlank(pattern) && StringUtils.startsWith(pattern, DYNAMIC_PATTERN_PREFIX_ENV)) {
                logger.warn("try to resolve dynamic environment pattern: {}", pattern);
                String envName = StringUtils.substringAfter(pattern, DYNAMIC_PATTERN_PREFIX_ENV);
                logger.warn("the environment property name is: {}", envName);
                if (StringUtils.isNotBlank(envName)) {
                    String envValue = this.resolveSystemProperty(envName);
                    logger.warn("the environment property value is: {}", envValue);
                    return envValue;
                }
            }
        }
        return super.convertPropertyValue(originalValue);
    }
}
