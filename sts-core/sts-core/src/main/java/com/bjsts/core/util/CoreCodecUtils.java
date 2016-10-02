package com.bjsts.core.util;

import com.bjsts.core.exception.util.CodecException;
import com.google.common.base.Charsets;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.zip.CRC32;

/**
 * 算法、摘要、签名、验签、加密、解密
 *
 * @author qatang
 * @since 2015-07-15 10:58
 */
public class CoreCodecUtils {
    private final static Logger logger = LoggerFactory.getLogger(CoreCodecUtils.class);

    public static final String KEY_SHA1 = "SHA1";
    public static final String KEY_MD5 = "MD5";
    public static final String KEY_RSA = "RSA";
    public static final String KEY_DESEDE = "DESede";
    public static final String KEY_AES = "AES";

    private static final String SIGNATURE_ALGORITHM_SHA = "SHA1WithRSA";
    private static final String SIGNATURE_ALGORITHM_MD5 = "MD5WithRSA";

    /**
     * 加密/解密算法/工作模式/填充方式
     */
    public static final String CIPHER_ALGORITHM_DESEDE = "DESede/ECB/PKCS5Padding";
    public static final String CIPHER_ALGORITHM_AES = "AES/CBC/PKCS5PADDING";
    public static final String CIPHER_ALGORITHM_RSA = "RSA/ECB/PKCS1Padding";

    private static final byte[] AES_CBC_INIT_VECTOR = "RandomInitVector".getBytes();

    /**
     * CRC32加密
     *
     * @param source   源数据
     * @param encoding 编码
     * @return crc32摘要
     */
    public static Long encryptCRC32(String source, String encoding) throws CodecException {
        try {
            CRC32 crc32 = new CRC32();
            crc32.update(source.getBytes(encoding));
            return crc32.getValue();
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
            throw new CodecException(e);
        }
    }

    /**
     * CRC32加密
     *
     * @param source   源数据
     * @return crc32摘要
     */
    public static Long encryptCRC32(String source) throws CodecException {
        return CoreCodecUtils.encryptCRC32(source, Charsets.UTF_8.name());
    }

    /**
     * MD5加密
     *
     * @param source   源数据
     * @param encoding 编码
     * @return md5摘要
     */
    public static String encryptMD5(String source, String encoding) throws CodecException {
        try {
            return DigestUtils.md5Hex(source.getBytes(encoding));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
            throw new CodecException(e);
        }
    }

    /**
     * MD5加密
     *
     * @param source 源数据
     * @return 加密结果
     */
    public static String encryptMD5(String source) throws CodecException {
        return CoreCodecUtils.encryptMD5(source, Charsets.UTF_8.name());
    }

    /**
     * SHA1加密
     *
     * @param source   源数据
     * @param encoding 编码
     * @return sha1摘要
     */
    public static String encryptSHA1(String source, String encoding) throws CodecException {
        try {
            return DigestUtils.sha1Hex(source.getBytes(encoding));
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
            throw new CodecException(e);
        }
    }

    /**
     * SHA1加密
     *
     * @param source 源数据
     * @return 加密结果
     */
    public static String encryptSHA1(String source) throws CodecException {
        return CoreCodecUtils.encryptSHA1(source, Charsets.UTF_8.name());
    }

    /**
     * BASE64加密
     *
     * @param source 源数据
     * @return 加密结果
     */
    public static String encryptBASE64(byte[] source) {
        Base64 base64 = new Base64();
        return base64.encodeToString(source);
    }

    /**
     * BASE64解密
     *
     * @param source 源数据
     * @return 解密结果
     */
    public static byte[] decryptBASE64(String source) {
        Base64 base64 = new Base64();
        return base64.decode(source);
    }

    /**
     * HEX加密
     *
     * @param source 源数据
     * @return 加密结果
     */
    public static String encryptHEX(byte[] source) {
        Hex hex = new Hex();
        return new String(hex.encode(source));
    }

    /**
     * HEX解密
     *
     * @param source 源数据
     * @return 解密结果
     */
    public static byte[] decryptHEX(String source) throws CodecException {
        try {
            Hex hex = new Hex();
            return hex.decode(source.getBytes());
        } catch (DecoderException e) {
            logger.error(e.getMessage(), e);
            throw new CodecException(e);
        }
    }

