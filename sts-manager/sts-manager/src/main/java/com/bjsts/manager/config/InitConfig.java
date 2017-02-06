package com.bjsts.manager.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author jinsheng
 * @since 2016-04-26 16:43
 */
@Configuration
@Import(value = {JpaConfig.class, ShiroConfig.class})
public class InitConfig {
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

/*        SimpleModule simpleModule = new SimpleModule();

        // 注册自定义enum序列化
        simpleModule.addSerializer(EnableDisableStatus.class, new CustomValueEnumSerializer<>());
        simpleModule.addSerializer(YesNoStatus.class, new CustomValueEnumSerializer<>());

        // 注册自定义enum反序列化
        simpleModule.addDeserializer(EnableDisableStatus.class, new CustomValueEnumDeserializer<EnableDisableStatus>() {});
        simpleModule.addDeserializer(YesNoStatus.class, new CustomValueEnumDeserializer<YesNoStatus>() {});

        objectMapper.registerModule(simpleModule);*/

        return objectMapper;
    }
}
