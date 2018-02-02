package com.diligrp.etrade.oauth.session;

import com.diligrp.etrade.oauth.util.Constants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Session超时过滤器实现
 *
 * @author: brenthuang
 * @date: 2018/01/04
 */
public class UserSessionTimeoutFilter implements Filter {

    private String redirectUrl = "/";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Expired user session
        HttpSession session = httpRequest.getSession(false);
        if (session == null || session.getAttribute(Constants.SESSION_KEY_OPERATOR) == null) {
            httpResponse.setHeader(Constants.HTTP_SESSION_STATUS, Constants.HTTP_SESSION_EXPIRED);

            if (!isAjaxRequest(httpRequest)) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + redirectUrl);
            } else {
                sessionExpired(httpResponse);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    protected void sessionExpired(HttpServletResponse response) throws IOException {
        String content = "{\"code\": 10, \"message\": \"session_expired\"}";
        response.setContentType(Constants.CONTENT_TYPE_JSON);
        byte[] responseBytes = content.getBytes(Constants.CHARSET_UTF8);
        response.setContentLength(responseBytes.length);
        response.getOutputStream().write(responseBytes);
        response.flushBuffer();
    }

    private boolean isAjaxRequest(HttpServletRequest request) {
        String requestWith = request.getHeader(Constants.AJAX_HTTP_HEADER);
        return Constants.XML_HTTP_REQUEST.equalsIgnoreCase(requestWith);
    }

}
