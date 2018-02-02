package com.diligrp.etrade.user.domain;

import java.util.Date;

/**
 * 页面权限数据模型-etrade_page_permission
 *
 * 一个WEB页面可以允许有多个页面权限，一个页面权限代表Web页面一个区域，甚至一个按钮。
 * 整个WEB页面最多可以允许16个页面权限，由一组16位的二进制数表示1011011111011011
 * 1表示具有相关权限，0表示不具备相关权限，至于哪一位表示具体什么权限由资源掩码Mask决定
 * 比如：用户管理页面中，页面权限一表示新增用户权限，由第三位二进制数决定是否具备此权限
 * 那么此权限掩码值为0000000000000100，页面权限二为除了新增用户权限之外的所有操作权限
 * 那么页面权限二的掩码值为1111111111111011
 *
 * 同一页面所有的页面权限掩码进行OR操作，将得到1111111111111111
 * 用户具有的某个页面的页面权限将由一组16位的二进制数表示011011111011011
 * 由所有用户所分配的页面权限的资源掩码进行OR运算得到
 *
 * @author: brenthuang
 * @date: 2018/01/23
 */
public class PagePermissionDo {
    /**
     * 页面权限主键
     */
    private Long id;
    /**
     * 权限编码
     */
    private String code;
    /**
     * 权限名称
     */
    private String name;
    /**
     * 所属页面编码
     */
    private String pageCode;
    /**
     * 权限掩码, 取值范围1 <= 2^n <= 65535
     */
    private Integer mask;
    /**
     * 备注
     */
    private String description;
    /**
     * 创建时间
     */
    private Date createdTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getPageCode() {
        return pageCode;
    }

    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
    }

    public Integer getMask() {
        return mask;
    }

    public void setMask(Integer mask) {
        this.mask = mask;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
