package com.yx.framework.tool.util;


import com.yx.framework.tool.constant.JwtConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author Administrator
 */
public class JwtUtils {

    private static final String SECRET = "soft56000000000001";

    /**
     * 获取系统token
     *
     * @param body
     * @return
     */
    public static String getToken(String body) {
        Date nowDate = new Date();
        //过期时间
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject("YxLco")
                .claim(JwtConstants.LOGIN_BODY, body)
                .setIssuedAt(nowDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    /**
     * 获取 Token 中注册信息
     *
     * @param token
     * @return
     */
    public static Claims getTokenClaim(String token) {
        try {
            return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
