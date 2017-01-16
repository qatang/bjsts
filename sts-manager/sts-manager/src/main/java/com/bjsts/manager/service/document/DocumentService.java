package com.bjsts.manager.service.document;


import com.bjsts.manager.core.service.IService;
import com.bjsts.manager.entity.document.DocumentEntity;

/**
 * 文档管理
 *
 */
public interface DocumentService extends IService<DocumentEntity, Long> {
    DocumentEntity findByGroupKeyAndObjectId(String groupKey, String objectId);
}
