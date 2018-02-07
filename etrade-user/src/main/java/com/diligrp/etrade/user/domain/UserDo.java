package com.diligrp.etrade.user.domain;

import com.diligrp.etrade.shared.domain.BaseDo;
import com.diligrp.etrade.shared.type.Gender;
import com.diligrp.etrade.user.type.Position;
import com.diligrp.etrade.user.type.Role;
import com.diligrp.etrade.user.type.UserStatus;

import java.time.LocalDateTime;

/**
 * 电子结算用户数据模型-etrade_user
 *
 * @author: brenthuang
 * @date: 2018/01/17
 */
public class UserDo extends BaseDo {
    /**
     * 登陆账号
     */
    private String account;
    /**
     * 姓名
     */
    private String name;
    /**
     * 性别
     */
    private Gender gender;
    /**
     * 手机号
     */
    private String mobile;
    /**
     * 固定电话
     */
    private String telphone;
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * 登陆密码
     */
    private String password;
    /**
     * 是否强制修改登陆密码
     */
    private Boolean pwdChange;
    /**
     * 最大密码错误次数
     */
    private Integer pwdErrors;
    /**
     * 最近登陆时间
     */
    private LocalDateTime loginTime;
    /**
     * 用户角色-财务
     */
    private Role role;
    /**
     * 用户职位
     */
    private Position position;
    /**
     * 登陆TokenID
     */
    private String accessToken;
    /**
     * 状态
     */
    private UserStatus status;
    /**
     * 组织机构编码
     */
    private String instCode;
    /**
     * 机构名称
     */
    private String instName;
    /**
     * 备注
     */
    private String description;
    /**
     * 数据版本号
     */
    private Integer version;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getPwdChange() {
        return pwdChange;
    }

    public void setPwdChange(Boolean pwdChange) {
        this.pwdChange = pwdChange;
    }

    public Integer getPwdErrors() {
        return pwdErrors;
    }

    public void setPwdErrors(Integer pwdErrors) {
        this.pwdErrors = pwdErrors;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
