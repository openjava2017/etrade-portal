package com.diligrp.etrade.web.controller;

import com.diligrp.etrade.web.httl.HttlLayoutViewSupport;
import com.diligrp.etrade.shared.domain.Message;
import com.diligrp.etrade.shared.domain.builder.MessageBuilder;
import com.diligrp.etrade.shared.sequence.IKeyGenerator;
import com.diligrp.etrade.shared.sequence.KeyGeneratorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 测试控制器
 *
 * @author: brenthuang
 * @date: 2018/01/04
 */
@Controller
public class TestController extends HttlLayoutViewSupport {

    private static Logger LOG = LoggerFactory.getLogger(TestController.class);

    @Resource
    private KeyGeneratorManager keyGeneratorManager;

    @RequestMapping(value = "/")
    public ModelAndView index() {
        return toDefault("application/index");
    }

    @RequestMapping(value = "/test.action")
    public @ResponseBody Message test(HttpServletRequest request) {
        LOG.info(request.getPathInfo());
        LOG.info(request.getQueryString());
        Enumeration<String> params = request.getParameterNames();
        while (params.hasMoreElements()) {
            String name = params.nextElement();
            LOG.info("{} = {}", name, request.getParameter(name));
        }
        IKeyGenerator keyGenerator = keyGeneratorManager.getKeyGenerator(KeyGeneratorManager.SequenceKey.FUND_TRANSACTION);
        return new MessageBuilder().success(String.valueOf(keyGenerator.nextId())).build();
    }
}
