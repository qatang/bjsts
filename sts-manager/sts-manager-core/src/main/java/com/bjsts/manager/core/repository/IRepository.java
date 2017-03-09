package com.bjsts.manager.core.repository;

import com.bjsts.core.api.repository.BaseInternalRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.io.Serializable;

/**
 * @author jinsheng
 * @since 2016-04-27 14:52
 */
public interface IRepository<T, ID extends Serializable> extends BaseInternalRepository<T,ID>, JpaRepository<T, ID>, JpaSpecificationExecutor<T> {
}
