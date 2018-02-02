package com.diligrp.etrade.user.exception;

/**
 * 用户登录授权异常类
 *
 * @author: brenthuang
 * @date: 2018/01/22
 */
public class UserAuthorizationException extends RuntimeException {
    private int code;

    private boolean stackTrace = true;

    public UserAuthorizationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.stackTrace = false;
    }

    public UserAuthorizationException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.code = errorCode.getCode();
    }

    @Override
    public Throwable fillInStackTrace() {
        return stackTrace ? super.fillInStackTrace() : this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public enum ErrorCode {
        // invalid_user_password
        INVALID_USER_PASSWORD(1010, "用户名密码不正确"),
        // user_not_found
        USER_NOT_FOUND(1020, "用户不存在"),
        // user_already_locked
        USER_ALREADY_LOCKED(1030, "用户已被锁定"),
        // user_soon_locked
        USER_SOON_LOCKED(1040, "用户名密码错误，用户已被锁定"),
        // user_pending_locked
        USER_PENDING_LOCKED(1050, "用户名密码错误，再输入错误一次将锁定用户"),
        // login_not_allowed
        LOGIN_NOT_ALLOWED(1060, "该类用户不允许登陆系统"),
        // user_already_login
        USER_ALREADY_LOGIN(1070, "该用户已经在线，不允许重复登陆系统");

        private int code;

        private String message;

        ErrorCode(int code, String message) {
            this.code = code;
            this.message = message;
        }

        public int getCode()
        {
            return code;
        }

        public String getMessage()
        {
            return message;
        }
    }
}
