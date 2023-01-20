package com.yx.framework.tool.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CryptoUtilsTest {

    @Test
    public void md5Test() {
        String password = "abc123456";

        String md5$24 = CryptoUtils.md5Base64(password);
        System.out.println(md5$24);
        String md5$32 = CryptoUtils.md5Hex(password);
        System.out.println(md5$32);
    }

}