package com.diligrp.etrade.oauth.session;

/**
 * 自定义SessionContext实现
 *
 * @author: brenthuang
 * @date: 2018/01/03
 */
public class HttpSessionContext {
    private SharedHttpSession session;
    private ISessionContextListener contextListener;

    public void attachSession(SharedHttpSession session, ISessionContextListener contextListener) {
        this.session = session;
        this.contextListener = contextListener;
        this.session.doInit(this);
    }

    public SharedHttpSession getSession() {
        return session;
    }

    public void destroy() {
        if (contextListener != null) {
            contextListener.onDestroy();
        }
    }

    public void onSessionCreated() {
        if (contextListener != null) {
            contextListener.sessionCreated();
        }
    }

    public void onSessionInvalidated() {
        if (contextListener != null) {
            contextListener.sessionInvalidated();
        }
    }

    public interface ISessionContextListener {
        /**
         * Session创建时触发
         */
        void sessionCreated();

        /**
         * Session钝化时触发
         */
        void sessionInvalidated();

        /**
         * Session销毁时触发
         */
        void onDestroy();
    }
}
