package com.bjsts.core.api.repository.impl;

import com.bjsts.core.api.repository.BaseInternalRepository;
import com.google.common.collect.Lists;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 实现一些通用的自定义DAO操作方法
 * Created by sunshow on 7/1/15.
 */
public class BaseInternalRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements BaseInternalRepository<T, ID> {

    private final EntityManager entityManager;

    public BaseInternalRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);

        this.entityManager = entityManager;
    }


    public BaseInternalRepositoryImpl(Class<T> domainClass, EntityManager entityManager) {
        super(domainClass, entityManager);

        this.entityManager = entityManager;
    }

    @Override
    public T findOneForUpdate(ID id) {
        return entityManager.find(this.getDomainClass(), id, LockModeType.PESSIMISTIC_WRITE);
    }

    @Override
    public void clear() {
        entityManager.clear();
    }

    @Override
    public BigDecimal sum(Specification<T> spec, String field) {
        return this.multiSum(spec, field).get(0);
    }

    @Override
    public List<BigDecimal> multiSum(Specification<T> spec, String... fields) {
        return multiSum(spec, Lists.newArrayList(fields));
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<BigDecimal> multiSum(Specification<T> spec, List<String> fieldList) {
        Assert.notEmpty(fieldList);

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery query = builder.createQuery();

        Root<T> root = query.from(getDomainClass());

        if (spec != null) {
            Predicate predicate = spec.toPredicate(root, query, builder);

            if (predicate != null) {
                query.where(predicate);
            }
        }

        List<Selection<?>> sumSelectList = Lists.newArrayList();
        for (String field : fieldList) {
            sumSelectList.add(builder.sum(root.get(field)));
        }
        query.multiselect(sumSelectList);

        List<BigDecimal> resultList = Lists.newArrayList();

        if (sumSelectList.size() == 1) {
            Object queryResult = entityManager.createQuery(query).getSingleResult();
            resultList.add(queryResult == null ? null : new BigDecimal(queryResult.toString()));
        } else {
            Object[] queryResult = (Object[]) entityManager.createQuery(query).getSingleResult();
            for (Object object : queryResult) {
                resultList.add(object == null ? null : new BigDecimal(object.toString()));
            }
        }

        return resultList;
    }
}
