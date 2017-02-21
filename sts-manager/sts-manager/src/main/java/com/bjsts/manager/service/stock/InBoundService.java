package com.bjsts.manager.service.stock;

import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.stock.InBoundEntity;

/**
 * @author jinsheng
 * @since 2016-04-28 15:44
 */
public interface InBoundService extends IService<InBoundEntity, Long> {
    InBoundEntity findByPurchaseNo(String purchaseNo);

    /**
     * 入库操作
     * 1、保存入库单
     * 2、修改采购单状态为已入库
     * 3、修改库存数量
     * @param inBoundEntity
     * @return
     */
    InBoundEntity inBound(InBoundEntity inBoundEntity);
}
