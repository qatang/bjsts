package com.bjsts.core.util;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.SocketTimeoutException;

/**
 * author: qatang.
 */
public class CoreHttpUtilsTest {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void getTest() throws Exception {
        String response = CoreHttpUtils.get("http://httpbin.org/get");
        logger.info(response);
    }

    @Test(expected = SocketTimeoutException.class)
    public void getTimeoutTest() throws Exception {
        String response = CoreHttpUtils.get("http://httpbin.org/delay/5", Charsets.UTF_8.name(), 3);
        logger.info(response);
    }

    @Test
    public void getUTF8Test() throws Exception {
        String response = CoreHttpUtils.get("http://httpbin.org/encoding/utf8", Charsets.UTF_8.name(), 3);
        logger.info(response);
    }

    @Test
    public void postWithStringBodyTest() throws Exception {
        String response = CoreHttpUtils.postWithStringBody("http://httpbin.org/post", ImmutableMap.of("aaa", "bbb"), "{'key' : '卧槽'}", CoreHttpUtils.HttpMediaType.APPLICATION_JSON, Charsets.UTF_8.name(), 3);
        logger.info(response);
//        String response = CoreHttpUtils.postWithStringBody("http://127.0.0.1:10086/test", ImmutableMap.of("aaa", "bbb"), "{'key' : '卧槽'}", CoreHttpUtils.HttpMediaType.APPLICATION_JSON, Charsets.UTF_8.name(), 3);
    }

    @Test
    public void postTextTest() throws Exception {
        String response = CoreHttpUtils.postTEXT("http://httpbin.org/post", "卧槽");
    }

    @Test
    public void postJSONTest() throws Exception {
        String response = CoreHttpUtils.postJSON("http://httpbin.org/post", "{'key' : '卧槽'}");
    }

    @Test
    public void postWithUrlEncodedFormBodyTest() throws Exception {
        String response = CoreHttpUtils.postWithUrlEncodedFormBody("http://httpbin.org/post", ImmutableMap.of("aaa", "bbb"), ImmutableMap.of("key", "卧槽"), Charsets.UTF_8.name(), 3);
        logger.info(response);
    }
}
