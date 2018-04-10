package com.diligrp.etrade.user.service.impl;

import com.diligrp.etrade.oauth.domain.Operator;
import com.diligrp.etrade.oauth.domain.builder.OperatorBuilder;
import com.diligrp.etrade.oauth.domain.builder.WebPageBuilder;
import com.diligrp.etrade.shared.redis.IRedisSystemService;
import com.diligrp.etrade.shared.security.PasswordUtils;
import com.diligrp.etrade.shared.util.DateUtils;
import com.diligrp.etrade.shared.util.ObjectUtils;
import com.diligrp.etrade.user.dao.IUserManageDao;
import com.diligrp.etrade.user.domain.PagePermissionDo;
import com.diligrp.etrade.user.domain.PageResourceDo;
import com.diligrp.etrade.user.domain.UserDo;
import com.diligrp.etrade.user.exception.UserAuthorizationException;
import com.diligrp.etrade.user.service.IUserAuthorizationService;
import com.diligrp.etrade.user.service.IUserManageService;
import com.diligrp.etrade.user.type.UserStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户登录授权服务
 *
 * @author: brenthuang
 * @date: 2018/01/22
 */
@Service("userAuthorizationService")
public class UserAuthorizationServiceImpl implements IUserAuthorizationService {

    private static Logger LOG = LoggerFactory.getLogger(UserAuthorizationServiceImpl.class);

    private static final String ETRADE_USER_PWDERRORS = "etrade:user:pwd_errors:";

    private static final int PWDERRORS_EXPIRE_TIME = 60 * 60 * 24 * 2;

    @Value("${user.password.secretKey}")
    private String secretKey;

    @Resource
    private IUserManageDao userManageDao;

    @Resource
    private IRedisSystemService redisSystemService;

    @Resource
    private IUserManageService userManageService;

    @Override
    public Operator authorizeUser(String account, String password) throws Exception {
        UserDo user = userManageDao.findUserByAccount(account);
        if (user == null) {
            throw new UserAuthorizationException(UserAuthorizationException.ErrorCode.USER_NOT_FOUND);
        }
        if (user.getStatus() == UserStatus.LOCKED) {
            throw new UserAuthorizationException(UserAuthorizationException.ErrorCode.USER_ALREADY_LOCKED);
        }

        // 用户输入密码通过AES->SHA算法得到密码密文
        String encryptPwd = PasswordUtils.encrypt(password, secretKey);
        String date = DateUtils.formatDate(LocalDate.now(), DateUtils.YYYY_MM_DD);
        // 用户当天密码错误次数Redis key
        String pwdErrorsKey = ETRADE_USER_PWDERRORS + account + "[" + date + "]";
        if (!ObjectUtils.equals(user.getPassword(), encryptPwd)) {
            // 获取用户密码输入错误次数
            Long errors = redisSystemService.incAndGet(pwdErrorsKey, PWDERRORS_EXPIRE_TIME);
            // 超过用户密码最大错误次数则锁定用户
            if (errors.intValue() > user.getPwdErrors().intValue()) {
                LOG.info("Exceed the user[{}] max password input error times", user.getAccount());
                boolean locked = userManageService.lockUser(user.getAccount(), LocalDateTime.now(), user.getVersion());
                // 锁定之后清除密码错误次数
                if (locked) {
                    redisSystemService.remove(pwdErrorsKey);
                } else {
                    LOG.error("Cannot lock the login user[{}], exceed max password input error times", user.getAccount());
                }
                throw new UserAuthorizationException(UserAuthorizationException.ErrorCode.USER_SOON_LOCKED);
            } else if (user.getPwdErrors().intValue() == errors.intValue()) {
                throw new UserAuthorizationException(UserAuthorizationException.ErrorCode.USER_PENDING_LOCKED);
            }
            throw new UserAuthorizationException(UserAuthorizationException.ErrorCode.INVALID_USER_PASSWORD);
        }
        redisSystemService.remove(pwdErrorsKey);

        // 构建操作用户
        OperatorBuilder operatorBuilder = new OperatorBuilder();
        Integer role = user.getRole() == null ? null : user.getRole().getCode();
        Integer position = user.getPosition() == null ? null : user.getPosition().getCode();
        operatorBuilder.id(user.getId()).account(user.getAccount()).name(user.getName()).role(role)
                .position(position).instCode(user.getInstCode()).instName(user.getInstName());

        // 加载用户分配的页面
        List<PageResourceDo> pages = userManageService.loadUserPageResources(user.getId());
        if (ObjectUtils.isNotEmpty(pages)) {
            // 加载用户分配的页面权限
            List<PagePermissionDo> permissions = userManageService.loadUserPagePermissions(user.getId());
            pages.forEach(page -> {
                // 页面权限 = 所有用户分配的页面权限掩码的OR运算结果
                Integer mask = permissions.stream()
                        .filter(permission -> ObjectUtils.equals(permission.getPageCode(), page.getCode()))
                        .mapToInt(permission -> permission.getMask()).reduce((maskA, maskB) -> maskA | maskB)
                        .orElse(com.diligrp.etrade.oauth.domain.Resource.ALL_PERMISSIONS);

                WebPageBuilder pageBuilder = new WebPageBuilder().code(page.getCode()).name(page.getName())
                        .parentCode(page.getParentCode()).permissions(mask).url(page.getUrl()).path(page.getPath());
                operatorBuilder.resource(pageBuilder.build());
            });
        }

        return operatorBuilder.build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerUserLogin(String account, String accessToken) {
        userManageDao.updateUserLogin(account, accessToken, LocalDateTime.now());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unregisterUserLogin(String account) {
        userManageDao.updateUserLogin(account, null, null);
    }
}
