package com.diligrp.etrade.oauth.util;

/**
 * 字符串工具类
 *
 * @author: brenthuang
 * @date: 2018/01/19
 */
public class StringUtils {
    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
