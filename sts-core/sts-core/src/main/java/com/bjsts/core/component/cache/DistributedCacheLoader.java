package com.bjsts.core.component.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * author: sunshow.
 */
public abstract class DistributedCacheLoader {

    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 默认load数据到缓存的超时时间
     */
    protected final long defaultTimeoutMillis = 3000;

    protected final long defaultLoadingTimeoutMillis = 3000;

    public final static class EmptyCache implements Serializable {
        private static final long serialVersionUID = -1508040097013582141L;
    }

    public static final EmptyCache EMPTY = new EmptyCache();

    /**
     * @param key 缓存key
     * @return 缓存数据, 无缓存则返回 null
     */
    abstract public <V extends Serializable> V getIfPresent(String key);

    /**
     * @param key 缓存key
     * @param v 缓存Value
     * @param ttl
     * @param timeUnit
     * @param <V> 缓存Value的类型
     */
    abstract public <V extends Serializable> void set(String key, V v, long ttl, TimeUnit timeUnit);

    public void setEmpty(String key, long ttl, TimeUnit timeUnit) {
        this.set(key, EMPTY, ttl, timeUnit);
    }

    /**
     * @param key 缓存key
     * @param loader 缓存载入方法
     * @param ttl 缓存存活时间
     * @param timeUnit 缓存存活时间单位
     * @return 缓存数据
     */
    abstract public <V extends Serializable> V getWithLoader(String key, Supplier<V> loader, long ttl, TimeUnit timeUnit);

    /**
     * @param key 缓存key
     * @param loader 缓存载入方法
     * @param ttl 缓存存活时间
     * @param timeUnit 缓存存活时间单位
     * @return 缓存数据
     */
    abstract public <V extends Serializable> V setWithLoader(String key, Supplier<V> loader, long ttl, TimeUnit timeUnit);

    /**
     * @param key 缓存key
     */
    abstract public void del(String key);

    public long getDefaultTimeoutMillis() {
        return defaultTimeoutMillis;
    }

    public long getDefaultLoadingTimeoutMillis() {
        return defaultLoadingTimeoutMillis;
    }
}
