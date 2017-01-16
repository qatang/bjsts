package com.bjsts.manager.repository.document;

import com.bjsts.manager.core.repository.IRepository;
import com.bjsts.manager.entity.document.DocumentEntity;

/**
 * 文件管理
 *
 */
public interface DocumentRepository extends IRepository<DocumentEntity, Long> {

    DocumentEntity findByGroupKeyAndObjectId(String groupKey, String objectId);
}
