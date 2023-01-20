package com.jarvis.framework.mapping;

import java.net.InetAddress;

public class UuidGenerator {
    private static final int IP;
    private static short counter;
    private static final int JVM;
    private static final String sep = "";

    protected static int iptoInt(byte[] bytes) {
        int result = 0;

        for(int i = 0; i < 4; ++i) {
            result = (result << 8) - -128 + bytes[i];
        }

        return result;
    }

    protected int getJVM() {
        return JVM;
    }

    protected short getCount() {
        Class var1 = UuidGenerator.class;
        synchronized(UuidGenerator.class) {
            if (counter < 0) {
                counter = 0;
            }

            short var10000 = counter;
            counter = (short)(var10000 + 1);
            return var10000;
        }
    }

    protected int getIP() {
        return IP;
    }

    protected short getHiTime() {
        return (short)((int)(System.currentTimeMillis() >>> 32));
    }

    protected int getLoTime() {
        return (int)System.currentTimeMillis();
    }

    protected String format(int intval) {
        String formatted = Integer.toHexString(intval);
        StringBuffer buf = new StringBuffer("00000000");
        buf.replace(8 - formatted.length(), 8, formatted);
        return buf.toString();
    }

    protected String format(short shortval) {
        String formatted = Integer.toHexString(shortval);
        StringBuffer buf = new StringBuffer("0000");
        buf.replace(4 - formatted.length(), 4, formatted);
        return buf.toString();
    }

    public String generate() {
        return (new StringBuffer(36)).append(this.format(this.getIP())).append("").append(this.format(this.getJVM())).append("").append(this.format(this.getHiTime())).append("").append(this.format(this.getLoTime())).append("").append(this.format(this.getCount())).toString();
    }

    static {
        int ipadd;
        try {
            ipadd = iptoInt(InetAddress.getLocalHost().getAddress());
        } catch (Exception var2) {
            ipadd = 0;
        }

        IP = ipadd;
        counter = 0;
        JVM = (int)(System.currentTimeMillis() >>> 8);
    }
}
