package com.bjsts.core.component.memcache.cache;

import com.google.common.base.Charsets;
import com.google.common.net.HostAndPort;
import com.spotify.folsom.ConnectFuture;
import com.spotify.folsom.MemcacheClient;
import com.spotify.folsom.MemcacheClientBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * author: sunshow.
 */
public class FolsomCacheLoaderTest {

    MemcacheClient<Serializable> client;

    FolsomCacheLoader folsomCacheLoader;

    @Before
    public void setUp() throws Exception {
        client = MemcacheClientBuilder.newSerializableObjectClient()
                .withKeyCharset(Charsets.UTF_8)
                .withAddress(HostAndPort.fromParts("192.168.0.32", 11211))
                .connectAscii();
        ConnectFuture.connectFuture(client).get();

        folsomCacheLoader = new FolsomCacheLoader(client);
    }

    @After
    public void teardown() {
        client.shutdown();
    }

    @Test
    public void testSetWithLoader() {
        folsomCacheLoader.setWithLoader("test", () -> new TestCacheObject("test123"), 10, TimeUnit.MINUTES);
    }

    @Test
    public void testGetIfPresent() {
        TestCacheObject object = folsomCacheLoader.getIfPresent("test");
        Assert.assertEquals(object.getValue(), "test123");
    }

    @Test
    public void testEmptyCacheGetIfPresent() {
        String key = "test_empty";

        folsomCacheLoader.setWithLoader(key, null, 10, TimeUnit.MINUTES);

        TestCacheObject object = folsomCacheLoader.getIfPresent(key);
        Assert.assertNull(object);
    }

    @Test
    public void testGetWithLoader() {
        TestCacheObject object = folsomCacheLoader.getWithLoader("test", () -> new TestCacheObject("test123"), 10, TimeUnit.SECONDS);
        Assert.assertEquals(object.getValue(), "test123");

        object = folsomCacheLoader.getIfPresent("test");
        Assert.assertEquals(object.getValue(), "test123");
    }

    @Test
    public void testDel() {
        folsomCacheLoader.del("test");
    }
}

class TestCacheObject implements Serializable {

    private static final long serialVersionUID = 1786656112490530038L;

    private String value;

    public TestCacheObject(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}