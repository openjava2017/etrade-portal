package com.diligrp.etrade.web.util;

import com.diligrp.etrade.shared.Constants;
import com.diligrp.etrade.shared.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Ajax工具类
 *
 * @author: brenthuang
 * @date: 2018/01/02
 */
public class AjaxHttpUtils {
    private static Logger LOG = LoggerFactory.getLogger(AjaxHttpUtils.class);

    public static final void sendResponse(HttpServletResponse response, Object dataPacket) {
        try {
            String content = JsonUtils.toJsonString(dataPacket);
            response.setContentType(WebConstants.CONTENT_TYPE_JSON);
            byte[] responseBytes = content.getBytes(Constants.CHARSET_UTF8);
            response.setContentLength(responseBytes.length);
            response.getOutputStream().write(responseBytes);
            response.flushBuffer();
        } catch (IOException iex) {
            LOG.error("Failed to write data packet back", iex);
        }
    }

    public static final String extractHttpBody(HttpServletRequest request) {
        String content = "";
        try {
            String line;
            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                content = content.concat(line);
            }
        } catch (IOException iex) {
            LOG.error("Failed to extract http body", iex);
        }
        return content;
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String requestWith = request.getHeader(WebConstants.AJAX_HTTP_HEADER);
        return WebConstants.XML_HTTP_REQUEST.equalsIgnoreCase(requestWith);
    }
}
