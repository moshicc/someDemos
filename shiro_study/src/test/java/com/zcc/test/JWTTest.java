package com.zcc.test;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @author zcc
 * @ClassName JWTTest
 * @description
 * @date 2021/4/29 20:02
 * @Version 1.0
 */

public class JWTTest {

    /* *
     * @description: 用jwt 生成token
     * @param
     * @return
     * @throws
     * @author zcc13
     * @date 2021/4/29 20:11
     */
    @Test
    public void jwtCreateTest(){
        Date date = new Date(System.currentTimeMillis() + 1 * 60 * 1000);
        try {
            Algorithm algorithm = Algorithm.HMAC256("mima");
            String jwt = "";
            jwt = JWT.create()
                    .withClaim("username","zcc")
                    .withExpiresAt(date)
                    .sign(algorithm);

            System.out.println("jwt:"+jwt);

            System.out.println();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /* *
     * @description: 解析token里面的信息
     * @param
     * @return
     * @throws
     * @author zcc13
     * @date 2021/4/29 20:11
     */
    @Test
    public void jwtClaimTest() throws UnsupportedEncodingException {
        String token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MTk2OTg1MDgsInVzZXJuYW1lIjoiemNjIn0.WkmvpC8KgK9gsTP0n3kpUXZ-QfbzawDWIacyC-KvPcA";
        DecodedJWT jwt = JWT.decode(token);
        String username = jwt.getClaim("username").asString();
        String sign = jwt.getSignature().toString();
//        Algorithm algorithm = Algorithm.HMAC256("mima");
        System.out.println("解析token中的username："+username);
        System.out.println("解析token中的sign："+sign );

    }
}
