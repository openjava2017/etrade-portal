package com.diligrp.etrade.user.type;

import com.diligrp.etrade.shared.type.IEnumType;

/**
 * 用户状态
 *
 * @author: brenthuang
 * @date: 2017/12/28
 */
public enum UserStatus implements IEnumType {

    NORMAL("正常", 1),

    LOCKED("锁定", 2),

    VOID("删除", 10);

    private String name;
    private int code;

    UserStatus(String name, int code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return getName();
    }
}
