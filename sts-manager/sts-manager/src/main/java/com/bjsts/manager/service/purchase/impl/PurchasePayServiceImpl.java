package com.bjsts.manager.service.purchase.impl;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.purchase.PurchasePayEntity;
import com.bjsts.manager.query.purchase.PurchasePaySearchable;
import com.bjsts.manager.repository.purchase.PurchasePayRepository;
import com.bjsts.manager.service.purchase.PurchasePayService;
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
public class PurchasePayServiceImpl extends AbstractService<PurchasePayEntity, Long> implements PurchasePayService {

    @Autowired
    private PurchasePayRepository purchasePayRepository;

    @Override
    public ApiResponse<PurchasePayEntity> findAll(PurchasePaySearchable purchasePaySearchable, Pageable pageable) {
        ApiRequest request = ApiRequest.newInstance();

        request.filterEqual("valid", EnableDisableStatus.ENABLE);

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        purchasePaySearchable.convertPageable(requestPage, pageable);

        Page<PurchasePayEntity> purchasePayEntityPage = purchasePayRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(purchasePayEntityPage);
    }
}
