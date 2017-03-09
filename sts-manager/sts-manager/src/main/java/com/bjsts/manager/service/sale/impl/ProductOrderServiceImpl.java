package com.bjsts.manager.service.sale.impl;

import com.bjsts.core.api.request.ApiRequest;
import com.bjsts.core.api.request.ApiRequestFilter;
import com.bjsts.core.api.request.ApiRequestPage;
import com.bjsts.core.api.response.ApiResponse;
import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.OperatorType;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.sale.PlanEntity;
import com.bjsts.manager.entity.sale.PlanTraceEntity;
import com.bjsts.manager.entity.system.UserEntity;
import com.bjsts.manager.enums.document.DocumentType;
import com.bjsts.manager.enums.sale.PlanStatus;
import com.bjsts.manager.query.sale.ProductOrderSearchable;
import com.bjsts.manager.query.sale.QuoteSearchable;
import com.bjsts.manager.query.sale.SaleHistorySearchable;
import com.bjsts.manager.query.sale.SaleItemSearchable;
import com.bjsts.manager.repository.document.DocumentRepository;
import com.bjsts.manager.repository.sale.ProductOrderRepository;
import com.bjsts.manager.repository.sale.SaleItemRepository;
import com.bjsts.manager.service.sale.ProductOrderService;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * @author jinsheng
 * @since 2016-04-28 15:59
 */
@Service
@Transactional
public class ProductOrderServiceImpl extends AbstractService<PlanEntity, Long> implements ProductOrderService {

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private SaleItemRepository saleItemRepository;

    public PlanEntity save(PlanEntity planEntity, DocumentEntity documentEntity) {
        PlanEntity db = productOrderRepository.save(planEntity);
        DocumentEntity existDocumentEntity = documentRepository.findByGroupKeyAndObjectId(DocumentType.CUSTOMER.getEnglishName(), db.getPlanNo());

        if (existDocumentEntity == null) {
            existDocumentEntity = new DocumentEntity();
            existDocumentEntity.setName(documentEntity.getName());
            existDocumentEntity.setGroupKey(DocumentType.CUSTOMER.getEnglishName());
            existDocumentEntity.setUrl(documentEntity.getUrl());
            existDocumentEntity.setObjectId(planEntity.getPlanNo());
        } else {
            existDocumentEntity.setName(documentEntity.getName());
            existDocumentEntity.setUrl(documentEntity.getUrl());
        }
        DocumentEntity dbDocumentEntity = documentRepository.save(existDocumentEntity);

        db.setDocumentId(dbDocumentEntity.getId());
        return db;
    }

    public PlanEntity saveQuote(PlanEntity planEntity, DocumentEntity documentEntity) {
        //保存报价单
        PlanEntity db = productOrderRepository.save(planEntity);
        DocumentEntity existDocumentEntity = documentRepository.findByGroupKeyAndObjectId(DocumentType.QUOTE.getEnglishName(), db.getPlanNo());

        if (existDocumentEntity == null) {
            existDocumentEntity = new DocumentEntity();
            existDocumentEntity.setGroupKey(DocumentType.QUOTE.getEnglishName());
            existDocumentEntity.setName(documentEntity.getName());
            existDocumentEntity.setUrl(documentEntity.getUrl());
            existDocumentEntity.setObjectId(planEntity.getPlanNo());
        } else {
            existDocumentEntity.setName(documentEntity.getName());
            existDocumentEntity.setUrl(documentEntity.getUrl());
        }
        DocumentEntity dbDocumentEntity = documentRepository.save(existDocumentEntity);

        db.setQuoteFileId(dbDocumentEntity.getId());

        //保存项目追踪信息
        PlanTraceEntity planTraceEntity = new PlanTraceEntity();
        planTraceEntity.setPlanNo(db.getPlanNo());

        UserEntity userInfo = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        planTraceEntity.setUserId(userInfo.getId());
        planTraceEntity.setRealName(userInfo.getRealName());
        planTraceEntity.setTraceTime(new Date());
        planTraceEntity.setDescription(userInfo.getRealName() + "提交报价单");
        saleItemRepository.save(planTraceEntity);
        return db;
    }

