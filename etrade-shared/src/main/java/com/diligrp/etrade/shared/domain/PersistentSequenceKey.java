package com.diligrp.etrade.shared.domain;

/**
 * KEY-ID生成器数据库模型
 *
 * @author: brenthuang
 * @date: 2018/01/02
 */
public class PersistentSequenceKey {
    private Long id;
    private String key;
    private Long startWith;
    private Long incSpan;
    private String scope;
    private Integer version;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getStartWith() {
        return startWith;
    }

    public void setStartWith(Long startWith) {
        this.startWith = startWith;
    }

    public Long getIncSpan() {
        return incSpan;
    }

    public void setIncSpan(Long incSpan) {
        this.incSpan = incSpan;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
