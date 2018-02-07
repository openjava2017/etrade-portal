package com.diligrp.etrade.shared.domain;

import java.time.LocalDateTime;

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
    private LocalDateTime createdTime;
    /**
     * 数据修改时间
     */
    private LocalDateTime modifiedTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
