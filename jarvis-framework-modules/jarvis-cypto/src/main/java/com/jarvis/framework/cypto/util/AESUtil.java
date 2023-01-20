package com.jarvis.framework.cypto.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class AESUtil {
    private static final String KEY = "uOcIbOgR6Im7q5ee";
    private static final String IV = "IcOhVLiQx5CCcY2V";
    private static final Charset CHARSET;

    static {
        CHARSET = StandardCharsets.UTF_8;
    }

    public static String encrypt(String data, String key, String iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength += blockSize - plaintextLength % blockSize;
            }

            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(1, keyspec, ivspec);
            byte[] encrypted = cipher.doFinal(plaintext);
            return (new Base64()).encodeToString(encrypted);
        } catch (Exception var11) {
            throw new RuntimeException("AES加密出错", var11);
        }
    }

    public static String decrypt(String data, String key, String iv) {
        try {
            byte[] encrypted = (new Base64()).decode(data);
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());
            cipher.init(2, keyspec, ivspec);
            byte[] original = cipher.doFinal(encrypted);
            int blockSize = cipher.getBlockSize();
            int length = original.length;
            if (length % blockSize != 0) {
                String text = new String(original, CHARSET);
                return text;
            } else {
                int i;
                for(i = length - 1; i >= 0 && original[i] == 0; --i) {
                }

                int newSize = i + 1;
                byte[] newArray = new byte[newSize];
                System.arraycopy(original, 0, newArray, 0, Math.min(original.length, newSize));
                return new String(newArray, CHARSET);
            }
        } catch (Exception var13) {
            throw new RuntimeException("AES解密出错", var13);
        }
    }

    public static String encrypt(String data) {
        return encrypt(data, "uOcIbOgR6Im7q5ee", "IcOhVLiQx5CCcY2V");
    }

    public static String decrypt(String data) {
        return decrypt(data, "uOcIbOgR6Im7q5ee", "IcOhVLiQx5CCcY2V");
    }

    public static String encrypt(String data, String key) {
        return encrypt(data, key, "IcOhVLiQx5CCcY2V");
    }

    public static String decrypt(String data, String key) {
        return decrypt(data, key, "IcOhVLiQx5CCcY2V");
    }
}
