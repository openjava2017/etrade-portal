package com.diligrp.etrade.oauth.session;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 分布式事务过滤器实现
 *
 * @author: brenthuang
 * @date: 2018/01/03
 */
public class DistributedSessionFilter implements Filter {
    private DistributedSessionManager sessionManager;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        if (!shouldFilter(httpRequest)) {
            chain.doFilter(request, response);
            return;
        }

        HttpServletRequestAdapter requestWrapper = new HttpServletRequestAdapter(httpRequest, httpResponse, sessionManager);
        try {
            chain.doFilter(requestWrapper, httpResponse);
        } finally {
            requestWrapper.finished();
        }
    }

    @Override
    public void destroy() {
    }

    public void setSessionManager(DistributedSessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    protected boolean shouldFilter(HttpServletRequest request) {
        return true;
    }
}
