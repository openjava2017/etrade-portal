package com.diligrp.etrade.oauth.domain;

/**
 * Web页面模型-系统权限使用
 *
 * @author: brenthuang
 * @date: 2018/01/22
 */
public class WebPage extends Resource {
    /**
     * 页面请求URL
     */
    private String url;
    /**
     * 页面资源路径
     */
    private String path;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
