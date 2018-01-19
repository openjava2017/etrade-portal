package com.diligrp.etrade.shared.type;

/**
 * 系统错误码枚举类
 *
 * @author: brenthuang
 * @date: 2018/01/03
 */
public enum ErrorCode implements IEnumType {

    //错误码列表
    UNKNOWN_EXCEPTION(1000, "系统未知异常"),

    SERVICE_ACCESS_DENIED(1001, "服务访问拒绝"),

    SERVICE_UNAVAILABLE(1002, "服务不可用"),

    DATA_VERIFY_FAILED(1003, "数据验签失败"),

    DATA_SIGN_FAILED(1004, "数据签名失败"),

    ILLEGAL_ARGUMENT(1010, "无效系统参数");

    private int code;
    private String name;

    ErrorCode(int code, String name) {
        this.code = code;
        this.name = name;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
