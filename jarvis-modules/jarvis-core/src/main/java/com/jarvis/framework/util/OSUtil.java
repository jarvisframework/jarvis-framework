package com.jarvis.framework.util;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2022年3月25日
 */
public final class OSUtil {

    private static String OS = System.getProperty("os.name").toLowerCase();

    private static OSUtil INSTANCE = new OSUtil();

    private OSName osname;

    private OSUtil() {
    }

    public static boolean isLinux() {
        return OS.indexOf("linux") >= 0;
    }

    public static boolean isUnix() {
        return OS.indexOf("unix") > 0;
    }

    public static boolean isMacOS() {
        return OS.indexOf("mac") >= 0 && OS.indexOf("os") > 0;
    }

    public static boolean isWindows() {
        return OS.indexOf("windows") >= 0;
    }

    public static boolean isOS2() {
        return OS.indexOf("os/2") >= 0;
    }

    public static boolean isSolaris() {
        return OS.indexOf("solaris") >= 0;
    }

    public static boolean isSunOS() {
        return OS.indexOf("sunos") >= 0;
    }

    public static boolean isMPEiX() {
        return OS.indexOf("mpe/ix") >= 0;
    }

    public static boolean isHPUX() {
        return OS.indexOf("hp-ux") >= 0;
    }

    public static boolean isAix() {
        return OS.indexOf("aix") >= 0;
    }

    public static boolean isOS390() {
        return OS.indexOf("os/390") >= 0;
    }

    public static boolean isFreeBSD() {
        return OS.indexOf("freebsd") >= 0;
    }

    public static boolean isIrix() {
        return OS.indexOf("irix") >= 0;
    }

    public static boolean isNetWare() {
        return OS.indexOf("netware") >= 0;
    }

    public static boolean isOSF1() {
        return OS.indexOf("osf1") >= 0;
    }

    public static boolean isOpenVMS() {
        return OS.indexOf("openvms") >= 0;
    }

    /**
     * 获取操作系统名字
     *
     * @return 操作系统名
     */
    public static String getOSname() {
        if (isAix()) {
            INSTANCE.osname = OSName.AIX;
        } else if (isUnix()) {
            INSTANCE.osname = OSName.Unix;
        } else if (isFreeBSD()) {
            INSTANCE.osname = OSName.FreeBSD;
        } else if (isHPUX()) {
            INSTANCE.osname = OSName.HP_UX;
        } else if (isIrix()) {
            INSTANCE.osname = OSName.Irix;
        } else if (isLinux()) {
            INSTANCE.osname = OSName.Linux;
        } else if (isMacOS()) {
            INSTANCE.osname = OSName.Mac;
        } else if (isMPEiX()) {
            INSTANCE.osname = OSName.MPEiX;
        } else if (isNetWare()) {
            INSTANCE.osname = OSName.NetWare_411;
        } else if (isOpenVMS()) {
            INSTANCE.osname = OSName.OpenVMS;
        } else if (isOS2()) {
            INSTANCE.osname = OSName.OS2;
        } else if (isOS390()) {
            INSTANCE.osname = OSName.OS390;
        } else if (isOSF1()) {
            INSTANCE.osname = OSName.OSF1;
        } else if (isSolaris()) {
            INSTANCE.osname = OSName.Solaris;
        } else if (isSunOS()) {
            INSTANCE.osname = OSName.SunOS;
        } else if (isWindows()) {
            INSTANCE.osname = OSName.Windows;
        } else {
            INSTANCE.osname = OSName.Others;
        }
        return INSTANCE.osname.toString();
    }

    /**
     * @param args
     *
     */
    public static void main(final String[] args) {
        System.out.println(OSUtil.getOSname());
    }

    /**
     * <p>
     * 描述：操作系统描述
     * </p>
     *
     * @author Doug Wang
     */
    public enum OSName {

        /**
         * 系统名称
         */
        Any("any"),
        Linux("Linux"),
        Mac("Mac OS"),
        Windows("Windows"),
        OS2("OS/2"),
        Solaris("Solaris"),
        /**
         * 系统名称
         */
        SunOS("SunOS"),
        MPEiX("MPE/iX"),
        HP_UX("HP-UX"),
        AIX("AIX"),
        OS390("OS/390"),
        FreeBSD("FreeBSD"),
        /**
         * 系统名称
         */
        Irix("Irix"),
        Unix("Unix"),
        NetWare_411("NetWare"),
        OSF1("OSF1"),
        OpenVMS("OpenVMS"),
        Others("Others");

        private OSName(final String desc) {
            this.description = desc;
        }

        @Override
        public String toString() {
            return description;
        }

        private String description;
    }

}
