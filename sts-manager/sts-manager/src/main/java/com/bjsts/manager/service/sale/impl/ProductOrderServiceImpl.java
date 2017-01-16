package com.bjsts.manager.service.sale.impl;

import com.bjsts.manager.core.constants.GlobalConstants;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.entity.sale.PlanEntity;
import com.bjsts.manager.repository.document.DocumentRepository;
import com.bjsts.manager.repository.sale.ProductOrderRepository;
import com.bjsts.manager.service.sale.ProductOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    public PlanEntity save(PlanEntity planEntity, String fileUrl) {
        PlanEntity db = productOrderRepository.save(planEntity);
        DocumentEntity existDocumentEntity = documentRepository.findByGroupKeyAndObjectId(GlobalConstants.CUSTOMER_FILE, db.getPlanNo());

        if (existDocumentEntity == null) {
            existDocumentEntity = new DocumentEntity();
            existDocumentEntity.setName(planEntity.getPlanNo());
            existDocumentEntity.setGroupKey(GlobalConstants.CUSTOMER_FILE);
            existDocumentEntity.setUrl(fileUrl);
            existDocumentEntity.setObjectId(planEntity.getPlanNo());
        } else {
            existDocumentEntity.setUrl(fileUrl);
        }
        DocumentEntity dbDocumentEntity = documentRepository.save(existDocumentEntity);

        db.setDocumentId(dbDocumentEntity.getId());
        return db;
    }
}
