package com.fz.zf.config.app;

/**
 * 自定义登录异常
 */
public class AuthException extends RuntimeException{
    public AuthException (String msg){
        super(msg);
    }
}
