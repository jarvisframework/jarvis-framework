package com.jarvis.framework.cypto.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA加密解密
 *
 * @author qiucs
 * @version 1.0.0 2022年11月4日
 */
public class RSAUtil {

    private static final String KEY_ALGORITHM = "RSA";

    //临时使用
    private static final String RSA_PUB_KEY = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJkmwyqeI59iA02YGcrL+p2FlxbaIzpUHI5oPIV8ESe3w6ohwhk5Uw2On5fL/li1eUgRIod4VsflIniLt7Rh9t8CAwEAAQ==";

    private static final String RSA_PRI_KEY = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAmSbDKp4jn2IDTZgZysv6nYWXFtojOlQcjmg8hXwRJ7fDqiHCGTlTDY6fl8v+WLV5SBEih3hWx+UieIu3tGH23wIDAQABAkABTTgJa7Cn0SBwD2Pgul0V66W+FvqHq2FWwIILLGPE76KGoJb0IRhXJZgugG3gosFVTchp14CgR1JlvePnUddBAiEAztgDL0XV5WZbiLDiXXPnrG2+izLKBfrxXLp7gKa8deECIQC9jDTTwlxRgYq5GTA7wDROby5pivP15kEijawvJL6EvwIhAM4yYNpbT+OjqlQVoVNwG9+0d2LkeqiDhODftgaGMRGBAiEAgIfKaq2pHDvTbaB+7IVw8p7Bwh/PPjdcg6m90FoCNtECIFAEoVgoSho6zgnFx8YRfXBIkBWfiM6mAW14fZLrgwWb";

    /**
     * 初始化密钥
     *
     * @return
     */
    public static RSAKey initKey() {
        try {
            // 随机生成密钥对
            final KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);

            // 按照指定字符串生成密钥对
            keyPairGen.initialize(512, new SecureRandom());

            final KeyPair keyPair = keyPairGen.generateKeyPair();

            // 公钥
            final RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            final String pubkey = new String(Base64.encodeBase64(publicKey.getEncoded()));
            // 私钥
            final RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            final String prikey = new String(Base64.encodeBase64(privateKey.getEncoded()));
            return new RSAKey(pubkey, prikey);
        } catch (final Exception e) {
            throw new RuntimeException("生成RSA秘钥失败", e);
        }
    }

    /**
     * 加密
     *
     * @param data 原文
     * @return 密文
     */
    public static String encrypt(String data) {
        return encrypt(data, RSA_PUB_KEY);
    }

    /**
     * 公钥加密
     *
     * @param data 原文
     * @param key 公钥
     * @return 密文
     */
    public static String encrypt(String data, String key) {
        try (final ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            // 取得公钥
            final X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64.decodeBase64(key));
            final KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            final Key publicKey = keyFactory.generatePublic(x509KeySpec);

            final Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            final byte[] encryptedData = data.getBytes(StandardCharsets.UTF_8);
            final int inputLen = encryptedData.length;

            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > 117) {
                    cache = cipher.doFinal(encryptedData, offSet, 117);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * 117;
            }
            final byte[] decryptedData = out.toByteArray();
            return new String(Base64.encodeBase64(decryptedData), StandardCharsets.UTF_8);
        } catch (final Exception e) {
            throw new RuntimeException("RSA公钥加密异常", e);
        }
    }

    /**
     * 解密
     *
     * @param data 密文
     * @return 原文
     */
    public static String decrypt(String data) {
        return decrypt(data, RSA_PRI_KEY);
    }

    /**
     * 私钥解密
     *
     * @param data 密文
     * @param key 私钥
     * @return 原文
     */
    public static String decrypt(String data, String key) {
        try (final ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            // 对密钥解密
            final byte[] keyBytes = Base64.decodeBase64(key);

            // 取得私钥
            final PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            final KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            final Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

            final Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);

            final byte[] encryptedData = Base64.decodeBase64(data);

            final int inputLen = encryptedData.length;
            int offSet = 0;
            byte[] cache;
            int i = 0;
            // 对数据分段解密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > 128) {
                    cache = cipher.doFinal(encryptedData, offSet, 128);
                } else {
                    cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * 128;
            }
            final byte[] decryptedData = out.toByteArray();
            return new String(decryptedData, StandardCharsets.UTF_8);
        } catch (final Exception e) {
            throw new RuntimeException("RSA私钥解密异常", e);
        }
    }

    /**
     * RSA公私钥
     *
     * @author qiucs
     * @version 1.0.0 2022年11月4日
     */
    public static class RSAKey {

        private final String pubKey;

        private final String priKey;

        public RSAKey(String pubKey, String priKey) {
            this.pubKey = pubKey;
            this.priKey = priKey;
        }

        /**
         * @return 公钥
         */
        public String getPubKey() {
            return pubKey;
        }

        /**
         * @return 私钥
         */
        public String getPriKey() {
            return priKey;
        }
    }

}
