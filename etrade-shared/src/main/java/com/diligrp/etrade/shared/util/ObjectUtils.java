package com.diligrp.etrade.shared.util;

import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用工具类
 *
 * @author: brenthuang
 * @date: 2017/12/28
 */
public class ObjectUtils {
    public static boolean equals(String str1, String str2) {
        return StringUtils.equals(str1, str2);
    }

    public static String[] split(String str, char separator) {
        if (str == null) {
            return new String[0];
        }

        return StringUtils.split(str, separator);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static <T> boolean isEmpty(List<T> array) {
        return array == null || array.isEmpty();
    }

    public static <T> boolean isNotEmpty(List<T> array) {
        return array != null && !array.isEmpty();
    }

    public static String trimToEmpty(String str) {
        return StringUtils.trimToEmpty(str);
    }

    public static List getObjList(Enum obj) {
        if (obj == null) {
            return new ArrayList();
        }
        return EnumUtils.getEnumList(obj.getClass());
    }

    public static boolean isNull(Object obj) {
        return null == obj;
    }
}
