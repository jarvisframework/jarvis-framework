package com.jarvis.framework.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Doug Wang
 * @version 1.0.0 2021年6月29日
 */
public abstract class HardwareAddressUtil {

    /**
     * 获取Mac地址
     *
     * @return Set
     * @throws IOException
     */
    public static Set<String> getAllHardwareAddress() throws IOException {
        final Set<String> macs = new HashSet<String>();
        final Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
        while (en.hasMoreElements()) {
            final NetworkInterface iface = en.nextElement();
            final List<InterfaceAddress> addrs = iface.getInterfaceAddresses();
            for (final InterfaceAddress addr : addrs) {
                final InetAddress ip = addr.getAddress();
                final NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                if (network == null) {
                    continue;
                }
                final byte[] mac = network.getHardwareAddress();
                if (mac == null) {
                    continue;
                }
                macs.add(toHex(mac));
            }
        }
        return macs;
    }

    private static String toHex(byte[] mac) {
        final StringBuilder sb = new StringBuilder();
        final String splice = OSUtil.isWindows() ? "-" : ":";
        for (int i = 0; i < mac.length; i++) {
            if (i > 0) {
                sb.append(splice);
            }
            sb.append(String.format("%02X", mac[i]));
        }
        return sb.toString();
    }
}
