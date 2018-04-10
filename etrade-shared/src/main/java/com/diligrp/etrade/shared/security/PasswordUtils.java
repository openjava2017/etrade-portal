package com.diligrp.etrade.shared.security;

import com.diligrp.etrade.shared.Constants;

/**
 * 密码加密散列工具类
 *
 * @author: brenthuang
 * @date: 2017/12/28
 */
public class PasswordUtils {
    public static String encrypt(String password, String secretKey) throws Exception {
        byte[] data = password.getBytes(Constants.CHARSET_UTF8);
        return HexUtils.encodeHexStr(ShaCipher.encrypt(AesCipher.encrypt(data, secretKey)));
    }
}
