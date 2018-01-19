package com.diligrp.etrade.user.domain;

import com.diligrp.etrade.shared.domain.BaseDo;

/**
 * 组织机构数据模型-etrade_institution
 *
 * @author: brenthuang
 * @date: 2018/01/17
 */
public class InstitutionDo extends BaseDo {
    /**
     * 机构编码
     */
    private String code;
    /**
     * 机构名称
     */
    private String name;
    /**
     * 级别
     */
    private Integer level;
    /**
     * 上级编码
     */
    private String parentCode;
    /**
     * 创建时间
     */
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
