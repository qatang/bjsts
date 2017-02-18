package com.bjsts.manager.service.purchase.impl;

import com.bjsts.core.enums.YesNoStatus;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.purchase.InBoundEntity;
import com.bjsts.manager.entity.purchase.PurchaseEntity;
import com.bjsts.manager.entity.purchase.StockEntity;
import com.bjsts.manager.repository.purchase.InBoundRepository;
import com.bjsts.manager.repository.purchase.PurchaseRepository;
import com.bjsts.manager.repository.purchase.StockRepository;
import com.bjsts.manager.service.purchase.InBoundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author jinsheng
 * @since 2016-04-28 15:59
 */
@Service
public class InBoundServiceImpl extends AbstractService<InBoundEntity, Long> implements InBoundService {

    @Autowired
    private InBoundRepository inBoundRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    public InBoundEntity findByPurchaseNo(String purchaseNo) {
        return inBoundRepository.findByPurchaseNo(purchaseNo);
    }

    @Override
    @Transactional
    public InBoundEntity inBound(InBoundEntity inBoundEntity) {
        //修改采购单状态
        PurchaseEntity purchaseEntity = purchaseRepository.getByPurchaseNo(inBoundEntity.getPurchaseNo());
        if (purchaseEntity.getInBound() == YesNoStatus.NO) {
            purchaseEntity.setInBound(YesNoStatus.YES);
            purchaseRepository.save(purchaseEntity);

            //保存库存
            StockEntity stockEntity = stockRepository.getByProductNameAndProductModel(purchaseEntity.getProductName(), purchaseEntity.getProductModel());
            if (stockEntity == null) {
                stockEntity = new StockEntity();
                stockEntity.setProductName(purchaseEntity.getProductName());
                stockEntity.setProductModel(purchaseEntity.getProductModel());
                stockEntity.setQuantity(purchaseEntity.getQuantity());
                stockRepository.save(stockEntity);
            } else {
                stockEntity.setQuantity(stockEntity.getQuantity() + purchaseEntity.getQuantity());
                stockRepository.save(stockEntity);
            }
        }

        //保存入库单
        InBoundEntity db = inBoundRepository.save(inBoundEntity);
        return db;
    }
}
