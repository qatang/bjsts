package com.bjsts.manager.service.purchase.impl;

import com.bjsts.core.util.CoreMathUtils;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.invoice.InvoiceEntity;
import com.bjsts.manager.entity.purchase.PurchasePayEntity;
import com.bjsts.manager.entity.purchase.PurchasePayItemEntity;
import com.bjsts.manager.enums.invoice.InvoiceCategory;
import com.bjsts.manager.repository.invoice.InvoiceRepository;
import com.bjsts.manager.repository.purchase.PurchasePayItemRepository;
import com.bjsts.manager.repository.purchase.PurchasePayRepository;
import com.bjsts.manager.service.purchase.PurchasePayItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author wangzhiliang
 */
@Service
@Transactional
public class PurchasePayItemServiceImpl extends AbstractService<PurchasePayItemEntity, Long> implements PurchasePayItemService {

    @Autowired
    private PurchasePayItemRepository purchasePayItemRepository;

    @Autowired
    private PurchasePayRepository purchasePayRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;


    @Override
    public List<PurchasePayItemEntity> findByPurchasePayId(Long purchasePayId) {
        return purchasePayItemRepository.findByPurchasePayId(purchasePayId);
    }

    @Override
    public PurchasePayItemEntity save(PurchasePayItemEntity purchasePayItemEntity) {
        PurchasePayItemEntity db = purchasePayItemRepository.save(purchasePayItemEntity);

        PurchasePayEntity purchasePayEntity = purchasePayRepository.findOne(db.getPurchasePayId());
        purchasePayEntity.setPayedAmount(CoreMathUtils.add(purchasePayEntity.getPayedAmount(), db.getAmount()));
        purchasePayRepository.save(purchasePayEntity);

        Long invoiceAmount = db.getInvoiceAmount();
        //开票金额大于0 创建采购发票
        if (invoiceAmount > 0) {
            InvoiceEntity invoiceEntity = new InvoiceEntity();
            invoiceEntity.setPlanNo("0");
            invoiceEntity.setInvoiceCategory(InvoiceCategory.BT);
            invoiceEntity.setAmount(invoiceAmount);
            invoiceEntity.setDeductionDate(db.getPayTime());
            invoiceEntity.setInvoiceNo("0");
            invoiceRepository.save(invoiceEntity);
        }
        return db;
    }

    @Override
    public void delete(Long id) {
        PurchasePayItemEntity db = purchasePayItemRepository.findOne(id);

        PurchasePayEntity purchasePayEntity = purchasePayRepository.findOne(db.getPurchasePayId());
        purchasePayEntity.setPayedAmount(CoreMathUtils.sub(purchasePayEntity.getPayedAmount(), db.getAmount()));
        purchasePayRepository.save(purchasePayEntity);

        purchasePayItemRepository.delete(id);
    }
}
