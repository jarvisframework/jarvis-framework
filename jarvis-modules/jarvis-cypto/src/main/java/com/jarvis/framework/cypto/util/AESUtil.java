package com.jarvis.framework.cypto.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * AES加密解密
 *
 * @author Doug Wang
 * @version 1.0.0 2022年3月18日
 */
public class AESUtil {

    //key为16位
    private static final String KEY = "uOcIbOgR6Im7q5ee";

    private static final String IV = "IcOhVLiQx5CCcY2V";

    private static final Charset CHARSET = StandardCharsets.UTF_8;

    /**
     * 加密方法
     *
     * @param data 要加密的数据
     * @param key 加密key
     * @param iv 加密iv
     * @return 加密的结果
     * @throws Exception
     */
    public static String encrypt(String data, String key, String iv) {
        try {

            final Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");//"算法/模式/补码方式"NoPadding PkcsPadding
            final int blockSize = cipher.getBlockSize();

            final byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }

            final byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            final SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            final IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            final byte[] encrypted = cipher.doFinal(plaintext);

            return new Base64().encodeToString(encrypted);

        } catch (final Exception e) {
            throw new RuntimeException("AES加密出错", e);
        }
    }

    /**
     * 解密方法
     *
     * @param data 要解密的数据
     * @param key 解密key
     * @param iv 解密iv
     * @return 解密的结果
     * @throws Exception
     */
    public static String decrypt(String data, String key, String iv) {
        try {
            final byte[] encrypted = new Base64().decode(data);

            final Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            final SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            final IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            final byte[] original = cipher.doFinal(encrypted);
            final int blockSize = cipher.getBlockSize();
            final int length = original.length;
            if (0 == length % blockSize) {
                // 解码后的数据正好是块大小的整数倍，说明可能存在补0的情况，去掉末尾所有的0
                int i = length - 1;
                while (i >= 0 && 0 == original[i]) {
                    i--;
                }
                final int newSize = i + 1;
                final byte[] newArray = new byte[newSize];
                System.arraycopy(original, 0, newArray, 0, Math.min(original.length, newSize));
                return new String(newArray, CHARSET);
            }
            final String text = new String(original, CHARSET);
            return text;
        } catch (final Exception e) {
            throw new RuntimeException("AES解密出错", e);
        }
    }

    /**
     * 使用默认的key和iv加密
     *
     * @param data 加密数据
     * @return
     * @throws Exception
     */
    public static String encrypt(String data) {
        return encrypt(data, KEY, IV);
    }

    /**
     * 使用默认的iv解密
     *
     * @param data 解密数据
     * @return
     * @throws Exception
     */
    public static String decrypt(String data) {
        return decrypt(data, KEY, IV);
    }

    /**
     * 使用默认的key和iv加密
     *
     * @param data 加密数据
     * @param key 加密key
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key) {
        return encrypt(data, key, IV);
    }

    /**
     * 使用默认的iv解密
     *
     * @param data 解密数据
     * @param key 解密key
     * @return
     * @throws Exception
     */
    public static String decrypt(String data, String key) {
        return decrypt(data, key, IV);
    }

}
