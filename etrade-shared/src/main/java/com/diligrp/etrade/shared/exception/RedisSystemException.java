package com.diligrp.etrade.shared.exception;

/**
 * Redis系统异常类
 *
 * @author: brenthuang
 * @date: 2017/12/29
 */
public class RedisSystemException extends Exception {
    public RedisSystemException(String msg) {
        super(msg);
    }

    public RedisSystemException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
