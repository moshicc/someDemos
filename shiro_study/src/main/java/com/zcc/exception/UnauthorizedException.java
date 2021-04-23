package com.zcc.exception;

/**
 * @author zcc
 * @ClassName UnauthorizedException
 * @description
 * @date 2021/4/23 17:34
 * @Version 1.0
 */

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String msg){
        super(msg);
    }

    public UnauthorizedException(){
        super();
    }
}
