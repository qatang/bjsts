package com.bjsts.core.component.memcache.cache;

import com.spotify.folsom.MemcacheClient;
import com.spotify.folsom.MemcacheStatus;
import com.bjsts.core.component.cache.DistributedCacheLoader;
import com.bjsts.core.component.memcache.lock.FolsomSerializableMemcacheLock;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * author: sunshow.
 */
public class FolsomCacheLoader extends DistributedCacheLoader {

    private MemcacheClient<Serializable> memcacheClient;

    public FolsomCacheLoader(MemcacheClient<Serializable> memcacheClient) {
        this.memcacheClient = memcacheClient;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V extends Serializable> V getIfPresent(String key) {
        try {
            Serializable v = memcacheClient.get(key).get();
            if (v == null || v instanceof EmptyCache) {
                return null;
            }
            return (V) v;
        } catch (Exception e) {
            logger.error("获取缓存出错, key=" + key, e);
            return null;
        }
    }

    @Override
    public <V extends Serializable> void set(String key, V v, long ttl, TimeUnit timeUnit) {
        try {
            MemcacheStatus memcacheStatus;
            if (v != null) {
                memcacheStatus = memcacheClient.set(key, v, (int) timeUnit.toSeconds(ttl)).get(defaultTimeoutMillis, TimeUnit.MILLISECONDS);
            } else {
                memcacheStatus = memcacheClient.set(key, EMPTY, (int) timeUnit.toSeconds(ttl)).get(defaultTimeoutMillis, TimeUnit.MILLISECONDS);
            }
            if (memcacheStatus != MemcacheStatus.OK) {
                logger.error("设置缓存结果状态不正确, key={}, status={}", key, memcacheStatus);
            }
        } catch (Exception e) {
            logger.error("设置缓存出错, key=" + key, e);
        }
    }

    @Override
    public <V extends Serializable> V getWithLoader(String key, Supplier<V> loader, long ttl, TimeUnit timeUnit) {
        V v = this.getIfPresent(key);

        if (v == null) {
            FolsomSerializableMemcacheLock memcacheLock = new FolsomSerializableMemcacheLock(this.memcacheClient, "cache_loader_" + key, defaultLoadingTimeoutMillis, TimeUnit.MILLISECONDS);

            boolean hasLocked = false;
            try {
                hasLocked = memcacheLock.tryLock(defaultLoadingTimeoutMillis, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }

            try {
                v = this.getIfPresent(key);
                if (v == null) {
                    v = this.setWithLoader(key, loader, ttl, timeUnit);
                }
            } finally {
                if (hasLocked) {
                    memcacheLock.unlock();
                }
            }
        }

        return v;
    }

    @Override
    public <V extends Serializable> V setWithLoader(String key, Supplier<V> loader, long ttl, TimeUnit timeUnit) {
        V v = null;

        if (loader != null) {
            v = loader.get();
        }

        this.set(key, v, ttl, timeUnit);

        return v;
    }

    @Override
    public void del(String key) {
        try {
            MemcacheStatus memcacheStatus = memcacheClient.delete(key).get();
            if (memcacheStatus != MemcacheStatus.OK) {
                logger.error("删除缓存结果状态不正确, key={}, status={}", key, memcacheStatus);
            }
        } catch (Exception e) {
            logger.error("设置缓存出错, key=" + key, e);
        }
    }
}
