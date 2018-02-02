package com.diligrp.etrade.user.service.impl;

import com.diligrp.etrade.user.dao.IUserManageDao;
import com.diligrp.etrade.user.dao.IUserRoleDao;
import com.diligrp.etrade.user.domain.PagePermissionDo;
import com.diligrp.etrade.user.domain.PageResourceDo;
import com.diligrp.etrade.user.service.IUserManageService;
import com.diligrp.etrade.user.type.UserStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 系统用户管理服务
 *
 * @author: brenthuang
 * @date: 2018/01/22
 */
@Service("userManageService")
public class UserManageServiceImpl implements IUserManageService {

    @Resource
    private IUserManageDao userManageDao;

    @Resource
    private IUserRoleDao userRoleDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean lockUser(String account, Date when, Integer version) {
        return userManageDao.updateUserStatus(account, UserStatus.LOCKED, when, version) > 0;
    }

    @Override
    public List<PageResourceDo> loadUserPageResources(Long userId) {
        return userRoleDao.loadUserPageResources(userId);
    }

    @Override
    public List<PagePermissionDo> loadUserPagePermissions(Long userId) {
        return userRoleDao.loadUserPagePermissions(userId);
    }
}
