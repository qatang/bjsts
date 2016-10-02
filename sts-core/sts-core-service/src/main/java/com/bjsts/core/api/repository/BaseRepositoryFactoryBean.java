package com.bjsts.core.api.repository;

import com.bjsts.core.api.repository.impl.BaseInternalRepositoryImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * 自定义Spring JPA的FactoryBean
 * Created by sunshow on 7/1/15.
 */
public class BaseRepositoryFactoryBean<R extends JpaRepository<T, I>, T,
        I extends Serializable> extends JpaRepositoryFactoryBean<R, T, I> {

    protected RepositoryFactorySupport createRepositoryFactory(EntityManager em) {
        return new BaseRepositoryFactory(em);
    }

    private static class BaseRepositoryFactory<T, I extends Serializable>
            extends JpaRepositoryFactory {

        public BaseRepositoryFactory(EntityManager em) {
            super(em);
        }

        @Override
        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
            return BaseInternalRepositoryImpl.class;
        }
    }

}
