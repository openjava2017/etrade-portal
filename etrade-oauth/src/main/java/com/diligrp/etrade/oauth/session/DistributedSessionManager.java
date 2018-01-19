package com.diligrp.etrade.oauth.session;

import com.diligrp.etrade.oauth.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * 分布式事务管理器
 *
 * @author: brenthuang
 * @date: 2018/01/03
 */
public class DistributedSessionManager {
    private static Logger LOG = LoggerFactory.getLogger(DistributedSessionManager.class);

    /**
     * Session最大失效时间
     */
    private int maxInactiveInterval = 30 * 60;
    /**
     * 是否启用单点登录
     */
    private boolean ssoEnabled = false;
    /**
     * Session存储仓库
     */
    private ISessionRepository sessionRepository;

    public boolean sessionExists(String sessionId) {
        return sessionRepository.sessionExists(sessionId);
    }

    public boolean ssoEnabled() {
        return ssoEnabled;
    }

    public void setMaxInactiveInterval(int maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
    }

    public void setSessionRepository(ISessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public void setSsoEnabled(boolean ssoEnabled) {
        this.ssoEnabled = ssoEnabled;
    }

    protected SharedHttpSession createSession(HttpServletRequest request, String sessionId, boolean create) {
        boolean hasSessionId = StringUtils.isNotEmpty(sessionId);
        SharedHttpSession session = null;
        if (!hasSessionId && !create) {
            return null;
        }

        if (hasSessionId) {
            session = loadSession(sessionId);
        }

        if (session == null && create) {
            session = newSession();
        }
        LOG.debug("Session manager created {} http session", session == null ? "null" : "non-null");
        return session;
    }

    protected SharedHttpSession loadSession(String sessionId) {
        SharedHttpSession session = sessionRepository.loadSession(sessionId, maxInactiveInterval);
        LOG.debug("Loading http session from repository[sid: {}, result: {}]", sessionId, session != null);
        return session != null ? session.wrap(false, false, false) : null;
    }

    protected void saveSession(SharedHttpSession session) {
        LOG.debug("Saving http session into repository[sid: {}]", session.getId());
        sessionRepository.saveSession(session, maxInactiveInterval);
    }

    protected void removeSession(SharedHttpSession session) {
        LOG.debug("Removing http session out of repository[sid: {}]", session.getId());
        sessionRepository.removeSession(session);
    }

    protected String newSessionId() {
        UUID uuid = UUID.randomUUID();
        long mostSigBits = uuid.getMostSignificantBits();
        long leastSigBits = uuid.getLeastSignificantBits();
        return (digits(mostSigBits >> 32, 8) +
                digits(mostSigBits >> 16, 4) +
                digits(mostSigBits, 4) +
                digits(leastSigBits >> 48, 4) +
                digits(leastSigBits, 12));
    }

    private String digits(long val, int digits) {
        long hi = 1L << (digits * 4);
        return Long.toHexString(hi | (val & (hi - 1))).substring(1).toUpperCase();
    }

    private SharedHttpSession newSession() {
        return new SharedHttpSession(newSessionId(), maxInactiveInterval).wrap(true, false, false);
    }
}
