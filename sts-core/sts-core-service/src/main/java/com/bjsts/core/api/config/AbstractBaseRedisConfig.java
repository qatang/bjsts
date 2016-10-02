package com.bjsts.core.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Redis配置
 */
public abstract class AbstractBaseRedisConfig {

    @Autowired
    private Environment env;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
        jedisConnectionFactory.setHostName(env.getProperty("redis.host"));
        jedisConnectionFactory.setPassword(env.getProperty("redis.password"));
        jedisConnectionFactory.setPort(Integer.parseInt(env.getProperty("redis.port")));
        jedisConnectionFactory.setDatabase(Integer.parseInt(env.getProperty("redis.database", "0")));
        jedisConnectionFactory.setUsePool(true);

        return jedisConnectionFactory;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(jedisConnectionFactory());

        return stringRedisTemplate;
    }
}
