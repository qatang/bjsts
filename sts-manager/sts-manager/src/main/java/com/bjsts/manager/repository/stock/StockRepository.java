package com.bjsts.manager.repository.stock;

import com.bjsts.manager.core.repository.IRepository;
import com.bjsts.manager.entity.stock.StockEntity;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;

/**
 * @author jinsheng
 * @since 2016-04-27 14:53
 */
public interface StockRepository extends IRepository<StockEntity, Long> {
/*    @Lock(LockModeType.PESSIMISTIC_WRITE)
    StockEntity getByProductNameAndProductModel(String productName, String productModel);*/

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    StockEntity getById(Long stockId);
}
