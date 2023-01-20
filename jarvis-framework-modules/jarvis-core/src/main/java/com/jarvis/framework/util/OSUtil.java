package com.jarvis.framework.util;

public final class OSUtil {

    private static String OS = System.getProperty("os.name").toLowerCase();

    private static OSUtil INSTANCE = new OSUtil();

    private OSUtil.OSName osname;

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

    public static String getOSname() {
        if (isAix()) {
            INSTANCE.osname = OSUtil.OSName.AIX;
        } else if (isUnix()) {
            INSTANCE.osname = OSUtil.OSName.Unix;
        } else if (isFreeBSD()) {
            INSTANCE.osname = OSUtil.OSName.FreeBSD;
        } else if (isHPUX()) {
            INSTANCE.osname = OSUtil.OSName.HP_UX;
        } else if (isIrix()) {
            INSTANCE.osname = OSUtil.OSName.Irix;
        } else if (isLinux()) {
            INSTANCE.osname = OSUtil.OSName.Linux;
        } else if (isMacOS()) {
            INSTANCE.osname = OSUtil.OSName.Mac;
        } else if (isMPEiX()) {
            INSTANCE.osname = OSUtil.OSName.MPEiX;
        } else if (isNetWare()) {
            INSTANCE.osname = OSUtil.OSName.NetWare_411;
        } else if (isOpenVMS()) {
            INSTANCE.osname = OSUtil.OSName.OpenVMS;
        } else if (isOS2()) {
            INSTANCE.osname = OSUtil.OSName.OS2;
        } else if (isOS390()) {
            INSTANCE.osname = OSUtil.OSName.OS390;
        } else if (isOSF1()) {
            INSTANCE.osname = OSUtil.OSName.OSF1;
        } else if (isSolaris()) {
            INSTANCE.osname = OSUtil.OSName.Solaris;
        } else if (isSunOS()) {
            INSTANCE.osname = OSUtil.OSName.SunOS;
        } else if (isWindows()) {
            INSTANCE.osname = OSUtil.OSName.Windows;
        } else {
            INSTANCE.osname = OSUtil.OSName.Others;
        }

        return INSTANCE.osname.toString();
    }

    public static void main(String[] args) {
        System.out.println(getOSname());
    }

    public static enum OSName {
        Any("any"),
        Linux("Linux"),
        Mac("Mac OS"),
        Windows("Windows"),
        OS2("OS/2"),
        Solaris("Solaris"),
        SunOS("SunOS"),
        MPEiX("MPE/iX"),
        HP_UX("HP-UX"),
        AIX("AIX"),
        OS390("OS/390"),
        FreeBSD("FreeBSD"),
        Irix("Irix"),
        Unix("Unix"),
        NetWare_411("NetWare"),
        OSF1("OSF1"),
        OpenVMS("OpenVMS"),
        Others("Others");

        private String description;

        OSName(String desc) {
            this.description = desc;
        }

        public String toString() {
            return this.description;
        }
    }
}
