package com.diligrp.etrade.user.sapi.domain;

/**
 * 消息信封JavaBean将安全数据与业务数据进行分离
 * 用于接口数据安全校验：数据签名验签，防止数据在传输过程中被篡改
 *
 * @author: brenthuang
 * @date: 2018/01/08
 */
public class MessageEnvelop {
    /**
     * 调用者APP标识
     */
    private Long appId;

    /**
     * 调用服务描述，格式componentId:methodName
     */
    private String service;

    /**
     * 服务访问Token
     */
    private String accessToken;

    /**
     * 签名数据-十六进制
     */
    private String signature;

    /**
     * 业务数据-十六进制（charset编码格式的字符流）
     */
    private String body;

    /**
     * 业务数据字符流的编码格式
     */
    private String charset;

    /**
     * 数据信封状态
     */
    private Integer status = 0;

    public Long getAppId() {
        return appId;
    }

    public void setAppId(Long appId) {
        this.appId = appId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
