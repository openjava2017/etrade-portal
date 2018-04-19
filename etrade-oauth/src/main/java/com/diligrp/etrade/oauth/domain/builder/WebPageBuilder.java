package com.diligrp.etrade.oauth.domain.builder;

import com.diligrp.etrade.oauth.domain.WebPage;

/**
 * Web页面builder
 *
 * @author: brenthuang
 * @date: 2018/01/23
 */
public class WebPageBuilder {
    /**
     * 资源编码
     */
    private String code;
    /**
     * 资源名称
     */
    private String name;
    /**
     * 父资源编码
     */
    private String parentCode;
    /**
     * 页面请求URL
     */
    private String route;
    /**
     * 页面资源路径
     */
    private String path;
    /**
     * 页面访问权限
     */
    private Integer permissions;

    public WebPageBuilder code(String code) {
        this.code = code;
        return this;
    }

    public WebPageBuilder name(String name) {
        this.name = name;
        return this;
    }

    public WebPageBuilder parentCode(String parentCode) {
        this.parentCode = parentCode;
        return this;
    }

    public WebPageBuilder route(String route) {
        this.route = route;
        return this;
    }

    public WebPageBuilder path(String path) {
        this.path = path;
        return this;
    }

    public WebPageBuilder permissions(Integer permissions) {
        this.permissions = permissions;
        return this;
    }

    public WebPage build() {
        WebPage page = new WebPage();
        page.setCode(code);
        page.setName(name);
        page.setParentCode(parentCode);
        page.setRoute(route);
        page.setPath(path);
        page.setPermissions(permissions);
        return page;
    }
}
