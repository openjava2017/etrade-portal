package com.diligrp.etrade.shared.type;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author: brenthuang
 * @date: 2017/12/28
 */
public enum Gender implements IEnumType {

    //男
    MALE("男", 1),

    //女
    FEMALE("女", 2);

    /**
     * 缓存Gender流
     */
    private static final Stream<Gender> GENDERS = Stream.of(Gender.values());

    private String name;
    private int code;

    Gender(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public static Gender getGender(int code) {
        Optional<Gender> result = GENDERS.filter(gender -> gender.getCode() == code).findFirst();
        return result.isPresent() ? result.get() : null;
    }

    public static String getName(int code) {
        Optional<Gender> result = GENDERS.filter(gender -> gender.getCode() == code).findFirst();
        return result.isPresent() ? result.get().name : null;
    }

    public static List<Gender> getGenderList() {
        return Arrays.asList(Gender.values());
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
