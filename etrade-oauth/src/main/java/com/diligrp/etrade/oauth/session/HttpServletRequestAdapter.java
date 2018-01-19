package com.diligrp.etrade.oauth.session;

import com.diligrp.etrade.oauth.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.*;
import java.util.Arrays;
import java.util.Optional;

/**
 * HttpServletRequest适配器实现
 *
 * @author: brenthuang
 * @date: 2018/01/03
 */
public class HttpServletRequestAdapter extends HttpServletRequestWrapper {
    private static Logger LOG = LoggerFactory.getLogger(HttpServletRequestAdapter.class);
    private static final String SESSION_ID_COOKIE = "accessToken";

    private HttpSessionContext context;
    private HttpServletResponse response;
    private DistributedSessionManager sessionManager;

    public HttpServletRequestAdapter(HttpServletRequest request, HttpServletResponse response,
                                     DistributedSessionManager sessionManager) {
        super(request);
        this.response = response;
        this.sessionManager = sessionManager;
        this.context = new HttpSessionContext();
    }

    @Override
    public HttpSession getSession(boolean create) {
        SharedHttpSession session = context.getSession();
        if (session != null) {
            if (session.isExpired() && !create) {
                return null;
            }
            return session;
        }

        String sessionId = getSessionId();
        SharedHttpSession newSession = sessionManager.createSession(this, sessionId, create);
        if (newSession != null) {
            context.attachSession(newSession, new HttpSessionContext.ISessionContextListener() {
                @Override
                public void sessionCreated() {
                    LOG.debug("Writing {}={} into cookie", SESSION_ID_COOKIE, newSession.getId());
                    Cookie cookie = new Cookie(SESSION_ID_COOKIE, newSession.getId());
                    String contextPath = getContextPath();
                    cookie.setPath(StringUtils.isEmpty(contextPath) ? "/" : contextPath);
                    cookie.setMaxAge(-1);
                    response.addCookie(cookie);
                }

                @Override
                public void sessionInvalidated() {
                    LOG.debug("Removing {} out of cookie, session expired", SESSION_ID_COOKIE);
                    Cookie cookie = new Cookie(SESSION_ID_COOKIE, null);
                    cookie.setPath(getContextPath());
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }

                @Override
                public void onDestroy() {
                    if (!newSession.isNew() && !newSession.isDirty()) {
                        return;
                    }
                    if (newSession.isNew() && newSession.isExpired()) {
                        return;
                    }

                    if (newSession.isExpired()) {
                        sessionManager.removeSession(newSession);
                    } else {
                        sessionManager.saveSession(newSession);
                    }
                }
            });
        } else if (StringUtils.isNotEmpty(sessionId)) {
            // Avoid session repository access frequently
            LOG.debug("Removing {}={} out of cookie, no session created", SESSION_ID_COOKIE, sessionId);
            Cookie cookie = new Cookie(SESSION_ID_COOKIE, null);
            cookie.setPath(getContextPath());
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        return newSession;
    }

    @Override
    public HttpSession getSession() {
        return getSession(true);
    }

    public void finished() {
        context.destroy();
    }

    private String getSessionId() {
        Cookie[] cookies = getCookies();
        if (cookies != null) {
            Optional<Cookie> result = Arrays.stream(cookies).filter(
                    cookie -> SESSION_ID_COOKIE.equals(cookie.getName())).findFirst();
            if (result.isPresent()) {
                LOG.debug("Get jsessionid {} from cookie", result.get().getValue());
                return result.get().getValue();
            }
        }

        return null;
    }
}
