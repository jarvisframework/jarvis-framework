package com.yx.framework.tool.util;

import com.jarvis.framework.core.constant.CharsetConstants;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>加密工具类（MD5,BASE64,SHA,CRC32）</p>
 *
 * @author 王涛
 * @see org.apache.commons.codec.digest.DigestUtils
 * @see org.apache.commons.codec.binary.Base64
 * @since 1.0, 2020-12-18 09:31:48
 */
public abstract class CryptoUtils {

    // --------------------md5--------------------

    /**
     * 计算MD5摘要，并返回十六进制字符串（32位密文）
     *
     * @param text 需加密文本
     * @return MD5摘要作为十六进制字符串
     */
    public static String md5Hex(String text) {
        return md5Hex(text, CharsetConstants.UTF8);
    }

    /**
     * 计算MD5摘要，并返回十六进制字符串（32位密文）
     *
     * @param text    需加密文本
     * @param charset 字符集
     * @return MD5摘要作为十六进制字符串
     * @throws RuntimeException 如果加密失败
     */
    public static String md5Hex(String text, String charset) throws RuntimeException {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        try {
            byte[] bytes = text.getBytes(charset);
            return md5Hex(bytes);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 计算MD5摘要，并返回十六进制字符串（32位密文）
     *
     * @param bytes 需加密byte数组
     * @return MD5摘要作为十六进制字符串
     */
    public static String md5Hex(byte[] bytes) {
        return DigestUtils.md5Hex(bytes);
    }

    /**
     * 计算MD5摘要,并返回base64编码字符串(24位密文)
     *
     * @param text 需加密文本
     * @return base64字符编码后的md5密钥
     * @throws RuntimeException 如果加密失败
     */
    public static String md5Base64(String text) throws RuntimeException {

        if (StringUtils.isBlank(text)) {
            return text;
        }
        byte[] digest;
        try {
            digest = md5(text.getBytes(CharsetConstants.UTF8));
            return encodeBase64String(digest);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * MD5加密
     *
     * @param bytes 需加密byte数组
     * @return md5加密数组
     * @throws NoSuchAlgorithmException 如果没有提供者支持指定算法的MessageDigestSpi实现
     */
    public static byte[] md5(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest alg = MessageDigest.getInstance(MessageDigestAlgorithms.MD5);
        alg.update(bytes);
        byte[] digest = alg.digest();
        return digest;
    }


    // --------------------base64--------------------

    /**
     * 使用base64算法对文本数据进行编码并返回字符串
     *
     * @param str 需编码的文本
     * @return base64编码字符串
     */
    public static String encodeBase64String(final String str) {
        return encodeBase64String(str, CharsetConstants.UTF8);
    }

    /**
     * 使用base64算法对文本数据进行编码并返回字符串
     *
     * @param text    需编码的文本
     * @param charset 编码字符集
     * @return base64编码字符串
     * @throws RuntimeException 如果指定的字符集不支持
     */
    public static String encodeBase64String(String text, String charset) throws RuntimeException {
        if (StringUtils.isBlank(text)) {
            return text;
        }
        try {
            byte[] bytes = text.getBytes(charset);
            return encodeBase64String(bytes);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用base64算法对二进制数据进行编码并返回字符串
     *
     * @param bytes 二进制数组
     * @return base64编码字符串
     */
    public static String encodeBase64String(byte[] bytes) {
        if (null == bytes) {
            return null;
        } else {
            return Base64.encodeBase64String(bytes);
        }
    }

    /**
     * 使用base64算法对文本数据进行解码并返回字符串
     *
     * @param text 需解码的文本
     * @return base64解码字符串
     */
    public static String decodeBase64String(String text) {
        return decodeBase64String(text, CharsetConstants.UTF8);
    }

    /**
     * 使用base64算法对文本数据进行解码并返回字符串
     *
     * @param text    需解码的文本
     * @param charset 编码字符集
     * @return base64解码字符串
     * @throws RuntimeException 如果指定的字符集不支持
     */
    public static String decodeBase64String(String text, String charset) throws RuntimeException {
        try {
            byte[] bytes = text.getBytes(charset);
            return new String(Base64.decodeBase64(bytes));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 使用base64算法对二进制数据进行解码并返回字符串
     *
     * @param bytes 二进制数组
     * @return base64解码字符串
     */
    public static String decodeBase64String(byte[] bytes) {
        if (null == bytes) {
            return null;
        } else {
            return new String(Base64.decodeBase64(bytes));
        }
    }

    // ------------------------SHA------------------------

    // ------------------------CRC32------------------------

}
