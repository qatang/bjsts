package com.bjsts.core.component.memcache.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 基于Memcached实现的锁, 供不同的Java实例之间使用
 * User: sunshow
 */
public abstract class MemcacheLock implements Lock {

    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    protected static final String KEY_PREFIX = "__memcached_lock_";

    private String key;
    private long timeout; // key的过期时间
    private TimeUnit timeUnit; // key的过期时间单位
    private boolean locked = false;

    /**
     * @param name 锁的名称
     * @param timeout key过期时间
     * @param timeUnit key过期时间的单位
     */
    public MemcacheLock(String name, long timeout, TimeUnit timeUnit) {
        this.key = KEY_PREFIX + name;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    @Override
    public void lock() {
        try {
            lockInterruptibly();
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        while (!tryLock()) {

        }
    }

    @Override
    public boolean tryLock() {
        long currentTimeMillis = System.currentTimeMillis();
        String expireStr = String.valueOf(currentTimeMillis + timeUnit.toMillis(timeout) + 1000);

        if (this.addKey(key, expireStr, (int) timeout)) {
            locked = true;
            return true;
        }

        // 如果未成功创建锁(锁已存在), 判断锁是否已经过期
        CasValue<String> casValue = this.casGetKey(key);
        if (casValue == null) {
            return false;
        }
        long cas = casValue.getCas();
        String currentValueStr = casValue.getValue();

        if (currentValueStr != null && Long.parseLong(currentValueStr) < currentTimeMillis) {
            // 锁已过期的情况, 删除并设置新的锁
            if (this.casSetKey(key, expireStr, (int) timeout, cas)) {
                locked = true;
                return true;
            }
        }
        return false;

    }

    @Override
    public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
        long timeout = timeUnit.toMillis(l);// timeout单位毫秒
        while (!tryLock()) {
            if (timeout <= 0) {
                return false;
            }
            timeout -= 100;
            synchronized (this) {
                this.wait(100);
            }
        }
        return true;
    }

    @Override
    public void unlock() {
        try {
            if (locked) {
                this.deleteKey(key);
            }
        } finally {
            locked = false;
        }
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }

    /**
     * add mckey
     * @param key 锁的key
     * @param value 锁的到期时间
     * @param ttl 存活秒数
     * @return add key 是否成功
     */
    abstract protected boolean addKey(String key, String value, int ttl);

    /**
     * cas get mckey
     * @param key 锁的key
     * @return key value (到期时间)
     */
    abstract protected CasValue<String> casGetKey(String key);

    /**
     * cas set mckey
     * @param key 锁的key
     * @param value 锁的值
     * @param cas cas
     * @return 是否成功
     */
    abstract protected boolean casSetKey(String key, String value, int ttl, long cas);

    /**
     * 删除mckey
     */
    abstract protected void deleteKey(String key);
}

class CasValue<T> {
    private long cas;

    private T value;

    public CasValue(T value, long cas) {
        this.value = value;
        this.cas = cas;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public long getCas() {
        return cas;
    }

    public void setCas(long cas) {
        this.cas = cas;
    }
}