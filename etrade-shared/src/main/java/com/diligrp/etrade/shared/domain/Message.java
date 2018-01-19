package com.diligrp.etrade.shared.domain;

/**
 * 消息JavaBean类
 *
 * @author: brenthuang
 * @date: 2018/01/03
 */
public class Message<E> {

    private Integer code;
    private String message;
    private E data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }
}