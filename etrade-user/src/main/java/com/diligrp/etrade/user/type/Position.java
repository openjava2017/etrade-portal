package com.diligrp.etrade.user.type;

import com.diligrp.etrade.shared.type.IEnumType;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 用户职位
 *
 * @author: brenthuang
 * @date: 2017/12/28
 */
public enum Position implements IEnumType {

    ADMIN("总经理", 1),

    MANAGER("部门经理", 2);

    private static final Stream<Position> POSITIONS = Stream.of(Position.values());

    private String name;
    private int code;

    Position(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static Position getEmployeeType(int code) {
        Optional<Position> result = POSITIONS.filter(position -> position.getCode() == code).findFirst();
        return result.isPresent() ? result.get() : null;
    }

    public static String getName(int code) {
        Optional<Position> result = POSITIONS.filter(position -> position.getCode() == code).findFirst();
        return result.isPresent() ? result.get().name : null;
    }

    public static List<Position> getRoleList() {
        return Arrays.asList(Position.values());
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
