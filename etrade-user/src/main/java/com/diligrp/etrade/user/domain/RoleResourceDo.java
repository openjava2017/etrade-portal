package com.diligrp.etrade.user.domain;

import com.diligrp.etrade.shared.domain.BaseDo;

/**
 * 角色-资源数据模型-etrade_role_resource
 *
 * @author: brenthuang
 * @date: 2018/01/17
 */
public class RoleResourceDo extends BaseDo {
    /**
     * 角色ID
     */
    private Long roleId;
    /**
     * 资源编码
     */
    private String code;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
