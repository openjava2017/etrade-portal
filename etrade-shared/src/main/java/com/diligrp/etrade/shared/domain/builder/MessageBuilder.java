package com.diligrp.etrade.shared.domain.builder;

import com.diligrp.etrade.shared.domain.Message;

/**
 * Message构造器
 *
 * @author: brenthuang
 * @date: 2018/01/03
 */
public class MessageBuilder<E> {
    private static final int CODE_SUCCESS = 0;

    private static final int CODE_FAILURE = 1000;

    private static final String MSG_SUCCESS = "success";

    private static final String MSG_FAILURE = "failure";

    private int code;
    private String message;
    private E data;

    public MessageBuilder code(int code) {
        this.code = code;
        return this;
    }

    public MessageBuilder message(String message) {
        this.message = message;
        return this;
    }

    public MessageBuilder data(E data) {
        this.data = data;
        return this;
    }

    public MessageBuilder success() {
        this.code = 0;
        this.message = MSG_SUCCESS;
        return this;
    }

    public MessageBuilder success(E data) {
        this.code = 0;
        this.data = data;
        return this;
    }

    public MessageBuilder success(String message) {
        this.code = CODE_SUCCESS;
        this.message = message;
        return this;
    }

    public MessageBuilder failure(String message) {
        this.code = CODE_FAILURE;
        this.message = message;
        return this;
    }

    public MessageBuilder failure(Exception ex) {
        this.code = CODE_FAILURE;
        this.message = ex.getMessage();
        return this;
    }

    public Message build() {
        Message<E> msg = new Message<>();
        msg.setCode(code);
        msg.setMessage(message);
        msg.setData(data);

        return msg;
    }
}
