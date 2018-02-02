package com.diligrp.etrade.user.type;

import com.diligrp.etrade.shared.type.IEnumType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 用户角色
 *
 * @author: brenthuang
 * @date: 2017/12/28
 */
public enum Role implements IEnumType {

    ROOT("超级管理员", 0),

    ADMIN("市场管理员", 1),

    FINANCE("财务", 2),

    CASHIER("总收银", 3),

    COUNTER("柜员", 4),

    EMPLOYEE("普通职员", 0);

    private static final Stream<Role> ROLES = Stream.of(Role.values());

    private String name;
    private int code;

    Role(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static Role getRoleType(int code) {
        Optional<Role> result = ROLES.filter(role -> role.getCode() == code).findFirst();
        return result.isPresent() ? result.get() : null;
    }

    public static String getName(int code) {
        Optional<Role> result = ROLES.filter(role -> role.getCode() == code).findFirst();
        return result.isPresent() ? result.get().name : null;
    }

    public static List<Role> getRoleList() {
        return Arrays.asList(Role.values());
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
        return name;
    }
}
