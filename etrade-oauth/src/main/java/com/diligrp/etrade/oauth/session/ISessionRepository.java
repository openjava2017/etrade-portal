package com.diligrp.etrade.oauth.session;

/**
 * Session存储仓库接口
 *
 * @author: brenthuang
 * @date: 2018/01/03
 */
public interface ISessionRepository {
    /**
     * 从Session仓库加载用户Session
     *
     * @param sessionId - Session唯一标识
     * @param maxInactiveInterval - 最大钝化间隔时间
     * @return 用户Session
     */
    SharedHttpSession loadSession(String sessionId, int maxInactiveInterval);

    /**
     * 存储用户Session到Session仓库
     *
     * @param session - 用户session
     * @param maxInactiveInterval - 最大钝化间隔时间
     */
    void saveSession(SharedHttpSession session, int maxInactiveInterval);

    /**
     * 从Session仓库移除用户Session
     *
     * @param session - 用户Session
     */
    void removeSession(SharedHttpSession session);

    /**
     * 判断Session是否已经存在
     *
     * @param sessionId - Session唯一标识
     * @return true-存在, false-不存在
     */
    boolean sessionExists(String sessionId);
}
