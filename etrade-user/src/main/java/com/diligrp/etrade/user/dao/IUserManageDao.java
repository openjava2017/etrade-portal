package com.diligrp.etrade.user.dao;

import com.diligrp.etrade.shared.mybatis.MybatisMapperSupport;
import com.diligrp.etrade.user.domain.UserDo;
import com.diligrp.etrade.user.type.UserStatus;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * 系统用户数据库访问Dao
 *
 * @author: brenthuang
 * @date: 2018/01/22
 */
@Repository("userManageDao")
public interface IUserManageDao extends MybatisMapperSupport {

    /**
     * 根据登录账号查询用户信息
     *
     * @param account - 登录账号
     * @return 用户信息
     */
    UserDo findUserByAccount(String account);

    /**
     * 根据登录账号修改用户状态，只有当原状态没有被并发修改时才能真正修改成功
     *
     * @param account - 登录账号
     * @param status - 修改状态
     * @param when - 修改时间
     * @param version - 数据原来版本-可以为空
     * @return 0-失败 1-成功
     */
    int updateUserStatus(@Param("account") String account, @Param("status") UserStatus status,
                         @Param("when") LocalDateTime when, @Param("version") Integer version);

    /**
     * 更新用户登录信息
     *
     * @param account - 用户账号
     * @param accessToken - accessToken
     */
    void updateUserLogin(@Param("account") String account, @Param("accessToken") String accessToken,
                         @Param("when") LocalDateTime when);
}