    /**
     * RSA用私钥对信息生成数字签名
     *
     * @param source     源数据
     * @param privateKey 私钥
     * @return 签名结果
     */
    public static byte[] signWithRSAByPrivateKey(byte[] source, byte[] privateKey) throws CodecException {
        try {
            //构造PKCS8EncodedKeySpec对象
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey);
            //指定加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA);
            //取私钥匙对象
            PrivateKey privateKey2 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            //用私钥对信息生成数字签名
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM_SHA);
            signature.initSign(privateKey2);
            signature.update(source);
            return signature.sign();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CodecException(e);
        }
    }

    /**
     * RSA用公钥验证数字签名
     *
     * @param source    源数据
     * @param publicKey 公钥
     * @param sign      签名
     * @return 签名结果
     */
    public static boolean verifyWithRSAByPublicKey(byte[] source, byte[] publicKey, byte[] sign) {
        try {
            //构造X509EncodedKeySpec对象
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey);
            //指定加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA);
            //取公钥匙对象
            PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);
            //用公钥对信息生成数字签名
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM_SHA);
            signature.initVerify(publicKey2);
            signature.update(source);
            //验证签名是否正常
            return signature.verify(sign);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * RSA公钥加密
     *
     * @param source    源数据
     * @param publicKey 公钥
     * @return 加密后的结果
     */
    public static byte[] encryptWithRSAByPublicKey(byte[] source, byte[] publicKey) throws CodecException {
        try {
            //构造X509EncodedKeySpec对象
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey);
            //指定加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA);
            //取公钥匙对象
            PublicKey publicKey2 = keyFactory.generatePublic(x509EncodedKeySpec);
            //加密
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_RSA);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey2);

            return cipher.doFinal(source);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CodecException(e);
        }
    }

    /**
     * RSA私钥解密
     *
     * @param source     源数据
     * @param privateKey 私钥 使用base64编码之后的key
     * @return 解密后的结果
     */
    public static byte[] decryptWithRSAByPrivateKey(byte[] source, byte[] privateKey) throws CodecException {
        try {
            //构造PKCS8EncodedKeySpec对象
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey);
            //指定加密算法
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_RSA);
            //取公钥匙对象
            PrivateKey privateKey2 = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            //解密
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_RSA);
            cipher.init(Cipher.DECRYPT_MODE, privateKey2);

            return cipher.doFinal(source);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CodecException(e);
        }
    }

    /**
     * 3des加密
     *
     * @param source 源数据
     * @param key    私钥 使用base64编码之后的key
     * @return 加密后的结果
     */
    public static byte[] encryptWithDESedeByKey(byte[] source, byte[] key) throws CodecException {
        try {
            //构造DESedeKeySpec对象
            DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(key);
            //指定加密算法
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(KEY_DESEDE);
            //取公钥匙对象
            SecretKey secretKey = secretKeyFactory.generateSecret(deSedeKeySpec);
            //加密
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_DESEDE);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            return cipher.doFinal(source);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CodecException(e);
        }
    }

    /**
     * 3des解密
     *
     * @param source 源数据
     * @param key    私钥 使用base64编码之后的key
     * @return 解密后的结果
     */
    public static byte[] decryptWithDESedeByKey(byte[] source, byte[] key) throws CodecException {
        try {
            //构造DESedeKeySpec对象
            DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(key);
            //指定加密算法
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(KEY_DESEDE);
            //取公钥匙对象
            SecretKey secretKey = secretKeyFactory.generateSecret(deSedeKeySpec);
            //解密
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_DESEDE);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);

            return cipher.doFinal(source);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CodecException(e);
        }
    }

    private static byte[] getHash(final String algorithm, final byte[] data) {
        try {
            final MessageDigest digest = MessageDigest.getInstance(algorithm);
            digest.update(data);
            return digest.digest();
        } catch (final Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    /**
     * AES加密
     * @param source 源数据
     * @param seed 最终加密使用的key的生成种子
     * @return 加密后的结果
     * @throws CodecException
     */
    public static byte[] encryptWithAESByKey(byte[] source, byte[] seed) throws CodecException {
        return encryptWithAESByKey(source, seed, AES_CBC_INIT_VECTOR);
    }

    /**
     * AES解密
     * @param source 源数据
     * @param seed 最终加密使用的key的生成种子
     * @return 解密后的结果
     * @throws CodecException
     */
    public static byte[] decryptWithAESByKey(byte[] source, byte[] seed) throws CodecException {
        return decryptWithAESByKey(source, seed, AES_CBC_INIT_VECTOR);
    }

    /**
     * AES加密
     * @param source 源数据
     * @param seed 最终加密使用的key的生成种子
     * @param initVector 16字节/128bit
     * @return 加密后的结果
     */
    public static byte[] encryptWithAESByKey(byte[] source, byte[] seed, byte[] initVector) throws CodecException {
        if (initVector == null || initVector.length != 16) {
            logger.error("Wrong IV length: must be 16 bytes long");
            throw new CodecException("Wrong IV length: must be 16 bytes long");
        }
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector);

            SecretKeySpec keySpec = new SecretKeySpec(getHash(KEY_MD5, seed), KEY_AES);

            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_AES);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

            return cipher.doFinal(source);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CodecException(e);
        }
    }

    /**
     * AES解密
     * @param source 源数据
     * @param seed 最终解密使用的key的生成种子
     * @param initVector 16字节/128bit
     * @return 解密后的结果
     * @throws CodecException
     */
    public static byte[] decryptWithAESByKey(byte[] source, byte[] seed, byte[] initVector) throws CodecException {
        if (initVector == null || initVector.length != 16) {
            logger.error("Wrong IV length: must be 16 bytes long");
            throw new CodecException("Wrong IV length: must be 16 bytes long");
        }
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector);

            SecretKeySpec keySpec = new SecretKeySpec(getHash(KEY_MD5, seed), KEY_AES);

            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_AES);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);

            return cipher.doFinal(source);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new CodecException(e);
        }
    }
}