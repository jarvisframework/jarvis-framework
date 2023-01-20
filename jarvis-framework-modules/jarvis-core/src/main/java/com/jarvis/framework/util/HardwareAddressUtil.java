package com.jarvis.framework.util;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class HardwareAddressUtil {

    public static Set<String> getAllHardwareAddress() throws IOException {
        Set<String> macs = new HashSet();
        Enumeration en = NetworkInterface.getNetworkInterfaces();

        while(en.hasMoreElements()) {
            NetworkInterface iface = (NetworkInterface)en.nextElement();
            List<InterfaceAddress> addrs = iface.getInterfaceAddresses();
            Iterator var4 = addrs.iterator();

            while(var4.hasNext()) {
                InterfaceAddress addr = (InterfaceAddress)var4.next();
                InetAddress ip = addr.getAddress();
                NetworkInterface network = NetworkInterface.getByInetAddress(ip);
                if (network != null) {
                    byte[] mac = network.getHardwareAddress();
                    if (mac != null) {
                        macs.add(toHex(mac));
                    }
                }
            }
        }

        return macs;
    }

    private static String toHex(byte[] mac) {
        StringBuilder sb = new StringBuilder();
        String splice = OSUtil.isWindows() ? "-" : ":";

        for(int i = 0; i < mac.length; ++i) {
            if (i > 0) {
                sb.append(splice);
            }

            sb.append(String.format("%02X", mac[i]));
        }

        return sb.toString();
    }
}
