package com.diligrp.etrade.shared.domain;

import java.util.Date;

/**
 * 数据模型基础类
 *
 * @author: brenthuang
 * @date: 2018/01/12
 */
public class BaseDo {
    /**
     * 数据库主键ID
     */
    private Long id;
    /**
     * 数据创建时间
     */
    private Date createdTime;
    /**
     * 数据修改时间
     */
    private Date modifiedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
