package com.diligrp.etrade.user.domain;

import com.diligrp.etrade.shared.domain.BaseDo;

/**
 * 用户权限数据模型-etrade_role
 *
 * @author: brenthuang
 * @date: 2018/01/17
 */
public class RoleDo extends BaseDo {
    /**
     * 角色名称
     */
    private String name;
    /**
     * 备注
     */
    private String description;
    /**
     * 组织机构编码
     */
    private String instCode;
    /**
     * 机构名称
     */
    private String instName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
