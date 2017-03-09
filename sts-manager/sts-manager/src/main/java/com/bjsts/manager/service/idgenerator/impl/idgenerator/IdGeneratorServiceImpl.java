package com.bjsts.manager.service.idgenerator.impl.idgenerator;

import com.bjsts.core.util.CoreDateUtils;
import com.bjsts.manager.core.service.AbstractService;
import com.bjsts.manager.entity.idgenerator.IdGeneratorEntity;
import com.bjsts.manager.repository.idgenerator.IdGeneratorRepository;
import com.bjsts.manager.service.idgenerator.IdGeneratorService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * 利用数据库行锁实现的发号器
 * !!!用到发号器的地方必须在事务外部
 * Created by sunshow on 4/21/15.
 */
@Service
@Transactional
public class IdGeneratorServiceImpl extends AbstractService<IdGeneratorEntity, String> implements IdGeneratorService {

    @Autowired
    private IdGeneratorRepository idGeneratorRepository;

    @Override
    public Long generate(String sequence) {
        return this.generate(sequence, 1);
    }

    @Override
    public Long generate(String sequence, int count) {
        return generateWithGroup(sequence, count, null);
    }

    @Override
    public Long generateWithGroup(String sequence, int count, String groupKey) {
        IdGeneratorEntity generator = this.idGeneratorRepository.findOneForUpdate(sequence);

        groupKey = StringUtils.trim(groupKey);

        Long returnValue;

        int added = count;
        if (added <= 0) {
            // 至少增加一个
            added = 1;
        }
        if (generator == null) {
            // 如果发号器首次申请发号时存在线程并发, 可能会导致死锁, 为了绝大多数情况下效率忽略死锁
            generator = new IdGeneratorEntity();
            generator.setSequence(sequence);
            if (StringUtils.isNotBlank(groupKey)) {
                generator.setGroupKey(groupKey);
            }

            returnValue = 1L;
            generator.setCurrentValue((long) added);
        } else {
            if (StringUtils.isNotBlank(groupKey) && !groupKey.equals(generator.getGroupKey())) {
                returnValue = 1L;
                generator.setGroupKey(groupKey);
                generator.setCurrentValue((long) added);
            } else {
                returnValue = generator.getCurrentValue() + 1;
                generator.setCurrentValue(generator.getCurrentValue() + added);
            }
        }
        this.idGeneratorRepository.saveAndFlush(generator);
        return returnValue;
    }

    @Override
    public String generateDateFormatted(String sequence) {
        return generateDateFormatted(sequence, 1);
    }

    @Override
    public String generateDateFormatted(String sequence, int count) {
        Long nextValue = generate(sequence, count);

        String date = CoreDateUtils.formatDate(new Date(), "yyMMdd");
        String str = String.format("%s%03d", date, nextValue % 100);

        return str;
    }
}
