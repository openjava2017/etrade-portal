package com.diligrp.etrade.user.service;

import com.diligrp.etrade.oauth.domain.Operator;

/**
 * 用户登录授权服务
 *
 * @author: brenthuang
 * @date: 2018/01/22
 */
public interface IUserAuthorizationService {
    /**
     * 验证用户名密码
     *
     * @param account - 登录账号
     * @param password - 登录密码
     * @return 登录用户信息
     */
    Operator authorizeUser(String account, String password) throws Exception;

    /**
     * 注册用户登录信息-将accessToken[jsessionId]和登录时间更新到数据库中
     *
     * @param account - 用户账号
     * @param accessToken - 登录后的accessToken或者jsessionId
     */
    void registerUserLogin(String account, String accessToken);

    /**
     * 注销用户登录信息
     *
     * @param account - 用户账号
     */
    void unregisterUserLogin(String account);
}
