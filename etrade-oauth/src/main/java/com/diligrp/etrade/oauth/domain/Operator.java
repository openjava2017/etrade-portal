package com.diligrp.etrade.oauth.domain;

import java.util.List;

/**
 * 系统登录用户模型
 *
 * @author: brenthuang
 * @date: 2018/01/22
 */
public class Operator {
    /**
     * 登录用户ID
     */
    private Long id;
    /**
     * 登录账号
     */
    private String account;
    /**
     * 登录名称
     */
    private String name;
    /**
     * 用户角色-财务 总收银 柜员等
     */
    private Integer role;
    /**
     * 用户职位-部门经理等
     */
    private Integer position;
    /**
     * 登录用户accessToken
     */
    private String accessToken;
    /**
     * 资源权限
     */
    private List<WebPage> resources;
    /**
     * 机构编码
     */
    private String instCode;
    /**
     * 机构名称
     */
    private String instName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public List<WebPage> getResources() {
        return resources;
    }

    public void setResources(List<WebPage> resources) {
        this.resources = resources;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }
}
