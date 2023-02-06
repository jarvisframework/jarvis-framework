package com.jarvis.framework.license;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDateTime;

/**
 *
 * @author qiucs
 * @version 1.0.0 2022年3月29日
 */
public class LicenseUtil {

    private static final Logger log = LoggerFactory.getLogger(LicenseUtil.class);

    private static final String KEY = "ces";

    private static final String PREFIX = "注册码:";

    private static final Charset CHARSET = StandardCharsets.UTF_8;

    public static String readLicenseCode() {
        try (InputStream fis = getResource().getInputStream();
             InputStreamReader reader = new InputStreamReader(fis);
             BufferedReader br = new BufferedReader(reader);) {
            String line;
            boolean success = false;
            while (null != (line = br.readLine())) {
                if (line.startsWith(PREFIX)) {
                    line = line.substring(4);
                    success = true;
                    break;
                }
            }
            if (success) {
                return decrypt(line);
            }

        } catch (final Exception e) {
            log.error("读取注册码失败", e);
        }
        return null;
    }

    public static Resource getResource() {

        final DefaultResourceLoader loader = new DefaultResourceLoader();
        final String[] keyResources = new String[] {
                "file:./config/key.license",
                "file:./key.license",
                "classpath:/key.license"
        };
        for (final String path : keyResources) {
            final Resource resource = loader.getResource(path);
            if (resource.exists()) {
                return resource;
            }
        }

        return null;
    }

    public static boolean isExpried(LocalDateTime time, int vaild) {
        final LocalDateTime vaildTime = time.plusDays(vaild);
        return LocalDateTime.now().isAfter(vaildTime);
    }

    private static String decrypt(String content) {

        final byte[] bytes = decrypt(parseHex2Byte(content), KEY);

        if (null == bytes) {
            return null;
        }

        try {
            return CHARSET.newDecoder().decode(ByteBuffer.wrap(bytes)).toString();
        } catch (final CharacterCodingException ex) {
            throw new IllegalArgumentException("Decoding failed", ex);
        }
    }

    private static byte[] parseHex2Byte(String src) {
        if (src.length() < 1) {
            return null;
        }
        final byte[] result = new byte[src.length() / 2];
        for (int i = 0; i < src.length() / 2; i++) {
            final int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);
            final int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    private static byte[] decrypt(byte[] content, String password) {
        try {
            final KeyGenerator kgen = KeyGenerator.getInstance("AES");
            final SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(password.getBytes());
            kgen.init(128, random);
            final SecretKey secretKey = kgen.generateKey();
            final byte[] enCodeFormat = secretKey.getEncoded();
            final SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            final Cipher cipher = Cipher.getInstance("AES");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            final byte[] result = cipher.doFinal(content);
            return result; // 解密
        } catch (final Exception e) {
            log.error("注册码解析失败", e);
        }

        return null;
    }
}
