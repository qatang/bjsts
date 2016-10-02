package com.bjsts.manager.service.log.impl;

import com.bjsts.manager.service.log.LogService;
import com.bjsts.manager.entity.log.LogEntity;
import com.bjsts.manager.core.service.AbstractService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author jinsheng
 * @since 2016-05-13 14:32
 */
@Service
@Transactional
public class LogServiceImpl extends AbstractService<LogEntity, Long> implements LogService {

}
