package com.bjsts.manager.service.purchase.impl;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.purchase.OutBoundEntity;
import com.bjsts.manager.entity.purchase.StockEntity;
import com.bjsts.manager.query.purchase.OutBoundSearchable;
import com.bjsts.manager.repository.purchase.OutBoundRepository;
import com.bjsts.manager.repository.purchase.StockRepository;
import com.bjsts.manager.service.purchase.OutBoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author wangzhiliang
 */
@Service
public class OutBoundServiceImpl extends AbstractService<OutBoundEntity, Long> implements OutBoundService {

    @Autowired
    private OutBoundRepository outBoundRepository;

    @Autowired
    private StockRepository stockRepository;

    @Override
    public ApiResponse<OutBoundEntity> findAll(OutBoundSearchable outBoundSearchable, Pageable pageable) {
        ApiRequest request = ApiRequest.newInstance();

        //request.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        outBoundSearchable.convertPageable(requestPage, pageable);

        Page<OutBoundEntity> outBoundEntityPage = outBoundRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(outBoundEntityPage);
    }

    @Override
    @Transactional
    public OutBoundEntity outBound(OutBoundEntity outBoundEntity) throws Exception{
        //保存库存
        StockEntity stockEntity = stockRepository.getById(outBoundEntity.getStockId());
        if (stockEntity == null) {
            throw new Exception("产品或型号不存在");
        }

        Long quantity = stockEntity.getQuantity() - outBoundEntity.getQuantity();
        if (quantity < 0) {
            throw new Exception("产品或型号数量不够");
        }

        stockEntity.setQuantity(quantity);
        stockRepository.save(stockEntity);

        //保存出库单
        OutBoundEntity db = outBoundRepository.save(outBoundEntity);
        return db;
    }

    @Override
    @Transactional
    public OutBoundEntity cancelBound(OutBoundEntity outBoundEntity) throws Exception{
        //恢复库存
        StockEntity stockEntity = stockRepository.getById(outBoundEntity.getStockId());
        if (stockEntity == null) {
            throw new Exception("产品或型号不存在");
        }

        Long quantity = stockEntity.getQuantity() + outBoundEntity.getQuantity();

        stockEntity.setQuantity(quantity);
        stockRepository.save(stockEntity);

        //删除出库单
        OutBoundEntity db = outBoundRepository.getById(outBoundEntity.getId());
        db.setValid(EnableDisableStatus.DISABLE);
        outBoundRepository.save(db);
        return db;
    }
}
