package com.bjsts.manager.service.purchase.impl;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.purchase.NormalPurchaseEntity;
import com.bjsts.manager.entity.purchase.PurchasePayEntity;
import com.bjsts.manager.query.purchase.NormalPurchaseSearchable;
import com.bjsts.manager.repository.purchase.NormalPurchaseRepository;
import com.bjsts.manager.repository.purchase.PurchasePayRepository;
import com.bjsts.manager.service.purchase.NormalPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author wangzhiliang
 */
@Service
@Transactional
public class NormalPurchaseServiceImpl extends AbstractService<NormalPurchaseEntity, Long> implements NormalPurchaseService {

    @Autowired
    private NormalPurchaseRepository normalPurchaseRepository;

    @Autowired
    private PurchasePayRepository purchasePayRepository;

    @Override
    public ApiResponse<NormalPurchaseEntity> findAll(NormalPurchaseSearchable normalPurchaseSearchable, Pageable pageable) {
        ApiRequest request = ApiRequest.newInstance();

        request.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        normalPurchaseSearchable.convertPageable(requestPage, pageable);

        Page<NormalPurchaseEntity> normalPurchaseEntityPage = normalPurchaseRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(normalPurchaseEntityPage);
    }

    @Override
    public NormalPurchaseEntity save(NormalPurchaseEntity normalPurchaseEntity) {
        NormalPurchaseEntity db = normalPurchaseRepository.save(normalPurchaseEntity);

        PurchasePayEntity purchasePayEntity = purchasePayRepository.findByPurchaseNo(db.getPurchaseNo());
        if (purchasePayEntity == null) {
            purchasePayEntity = new PurchasePayEntity();
            purchasePayEntity.setPurchaseNo(db.getPurchaseNo());
            purchasePayEntity.setAmount(db.getAmount());
            purchasePayEntity.setPayedAmount(0L);
        } else {
            purchasePayEntity.setAmount(db.getAmount());
        }
        purchasePayRepository.save(purchasePayEntity);
        return db;
    }
}
