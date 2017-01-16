package com.bjsts.manager.service.document.impl;

import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.document.DocumentEntity;
import com.bjsts.manager.repository.document.DocumentRepository;
import com.bjsts.manager.service.document.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * 文件管理
 */
@Service
@Transactional
public class DocumentServiceImpl extends AbstractService<DocumentEntity, Long> implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Override
    public DocumentEntity findByGroupKeyAndObjectId(String groupKey, String objectId) {
        return documentRepository.findByGroupKeyAndObjectId(groupKey, objectId);
    }
}
