package com.bjsts.manager.repository.user;

import com.bjsts.manager.core.repository.IRepository;
import com.bjsts.manager.entity.user.AttendanceEntity;

import java.util.List;

/**
 * @author wangzhiliang
 */
public interface AttendanceRepository extends IRepository<AttendanceEntity, Long> {
    List<AttendanceEntity> findByUserId(Long userId);
}
