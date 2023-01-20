package com.yx.framework.tool.util;

/**
 * <p>数值操作工具类</p>
 *
 * @author 王涛
 * @since 1.0, 2021-01-18 10:07:53
 */
public class NumberUtils {

    /**
     * int值转byte数组，使用大端字节序（高位字节在前，低位字节在后）<br>
     * 见：http://www.ruanyifeng.com/blog/2016/11/byte-order.html
     *
     * @param value 值
     * @return byte数组
     * @since 4.4.5
     */
    public static byte[] toBytes(int value) {
        final byte[] result = new byte[4];

        result[0] = (byte) (value >> 24);
        result[1] = (byte) (value >> 16);
        result[2] = (byte) (value >> 8);
        result[3] = (byte) (value);

        return result;
    }

    /**
     * byte数组转int，使用大端字节序（高位字节在前，低位字节在后）<br>
     * 见：http://www.ruanyifeng.com/blog/2016/11/byte-order.html
     *
     * @param bytes byte数组
     * @return int
     * @since 4.4.5
     */
    public static int toInt(byte[] bytes) {
        return (bytes[0] & 0xff) << 24
                | (bytes[1] & 0xff) << 16
                | (bytes[2] & 0xff) << 8
                | (bytes[3] & 0xff);
    }

    /**
     * 字符转int
     *
     * @param str 字符型整数
     * @return 转换结果
     */
    public static int toInt(String str) {
        return toInt(str, 0);
    }

    /**
     * 字符转int
     *
     * @param str          字符型整数
     * @param defaultValue 默认值
     * @return 转换结果
     */
    public static int toInt(String str, int defaultValue) {
        if (str == null) {
            return defaultValue;
        } else {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException var3) {
                return defaultValue;
            }
        }
    }

    /**
     * 字符转数long
     *
     * @param str 字符型长整数
     * @return 转换结果
     */
    public static long toLong(String str) {
        return toLong(str, 0L);
    }

    /**
     * 字符转数long
     *
     * @param str          字符型长整数
     * @param defaultValue 默认值
     * @return 转换结果
     */
    public static long toLong(String str, long defaultValue) {
        if (str == null) {
            return defaultValue;
        } else {
            try {
                return Long.parseLong(str);
            } catch (NumberFormatException var4) {
                return defaultValue;
            }
        }
    }

    public static byte toByte(String str) {
        return toByte(str, (byte)0);
    }

    public static byte toByte(String str, byte defaultValue) {
        if (str == null) {
            return defaultValue;
        } else {
            try {
                return Byte.parseByte(str);
            } catch (NumberFormatException var3) {
                return defaultValue;
            }
        }
    }

    public static short toShort(String str) {
        return toShort(str, (short)0);
    }

    public static short toShort(String str, short defaultValue) {
        if (str == null) {
            return defaultValue;
        } else {
            try {
                return Short.parseShort(str);
            } catch (NumberFormatException var3) {
                return defaultValue;
            }
        }
    }

    public static double toDouble(String str) {
        return toDouble(str, 0.0D);
    }

    public static double toDouble(String str, double defaultValue) {
        if (str == null) {
            return defaultValue;
        } else {
            try {
                return Double.parseDouble(str);
            } catch (NumberFormatException var4) {
                return defaultValue;
            }
        }
    }

    public static float toFloat(String str) {
        return toFloat(str, 0.0F);
    }

    public static float toFloat(String str, float defaultValue) {
        if (str == null) {
            return defaultValue;
        } else {
            try {
                return Float.parseFloat(str);
            } catch (NumberFormatException var3) {
                return defaultValue;
            }
        }
    }
}