    @Override
    public ApiResponse<PlanEntity> findAll(ProductOrderSearchable productOrderSearchable, Pageable pageable) {
        ApiRequest request = ApiRequest.newInstance();

        request.filterEqual("valid", EnableDisableStatus.ENABLE);

        request.filterEqual("planStatus", PlanStatus.ASK_PRICE);

        String booker = productOrderSearchable.getBooker();
        if (!StringUtils.isEmpty(booker)){
            request.filterLike("booker", booker);
        }

        String linkman = productOrderSearchable.getLinkman();
        if (!StringUtils.isEmpty(linkman)){
            request.filterLike("linkman", linkman);
        }

        if (productOrderSearchable.getBeginPriceTime() != null) {
            request.filterGreaterEqual("priceTime", productOrderSearchable.getBeginPriceTime());
        }

        if (productOrderSearchable.getEndPriceTime() != null) {
            request.filterLessEqual("priceTime", productOrderSearchable.getEndPriceTime());
        }

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        productOrderSearchable.convertPageable(requestPage, pageable);

        Page<PlanEntity> planEntityPage = productOrderRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(planEntityPage);
    }

    @Override
    public ApiResponse<PlanEntity> findAll(QuoteSearchable quoteSearchable, Pageable pageable) {
        ApiRequest request = ApiRequest.newInstance();

        request.filterEqual("valid", EnableDisableStatus.ENABLE);

        request.filterIn("planStatus", Lists.newArrayList(PlanStatus.ASK_PRICE, PlanStatus.TWO_ASK_PRICE));

        String booker = quoteSearchable.getBooker();
        if (!StringUtils.isEmpty(booker)){
            request.filterLike("booker", booker);
        }

        String linkman = quoteSearchable.getLinkman();
        if (!StringUtils.isEmpty(linkman)){
            request.filterLike("linkman", linkman);
        }

        if (quoteSearchable.getBeginPriceTime() != null) {
            request.filterGreaterEqual("priceTime", quoteSearchable.getBeginPriceTime());
        }

        if (quoteSearchable.getEndPriceTime() != null) {
            request.filterLessEqual("priceTime", quoteSearchable.getEndPriceTime());
        }

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        quoteSearchable.convertPageable(requestPage, pageable);

        Page<PlanEntity> planEntityPage = productOrderRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(planEntityPage);
    }

    @Override
    public ApiResponse<PlanEntity> findAll(SaleItemSearchable saleItemSearchable, Pageable pageable) {
        ApiRequest request = ApiRequest.newInstance();

        request.filterEqual("valid", EnableDisableStatus.ENABLE);

        request.filterIn("planStatus", PlanStatus.listTraceStatus());

        UserEntity userInfo = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        Subject currentUser = SecurityUtils.getSubject();
        if (!currentUser.isPermitted("sts:saleItem:listAll")) {
            request.filterOr(
                    new ApiRequestFilter(OperatorType.EQ,"bookerId", userInfo.getId()),
                    new ApiRequestFilter(OperatorType.EQ,"quoterId", userInfo.getId())
            );
        }

        String booker = saleItemSearchable.getBooker();
        if (!StringUtils.isEmpty(booker)){
            request.filterLike("booker", booker);
        }

        String linkman = saleItemSearchable.getLinkman();
        if (!StringUtils.isEmpty(linkman)){
            request.filterLike("linkman", linkman);
        }

        if (saleItemSearchable.getBeginPriceTime() != null) {
            request.filterGreaterEqual("priceTime", saleItemSearchable.getBeginPriceTime());
        }

        if (saleItemSearchable.getEndPriceTime() != null) {
            request.filterLessEqual("priceTime", saleItemSearchable.getEndPriceTime());
        }

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        saleItemSearchable.convertPageable(requestPage, pageable);

        Page<PlanEntity> planEntityPage = productOrderRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(planEntityPage);
    }

    @Override
    public ApiResponse<PlanEntity> findAll(SaleHistorySearchable saleHistorySearchable, Pageable pageable) {
        ApiRequest request = ApiRequest.newInstance();

        request.filterEqual("valid", EnableDisableStatus.ENABLE);

        request.filterIn("planStatus", PlanStatus.listHistoryStatus());

        ApiRequestPage requestPage = ApiRequestPage.newInstance();
        saleHistorySearchable.convertPageable(requestPage, pageable);

        Page<PlanEntity> planEntityPage = productOrderRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(planEntityPage);
    }

    @Override
    public PlanEntity findByPlanNo(String planNo) {
        return productOrderRepository.findByPlanNo(planNo);
    }

    @Override
    public ApiResponse<PlanEntity> findAll(ApiRequest request, ApiRequestPage requestPage) {
        Page<PlanEntity> page = productOrderRepository.findAll(convertSpecification(request), convertPageable(requestPage));
        return convertApiResponse(page);
    }
}
