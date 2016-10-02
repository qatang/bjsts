package com.bjsts.core.component.memcache.lock;

import com.spotify.folsom.GetResult;
import com.spotify.folsom.MemcacheClient;
import com.spotify.folsom.MemcacheStatus;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * 基于Memcached实现的锁, 供不同的Java实例之间使用
 * User: sunshow
 */
public class FolsomSerializableMemcacheLock extends MemcacheLock {

    private MemcacheClient<Serializable> memcacheClient;

    /**
     * @param memcacheClient memcache操作客户端
     * @param name 锁的名称
     * @param timeout key过期时间
     * @param timeUnit key过期时间的单位
     */
    public FolsomSerializableMemcacheLock(MemcacheClient<Serializable> memcacheClient, String name, long timeout, TimeUnit timeUnit) {
        super(name, timeout, timeUnit);
        this.memcacheClient = memcacheClient;
    }

    @Override
    protected boolean addKey(String key, String value, int ttl) {
        try {
            return memcacheClient.add(key, value, ttl).get() == MemcacheStatus.OK;
        } catch (Exception e) {
            logger.error("add key出错, key={}, value={}, ttl={}", key, value, ttl);
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    protected CasValue<String> casGetKey(String key) {
        try {
            GetResult<Serializable> getResult = memcacheClient.casGet(key).get();
            if (getResult == null) {
                return null;
            }
            return new CasValue<>((String)getResult.getValue(), getResult.getCas());
        } catch (Exception e) {
            logger.error("casGet key出错, key=" + key, e);
            return null;
        }
    }

    @Override
    protected boolean casSetKey(String key, String value, int ttl, long cas) {
        try {
            return memcacheClient.set(key, value, ttl, cas).get() == MemcacheStatus.OK;
        } catch (Exception e) {
            logger.error("casSet key出错, key={}, value={}, ttl={}", key, value, ttl);
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    @Override
    protected void deleteKey(String key) {
        memcacheClient.delete(key);
        logger.info("delete key: {}", key);
    }
}
