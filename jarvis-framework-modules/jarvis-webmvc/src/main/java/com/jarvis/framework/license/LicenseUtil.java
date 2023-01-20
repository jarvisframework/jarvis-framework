package com.jarvis.framework.license;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

public class LicenseUtil {
    private static final Logger log = LoggerFactory.getLogger(LicenseUtil.class);
    private static final String KEY = "ces";
    private static final String PREFIX = "注册码:";
    private static final Charset CHARSET;

    public LicenseUtil() {
    }

    public static String readLicenseCode() {
        try {
            InputStream fis = getResource().getInputStream();
            Throwable var1 = null;

            try {
                InputStreamReader reader = new InputStreamReader(fis);
                Throwable var3 = null;

                try {
                    BufferedReader br = new BufferedReader(reader);
                    Throwable var5 = null;

                    try {
                        boolean success = false;

                        while(true) {
                            String line;
                            if (null != (line = br.readLine())) {
                                if (!line.startsWith("注册码:")) {
                                    continue;
                                }

                                line = line.substring(4);
                                success = true;
                            }

                            if (!success) {
                                return null;
                            }

                            String var8 = decrypt(line);
                            return var8;
                        }
                    } catch (Throwable var61) {
                        var5 = var61;
                        throw var61;
                    } finally {
                        if (br != null) {
                            if (var5 != null) {
                                try {
                                    br.close();
                                } catch (Throwable var60) {
                                    var5.addSuppressed(var60);
                                }
                            } else {
                                br.close();
                            }
                        }

                    }
                } catch (Throwable var63) {
                    var3 = var63;
                    throw var63;
                } finally {
                    if (reader != null) {
                        if (var3 != null) {
                            try {
                                reader.close();
                            } catch (Throwable var59) {
                                var3.addSuppressed(var59);
                            }
                        } else {
                            reader.close();
                        }
                    }

                }
            } catch (Throwable var65) {
                var1 = var65;
                throw var65;
            } finally {
                if (fis != null) {
                    if (var1 != null) {
                        try {
                            fis.close();
                        } catch (Throwable var58) {
                            var1.addSuppressed(var58);
                        }
                    } else {
                        fis.close();
                    }
                }

            }
        } catch (Exception var67) {
            log.error("读取注册码失败", var67);
            return null;
        }
    }

    public static Resource getResource() {
        DefaultResourceLoader loader = new DefaultResourceLoader();
        String[] keyResources = new String[]{"file:./config/key.license", "file:./key.license", "classpath:/key.license"};
        String[] var2 = keyResources;
        int var3 = keyResources.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String path = var2[var4];
            Resource resource = loader.getResource(path);
            if (resource.exists()) {
                return resource;
            }
        }

        return null;
    }

    public static boolean isExpried(LocalDateTime time, int vaild) {
        LocalDateTime vaildTime = time.plusDays((long)vaild);
        return LocalDateTime.now().isAfter(vaildTime);
    }

    private static String decrypt(String content) {
        byte[] bytes = decrypt(parseHex2Byte(content), "ces");
        if (null == bytes) {
            return null;
        } else {
            try {
                return CHARSET.newDecoder().decode(ByteBuffer.wrap(bytes)).toString();
            } catch (CharacterCodingException var3) {
                throw new IllegalArgumentException("Decoding failed", var3);
            }
        }
    }

    private static byte[] parseHex2Byte(String src) {
        if (src.length() < 1) {
            return null;
        } else {
            byte[] result = new byte[src.length() / 2];

            for(int i = 0; i < src.length() / 2; ++i) {
                int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);
                int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);
                result[i] = (byte)(high * 16 + low);
            }

            return result;
        }
    }

    private static byte[] decrypt(byte[] content, String password) {
        try {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(password.getBytes());
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(2, key);
            byte[] result = cipher.doFinal(content);
            return result;
        } catch (Exception var9) {
            log.error("注册码解析失败", var9);
            return null;
        }
    }

    static {
        CHARSET = StandardCharsets.UTF_8;
    }
}
