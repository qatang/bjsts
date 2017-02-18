package com.bjsts.manager.repository.purchase;

import com.bjsts.manager.core.repository.IRepository;
import com.bjsts.manager.entity.purchase.PurchaseEntity;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;

/**
 * @author jinsheng
 * @since 2016-04-27 14:53
 */
public interface PurchaseRepository extends IRepository<PurchaseEntity, Long> {
    PurchaseEntity findByPurchaseNo(String purchaseNo);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    PurchaseEntity getByPurchaseNo(String purchaseNo);
}
