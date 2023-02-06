package com.jarvis.framework.mapping;

import java.net.InetAddress;

/**
 *
 * @author qiucs
 * @version 1.0.0 2021年2月8日
 */
public class UuidGenerator {

    private static final int IP;

    protected static int iptoInt(final byte[] bytes) {
        int result = 0;
        for (int i = 0; i < 4; i++) {
            result = (result << 8) - Byte.MIN_VALUE + bytes[i];
        }
        return result;
    }

    static {
        int ipadd;
        try {
            ipadd = iptoInt(InetAddress.getLocalHost().getAddress());
        } catch (final Exception e) {
            ipadd = 0;
        }
        IP = ipadd;
    }

    private static short counter = (short) 0;

    private static final int JVM = (int) (System.currentTimeMillis() >>> 8);

    /*private UuidGenerator() {
    }*/

    /**
     * Unique across JVMs on this machine (unless they load this class in the
     * same quater second - very unlikely)
     */
    protected int getJVM() {
        return JVM;
    }

    /**
     * Unique in a millisecond for this JVM instance (unless there are >
     * Short.MAX_VALUE instances created in a
     * millisecond)
     */
    protected short getCount() {
        synchronized (UuidGenerator.class) {
            if (counter < 0) {
                counter = 0;
            }
            return counter++;
        }
    }

    /**
     * Unique in a local network
     */
    protected int getIP() {
        return IP;
    }

    /**
     * Unique down to millisecond
     */
    protected short getHiTime() {
        return (short) (System.currentTimeMillis() >>> 32);
    }

    protected int getLoTime() {
        return (int) System.currentTimeMillis();
    }

    private static final String sep = "";

    protected String format(final int intval) {
        final String formatted = Integer.toHexString(intval);
        final StringBuffer buf = new StringBuffer("00000000");
        buf.replace(8 - formatted.length(), 8, formatted);
        return buf.toString();
    }

    protected String format(final short shortval) {
        final String formatted = Integer.toHexString(shortval);
        final StringBuffer buf = new StringBuffer("0000");
        buf.replace(4 - formatted.length(), 4, formatted);
        return buf.toString();
    }

    public String generate() {
        return new StringBuffer(36)
                .append(format(getIP())).append(sep)
                .append(format(getJVM())).append(sep)
                .append(format(getHiTime())).append(sep)
                .append(format(getLoTime())).append(sep)
                .append(format(getCount())).toString();
    }

}
