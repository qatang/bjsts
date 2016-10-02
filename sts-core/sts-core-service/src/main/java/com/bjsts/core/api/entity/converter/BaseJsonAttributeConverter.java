package com.bjsts.core.api.entity.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import java.lang.reflect.ParameterizedType;

/**
 * 数据库字符串字段和JSON抽象转换器
 * @author sunshow
 */
public abstract class BaseJsonAttributeConverter<T> implements AttributeConverter<T, String> {

    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    @SuppressWarnings("unchecked")
    protected Class<T> getActualType() {
        ParameterizedType paramType = (ParameterizedType)this.getClass().getGenericSuperclass();

        return  (Class<T>)paramType.getActualTypeArguments()[0];
    }

    @Override
    public String convertToDatabaseColumn(T attribute) {
        try {
            return this.getObjectMapper().writeValueAsString(attribute);
        } catch (Exception e) {
            logger.error("序列化JSON出错", e);
            return null;
        }
    }

    @Override
    public T convertToEntityAttribute(String dbData) {
        try {
            return this.readValueFromDatabaseColumn(dbData);
        } catch (Exception e) {
            logger.error("反序列化参与日志详情出错, dbData=" + dbData, e);
            return null;
        }
    }

    /**
     * 将数据库内容反序列化成对象
     * @param dbData 数据库字符串内容
     * @return 反序列化后的对象
     * @throws Exception
     */
    protected T readValueFromDatabaseColumn(String dbData) throws Exception {
        return this.getObjectMapper().readValue(dbData, this.getActualType());
    }

    /**
     * @return JSON转换器
     */
    abstract protected ObjectMapper getObjectMapper();
}
