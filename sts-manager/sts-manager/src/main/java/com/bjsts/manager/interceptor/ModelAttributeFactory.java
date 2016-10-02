package com.bjsts.manager.interceptor;

import com.bjsts.core.enums.EnableDisableStatus;
import com.bjsts.core.enums.YesNoStatus;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

/**
 * @author jinsheng
 * @since 2016-06-20 19:19
 */
@Component
public class ModelAttributeFactory {

    private Map<String, List<?>> modelAttributeMap = Maps.newHashMap();

    @PostConstruct
    private void init() {
        modelAttributeMap.put("allEnableDisableStatusList", EnableDisableStatus.listAll());
        modelAttributeMap.put("enableDisableStatusList", EnableDisableStatus.list());

        modelAttributeMap.put("allYesNoStatusList", YesNoStatus.listAll());
        modelAttributeMap.put("yesNoStatusList", YesNoStatus.list());

    }

    List<?> getModelAttributeList(String key) {
        return modelAttributeMap.get(key);
    }

    boolean containsKey(String key) {
        return modelAttributeMap.containsKey(key);
    }
}
