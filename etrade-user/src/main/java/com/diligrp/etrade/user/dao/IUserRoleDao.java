package com.diligrp.etrade.user.dao;

import com.diligrp.etrade.shared.mybatis.MybatisMapperSupport;
import com.diligrp.etrade.user.domain.PagePermissionDo;
import com.diligrp.etrade.user.domain.PageResourceDo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户角色数据库访问Dao
 *
 * @author: brenthuang
 * @date: 2018/01/23
 */
@Repository("userRoleDao")
public interface IUserRoleDao extends MybatisMapperSupport {
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
