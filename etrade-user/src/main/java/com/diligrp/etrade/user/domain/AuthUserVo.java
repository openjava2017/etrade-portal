package com.diligrp.etrade.user.domain;

/**
 * 用户授权请求模型
 *
 * @author: brenthuang
 * @date: 2018/01/22
 */
public class AuthUserVo {
    /**
     * 登录账号
     */
    private String account;
    /**
     * 登录密码
     */
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
