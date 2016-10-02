package com.bjsts.core.component.memcache.lock;

import com.google.common.base.Charsets;
import com.google.common.net.HostAndPort;
import com.spotify.folsom.ConnectFuture;
import com.spotify.folsom.MemcacheClient;
import com.spotify.folsom.MemcacheClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * author: sunshow.
 */
public class FolsomMemcacheLockTest {

    MemcacheClient<String> client;

    @Before
    public void setUp() throws Exception {
        client = MemcacheClientBuilder.newStringClient()
                .withKeyCharset(Charsets.UTF_8)
                .withAddress(HostAndPort.fromParts("192.168.0.32", 11211))
                .connectAscii();
        ConnectFuture.connectFuture(client).get();
    }

    @Test
    public void multiThreadTest() throws Exception {
        Runnable runnable = () -> {
            String key = "test";
            MemcacheLock memcacheLock = new FolsomMemcacheLock(client, key, 30, TimeUnit.SECONDS);
            boolean hasLocked = false;
            try {
                hasLocked = memcacheLock.tryLock(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!hasLocked) {
                System.out.println("获取锁失败: " + this);
            } else {
                System.out.println("获取锁成功: " + this);
            }
            try {
                System.out.println("执行内容");
                synchronized (this) {
                    try {
                        this.wait(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } finally {
                if (hasLocked) {
                    memcacheLock.unlock();
                }
            }
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);

        thread1.start();
        synchronized (this) {
            this.wait(5000);
            thread2.start();
            this.wait(30000);
        }
    }

    @After
    public void teardown() {
        client.shutdown();
    }
}
