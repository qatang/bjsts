package com.bjsts.manager.service.purchase;

import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.purchase.OutBoundEntity;
import com.bjsts.manager.query.purchase.OutBoundSearchable;
import org.springframework.data.domain.Pageable;

/**
 * @author jinsheng
 * @since 2016-04-28 15:44
 */
public interface OutBoundService extends IService<OutBoundEntity, Long> {
    ApiResponse<OutBoundEntity> findAll(OutBoundSearchable outBoundSearchable, Pageable pageable);

    /**
     * 出库操作
     * 1、查询商品对应的库存
     * 2、修改库存数量
     * 3、保存出库单
     * @param outBoundEntity
     * @return
     */
    OutBoundEntity outBound(OutBoundEntity outBoundEntity) throws Exception;

    /**
     * 出库操作
     * 1、查询商品对应的库存
     * 2、修改库存数量
     * 3、保存出库单
     * @param outBoundEntity
     * @return
     */
    OutBoundEntity cancelBound(OutBoundEntity outBoundEntity) throws Exception;
}
