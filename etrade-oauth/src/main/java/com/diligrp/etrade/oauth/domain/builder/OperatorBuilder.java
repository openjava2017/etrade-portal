package com.diligrp.etrade.oauth.domain.builder;

import com.diligrp.etrade.oauth.domain.Operator;
import com.diligrp.etrade.oauth.domain.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作用户模型Builder
 *
 * @author: brenthuang
 * @date: 2018/01/24
 */
public class OperatorBuilder {
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
    private List<Resource> resources;
    /**
     * 机构编码
     */
    private String instCode;
    /**
     * 机构名称
     */
    private String instName;

    public OperatorBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public OperatorBuilder account(String account) {
        this.account = account;
        return this;
    }

    public OperatorBuilder name(String name) {
        this.name = name;
        return this;
    }

    public OperatorBuilder role(Integer role) {
        this.role = role;
        return this;
    }

    public OperatorBuilder position(Integer position) {
        this.position = position;
        return this;
    }

    public OperatorBuilder accessToken(String accessToken) {
        this.accessToken = accessToken;
        return this;
    }

    public OperatorBuilder resources(List<Resource> resources) {
        this.resources = resources;
        return this;
    }

    public OperatorBuilder resource(Resource resource) {
        if (this.resources == null) {
            this.resources = new ArrayList<>();
        }
        this.resources.add(resource);

        return this;
    }

    public OperatorBuilder instCode(String instCode) {
        this.instCode = instCode;
        return this;
    }

    public OperatorBuilder instName(String instName) {
        this.instName = instName;
        return this;
    }

    public Operator build() {
        Operator operator = new Operator();
        operator.setId(id);
        operator.setAccount(account);
        operator.setName(name);
        operator.setRole(role);
        operator.setPosition(position);
        operator.setAccessToken(accessToken);
        operator.setResources(resources);
        operator.setInstCode(instCode);
        operator.setInstName(instName);
        return operator;
    }
}
