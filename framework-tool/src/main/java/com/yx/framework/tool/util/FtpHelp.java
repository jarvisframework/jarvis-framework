package com.yx.framework.tool.util;


import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketException;


/**
 * <p>ftp工具类</p>
 *
 * @author zdc
 * @date 2022-03-23 02:01:01
 */
public class FtpHelp {

    private static final Logger LOGGER = LoggerFactory.getLogger(FtpHelp.class);

    public static FTPClient connectFTP(String hostname, int port, String username, String password) {
        FTPClient ftp = new FTPClient();
        try {
            //连接FTP服务器
            ftp.connect(hostname, port);
            boolean login = ftp.login(username, password);
            if (login) {
                System.out.println(">>>>>>>>FTP-->登录成功>>>>>>>>>>>>>");
            } else {
                System.out.println(">>>>>>>>FTP-->登录失败>>>>>>>>>>>>>");
            }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ftp;
    }
}
