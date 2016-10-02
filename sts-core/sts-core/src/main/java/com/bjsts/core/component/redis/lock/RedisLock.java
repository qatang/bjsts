package com.bjsts.core.component.redis.lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 基于Redis实现的锁, 供不同的Java实例之间使用
 * User: sunshow
 */
public class RedisLock implements Lock {

    protected final transient Logger logger = LoggerFactory.getLogger(this.getClass());

    protected static final String KEY_PREFIX = "__redis_lock_";

    protected static final String KEY_SUFFIX_TEMP = "_temp";

    private String key;
    private long timeout; // key的过期时间
    private TimeUnit timeUnit; // key的过期时间单位
    private boolean locked = false;

    private StringRedisTemplate redisTemplate;

    /**
     * @param redisTemplate redis操作模板
     * @param name 锁的名称
     * @param timeout key过期时间
     * @param timeUnit key过期时间的单位
     */
    public RedisLock(StringRedisTemplate redisTemplate, String name, long timeout, TimeUnit timeUnit) {
        this.redisTemplate = redisTemplate;
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
        if (redisTemplate == null) {
            return false;
        }
        long currentTimeMillis = System.currentTimeMillis();
        String expireStr = String.valueOf(currentTimeMillis + timeUnit.toMillis(timeout) + 1000);

        {
            String tempKey = key + KEY_SUFFIX_TEMP;
            List<Object> txResult = redisTemplate.execute(new SessionCallback<List<Object>>() {
                public List<Object> execute(RedisOperations operations) throws DataAccessException {
                    StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) operations;
                    stringRedisTemplate.multi();

                    // 通过 SETEX 和 RENAMENX 模拟实现 SETNXEX
                    stringRedisTemplate.opsForValue().set(tempKey, expireStr, timeout, timeUnit);   // set没有返回值
                    stringRedisTemplate.renameIfAbsent(tempKey, key);
                    stringRedisTemplate.delete(tempKey);

                    // This will contain the results of all ops in the transaction
                    return stringRedisTemplate.exec();
                }
            });
            // 第一个返回结果即为 RENAMENX 的返回结果
            if (txResult.get(0).equals(Boolean.TRUE)) {
                locked = true;
                return true;
            }
        }

        // 如果未成功创建锁(锁已存在), 判断锁是否已经过期
        String currentValueStr = redisTemplate.opsForValue().get(key);
        if (currentValueStr != null && Long.parseLong(currentValueStr) < currentTimeMillis) {

            List<Object> txResult = redisTemplate.execute(new SessionCallback<List<Object>>() {
                public List<Object> execute(RedisOperations operations) throws DataAccessException {
                    StringRedisTemplate stringRedisTemplate = (StringRedisTemplate) operations;
                    stringRedisTemplate.multi();

                    // 通过 SETEX 和 RENAMENX 模拟实现 SETNXEX
                    stringRedisTemplate.opsForValue().getAndSet(key, expireStr);
                    stringRedisTemplate.expire(key, timeout, timeUnit);

                    // This will contain the results of all ops in the transaction
                    return stringRedisTemplate.exec();
                }
            });

            // 第一个返回结果即为 getAndSet 的返回结果
            if (txResult.size() > 0 && txResult.get(0) != null && txResult.get(0).equals(currentValueStr)) {
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
                if (redisTemplate == null){
                    throw new IllegalStateException("unlock failed, lock key is "+ key +", cause by :connection is null! ");
                }
                redisTemplate.delete(key);
            }
        } finally {
            locked = false;
        }
    }

    @Override
    public Condition newCondition() {
        throw new UnsupportedOperationException();
    }

}
