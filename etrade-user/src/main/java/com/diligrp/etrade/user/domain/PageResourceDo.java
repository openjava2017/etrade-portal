package com.diligrp.etrade.user.domain;

import com.diligrp.etrade.shared.domain.BaseDo;

import java.util.Date;

/**
 * 页面资源数据模型-etrade_page_resource
 *
 * @author: brenthuang
 * @date: 2018/01/17
 */
public class PageResourceDo extends BaseDo {
    /**
     * 页面资源主键
     */
    private Long id;
    /**
     * 页面资源编码
     */
    private String code;
    /**
     * 页面资源名称
     */
    private String name;
    /**
     * 页面资源请求路径
     */
    private String url;
    /**
     * 页面资源路径
     */
    private String path;
    /**
     * 页面层级
     */
    private Integer level;
    /**
     * 上级资源编码
     */
    private String parentCode;
    /**
     * 页面顺序
     */
    private Integer sequence;
    /**
     * 备注
     */
    private String description;
    /**
     * 创建时间
     */
    private Date createdTime;

    @Override
    public Long getId() {
        return id;
    }

    @Override
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public Date getCreatedTime() {
        return createdTime;
    }

    @Override
    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}
