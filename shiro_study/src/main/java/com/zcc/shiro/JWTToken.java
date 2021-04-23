package com.zcc.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author zcc
 * @ClassName JWTToken
 * @description
 * @date 2021/4/23 19:06
 * @Version 1.0
 */

public class JWTToken implements AuthenticationToken {

    //密钥
    private String token;

    public JWTToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
