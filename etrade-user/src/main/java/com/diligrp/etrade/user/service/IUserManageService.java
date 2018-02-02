package com.diligrp.etrade.user.service;

import com.diligrp.etrade.user.domain.PagePermissionDo;
import com.diligrp.etrade.user.domain.PageResourceDo;

import java.util.Date;
import java.util.List;

/**
 * 系统用户管理服务
 *
 * @author: brenthuang
 * @date: 2018/01/22
 */
public interface IUserManageService {
    /**
     * 锁定系统用户
     *
     * @param account - 用户账号
     * @param when - 锁定时间
     * @return true-成功，false-失败
     */
    boolean lockUser(String account, Date when, Integer version);

    /**
     * 获取用户分配的页面资源
     *
     * @param userId - 用户ID
     * @return 页面资源列表
     */
    List<PageResourceDo> loadUserPageResources(Long userId);

    /**
     * 获取用户分配的页面权限
     *
     * @param userId - 用户ID
     * @return 页面权限
     */
    List<PagePermissionDo> loadUserPagePermissions(Long userId);
}
