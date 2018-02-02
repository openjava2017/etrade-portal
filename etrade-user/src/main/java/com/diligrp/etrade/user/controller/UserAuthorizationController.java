package com.diligrp.etrade.user.controller;

import com.diligrp.etrade.oauth.domain.Operator;
import com.diligrp.etrade.oauth.session.DistributedSessionManager;
import com.diligrp.etrade.oauth.util.Constants;
import com.diligrp.etrade.shared.domain.Message;
import com.diligrp.etrade.shared.domain.builder.MessageBuilder;
import com.diligrp.etrade.shared.type.ErrorCode;
import com.diligrp.etrade.shared.util.AssertUtils;
import com.diligrp.etrade.user.domain.AuthUserVo;
import com.diligrp.etrade.user.exception.UserAuthorizationException;
import com.diligrp.etrade.user.service.IUserAuthorizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用户登录授权Controller
 * 用户登录授权方案目前仍然利用session来实现，密码验证成功后往session中写入登录信息，通过配置的filter
 * 来判断用户是否登录; 在分布式环境下，我们自己实现的分布式session(参见oauth模块的分布式session实现);
 * 整个session的分布式处理对开发者来说是透明的，我们可以像使用传统session的方式来使用分布式session;
 * 如果后期有需要为其他系统提供用户鉴权登录服务，我们也可以进行扩展支持远程接口而不仅限于Servlet方式。
 *
 * @author: brenthuang
 * @date: 2018/01/22
 */
@Controller
@RequestMapping(value = "/user")
public class UserAuthorizationController {

    private static Logger LOG = LoggerFactory.getLogger(UserAuthorizationController.class);

    @Resource
    private IUserAuthorizationService userAuthorizationService;

    @Resource
    private DistributedSessionManager sessionManager;

    @RequestMapping(value = "/oauth/authorizeUser.auth")
    public @ResponseBody Message authorizeUser(@RequestBody AuthUserVo authUser, HttpServletRequest request) {
        try {
            AssertUtils.notEmpty(authUser.getAccount(), "Account must not be empty");
            AssertUtils.notEmpty(authUser.getPassword(), "Password must not be empty");
            Operator operator = userAuthorizationService.authorizeUser(authUser.getAccount(), authUser.getPassword());
            if (sessionManager.ssoEnabled()) {
                boolean sessionExists = sessionManager.sessionExists(operator.getAccessToken());
                if (sessionExists) {
                    LOG.warn("User {} already login, cannot re-login", operator.getAccount());
                    UserAuthorizationException.ErrorCode errorCode = UserAuthorizationException.ErrorCode.USER_ALREADY_LOGIN;
                    return new MessageBuilder().failure(errorCode.getMessage()).code(errorCode.getCode()).build();
                }
            }

            HttpSession session = request.getSession(true);
            session.setAttribute(Constants.SESSION_KEY_OPERATOR, operator);
            userAuthorizationService.registerUserLogin(operator.getAccount(), session.getId());
            LOG.info("User {} login successfully", authUser.getAccount());
            return new MessageBuilder<Void>().success().data(session.getId()).build();
        } catch (UserAuthorizationException uae) {
            LOG.error("User " + authUser.getAccount() + " login authorization failure", uae);
            return new MessageBuilder<Void>().code(uae.getCode()).message(uae.getMessage()).build();
        } catch (IllegalArgumentException iae) {
            LOG.error("User " + authUser.getAccount() + " login authorization exception: " + iae.getMessage());
            ErrorCode errorCode = ErrorCode.ILLEGAL_ARGUMENT;
            return new MessageBuilder<Void>().code(errorCode.getCode()).message(errorCode.getName()).build();
        } catch (Exception ex) {
            LOG.error("User " + authUser.getAccount() + " login authorization exception", ex);
            ErrorCode errorCode = ErrorCode.UNKNOWN_EXCEPTION;
            return new MessageBuilder<Void>().code(errorCode.getCode()).message(errorCode.getName()).build();
        }
    }

    @RequestMapping(value = "/oauth/logout.auth")
    public @ResponseBody Message logout(HttpServletRequest request) {
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();

                Operator operator = (Operator) session.getAttribute(Constants.SESSION_KEY_OPERATOR);
                if (operator != null) {
                    userAuthorizationService.unregisterUserLogin(operator.getAccount());
                    LOG.info("User {} logout successfully", operator.getAccount());
                }
            }

            return new MessageBuilder<Void>().success().build();
        } catch (Exception ex) {
            LOG.error("User logout exception", ex);
            ErrorCode errorCode = ErrorCode.UNKNOWN_EXCEPTION;
            return new MessageBuilder<Void>().code(errorCode.getCode()).message(errorCode.getName()).build();
        }
    }
}
