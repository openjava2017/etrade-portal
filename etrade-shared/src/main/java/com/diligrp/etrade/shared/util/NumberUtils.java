package com.diligrp.etrade.shared.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 字符串-数值转化工具类
 *
 * @author: brenthuang
 * @date: 2017/12/28
 */
public class NumberUtils {
    private static Logger LOG = LoggerFactory.getLogger(NumberUtils.class);

    public static int str2Int(String number, int defaultValue) {
        if (ObjectUtils.isEmpty(number)) {
            return defaultValue;
        }

        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException nfe) {
            // Never ignore any exception
            LOG.error("Invalid number format", nfe);
            return defaultValue;
        }
    }
}
