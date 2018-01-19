package com.diligrp.etrade.shared.domain;

/**
 * 园区机构数据模型
 *
 * @author: brenthuang
 * @date: 2018/01/15
 */
public class Institution {
    private Long id;
    /**
     * 园区编码
     */
    private String code;
    /**
     * 园区名称
     */
    private String name;
    /**
     * 园区内部账户
     */
    private Long accountId;
    /**
     * 支付系统商户ID
     */
    private Long merchantId;
    /**
     * 现金柜-财务专用
     */
    private Long cashBox;
    /**
     * 数据版本号
     */
    private Integer version;
    /**
     * 描述
     */
    private String description;

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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public Long getCashBox() {
        return cashBox;
    }

    public void setCashBox(Long cashBox) {
        this.cashBox = cashBox;
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
