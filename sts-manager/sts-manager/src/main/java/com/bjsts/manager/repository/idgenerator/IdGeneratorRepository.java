package com.bjsts.manager.repository.idgenerator;

import com.bjsts.manager.core.repository.IRepository;
import com.bjsts.manager.entity.idgenerator.IdGeneratorEntity;

/**
 * 发号器
 * !!!用到发号器的地方必须在事务外部
 * @author Sunshow
 *
 */
public interface IdGeneratorRepository extends IRepository<IdGeneratorEntity, String> {
    /**
     * 锁行读写的方式通过ID获取对象
     * @param sequence 对象ID
     * @return 对应的对象
     */
    IdGeneratorEntity findOneForUpdate(String sequence);
}
