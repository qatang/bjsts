package com.bjsts.manager.config;

/**
 * @author jinsheng
 * @since 2016-04-27 14:49
 */

import com.bjsts.core.api.config.AbstractBaseJpaConfig;
import com.bjsts.core.api.repository.BaseRepositoryFactoryBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.bjsts.manager.repository", repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
public class JpaConfig extends AbstractBaseJpaConfig {

    @Override
    protected String entityPackagesToScan() {
        return "com.bjsts.manager.entity";
    }
}
