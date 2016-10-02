package com.bjsts.core.api.repository;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 用于实现自定义实现的预置方法
 * Created by sunshow on 4/21/15.
 */
@NoRepositoryBean
public interface BaseInternalRepository<T, ID extends Serializable> extends Repository<T, ID> {
    /**
     * 锁行读写的方式通过ID获取对象
     * @param id 对象ID
     * @return 对应的对象
     */
    T findOneForUpdate(ID id);

    /**
     * 清除所有被entityManager管理的entity
     * 警告: 不只是清除当前类型的entity, 所有已经载入的entity都会被清除, 除非你知道在干什么, 否则不要调用此方法
     */
    void clear();

    /**
     * 聚合操作求和
     * @param spec 查询条件
     * @param field 要聚合的字段
     * @return 聚合结果列表
     */
    BigDecimal sum(Specification<T> spec, String field);

    /**
     * 聚合操作求和
     * @param spec 查询条件
     * @param fields 要聚合的字段列表
     * @return 聚合结果列表
     */
    List<BigDecimal> multiSum(Specification<T> spec, String... fields);

    /**
     * 聚合操作求和
     * @param spec 查询条件
     * @param fieldList 要聚合的字段集合
     * @return 聚合结果列表
     */
    List<BigDecimal> multiSum(Specification<T> spec, List<String> fieldList);
}
