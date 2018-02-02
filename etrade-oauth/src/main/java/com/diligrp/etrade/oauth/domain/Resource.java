package com.diligrp.etrade.oauth.domain;

/**
 * 权限资源模型
 *
 * @author: brenthuang
 * @date: 2018/01/22
 */
public class Resource {
    /**
     * 所有页面权限，所有二进制位都为1，表示拥有所有权限
     */
    public static final Integer ALL_PERMISSIONS = 0xFFFF;
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
     * 资源访问权限: 数值范围 1 <= 2^n <= 65535 （N最小值0，最大值为15，因此可用2个字节-16位来表示1-32768的数）
     *
     * 资源权限的二进制位表示法(数据库中使用SMALLINT UNSIGNED, 有效位数为16位，因此每个页面最多支持16种页面元素访问权限)
     * 比如：permissions=1100101101010111，1表示某个页面元素有访问权限，0表示没有权限
     */
    private Integer permissions;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getPermissions() {
        return permissions;
    }

    public void setPermissions(Integer permissions) {
        this.permissions = permissions;
    }
}
