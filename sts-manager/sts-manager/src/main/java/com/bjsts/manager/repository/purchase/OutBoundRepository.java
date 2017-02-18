package com.bjsts.manager.repository.purchase;

import com.bjsts.manager.core.repository.IRepository;
import com.bjsts.manager.entity.purchase.OutBoundEntity;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;

/**
 * @author wangzhiliang
 */
public interface OutBoundRepository extends IRepository<OutBoundEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    OutBoundEntity getById(Long id);
}
