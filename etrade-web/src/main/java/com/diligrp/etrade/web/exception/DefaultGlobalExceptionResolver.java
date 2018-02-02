package com.diligrp.etrade.web.exception;

import com.diligrp.etrade.web.httl.HttlLayoutViewSupport;
import com.diligrp.etrade.web.util.AjaxHttpUtils;
import com.diligrp.etrade.shared.domain.Message;
import com.diligrp.etrade.shared.domain.builder.MessageBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 *
 * @author: brenthuang
 * @date: 2018/01/02
 */
public class DefaultGlobalExceptionResolver extends HttlLayoutViewSupport implements HandlerExceptionResolver {

    private static Logger LOG = LoggerFactory.getLogger(DefaultGlobalExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        LOG.error("System unknown exception", ex);
        if (AjaxHttpUtils.isAjaxRequest(request)) {
            Message<Void> message = new MessageBuilder<Void>().failure(ex).build();
            AjaxHttpUtils.sendResponse(response, message);
            return null;
        } else {
            Map<String, Object> params = new HashMap<>(4);
            params.put("requestUri", request.getRequestURI());
            params.put("message", ex.getMessage());
            StringWriter sw = new StringWriter();
            ex.printStackTrace(new PrintWriter(sw));
            params.put("stackTrace", sw.toString());
            return toDefault("application/exception", params);
        }
    }
}
