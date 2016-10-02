package com.bjsts.core.util;

import com.google.common.base.Charsets;
import com.google.common.net.UrlEscapers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.net.URLEncoder;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * author: sunshow.
 */
public class CoreCodecUtilsTest {

    private ThreadLocal<String> publicKeyThreadLocal = new ThreadLocal<>();

    private ThreadLocal<String> privateKeyThreadLocal = new ThreadLocal<>();

    @Before
    public void generateRSATest() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(CoreCodecUtils.KEY_RSA);
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        //公钥
        RSAPublicKey publicKey1 = (RSAPublicKey) keyPair.getPublic();
        //私钥
        RSAPrivateKey privateKey1 = (RSAPrivateKey) keyPair.getPrivate();

        String publicKeyStr = CoreCodecUtils.encryptBASE64(publicKey1.getEncoded());
        System.out.println(publicKeyStr);
        publicKeyThreadLocal.set(publicKeyStr);

        String privateKeyStr = CoreCodecUtils.encryptBASE64(privateKey1.getEncoded());
        System.out.println(privateKeyStr);
        privateKeyThreadLocal.set(privateKeyStr);
    }

    @Test
    public void encryptAndDecryptRSATest() throws Exception {
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);

        String encryptByPublic = CoreCodecUtils.encryptBASE64(CoreCodecUtils.encryptWithRSAByPublicKey(uuid.getBytes(Charsets.UTF_8), CoreCodecUtils.decryptBASE64(publicKeyThreadLocal.get())));
        System.out.println(encryptByPublic);

        byte[] decryptBytes = CoreCodecUtils.decryptWithRSAByPrivateKey(CoreCodecUtils.decryptBASE64(encryptByPublic), CoreCodecUtils.decryptBASE64(privateKeyThreadLocal.get()));
        Assert.assertNotNull(decryptBytes);

        String decryptByPrivate = new String(decryptBytes, Charsets.UTF_8);
        System.out.println(decryptByPrivate);

        Assert.assertEquals(uuid, decryptByPrivate);
    }

    @Test
    public void encryptAndDecryptDESedeTest() throws Exception {
        String uuid = UUID.randomUUID().toString();
        System.out.println(uuid);

        String source = CoreStringUtils.generateRandomStr(128);
        System.out.println(source);

        byte[] encryptBytes = CoreCodecUtils.encryptWithDESedeByKey(source.getBytes(Charsets.UTF_8), uuid.getBytes(Charsets.UTF_8));
        byte[] decryptBytes = CoreCodecUtils.decryptWithDESedeByKey(encryptBytes, uuid.getBytes(Charsets.UTF_8));

        String decryptStr = new String(decryptBytes, Charsets.UTF_8);
        System.out.println(decryptStr);

        Assert.assertEquals(source, decryptStr);
    }

    @Test
    public void encryptAndDecryptAESTest() throws Exception {
        String content = "卧槽";
        String password = "0123456789012345";

        byte[] result = CoreCodecUtils.encryptWithAESByKey(content.getBytes(), password.getBytes(Charsets.UTF_8));
        System.out.println(CoreCodecUtils.encryptBASE64(result));

        byte[] result2 = CoreCodecUtils.decryptWithAESByKey(result, password.getBytes(Charsets.UTF_8));
        String decryptStr = new String(result2, Charsets.UTF_8);
        System.out.println(decryptStr);

        Assert.assertEquals(content, decryptStr);
    }

    @Test
    public void encryptCRC32Test() throws Exception {
        for (int i = 0; i < 20; i ++) {
            String a = CoreStringUtils.generateRandomStr(20);
            System.out.println(a + " : " + CoreCodecUtils.encryptCRC32(a));
        }
    }

    @Test
    public void urlEncodeTest() throws Exception {
        String src = "您已超过单日提现次数（5次），如有提款需求请明日申请。 客服热线：400-7766-981";
        String urlEncoderResult = URLEncoder.encode(src, "UTF-8");
        System.out.println(urlEncoderResult);
        String urlPathSegmentResult = UrlEscapers.urlPathSegmentEscaper().escape(src);
        System.out.println(urlPathSegmentResult);
    }
}
