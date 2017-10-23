package com.wangda.alarm.provider.controller;

import com.wangda.alarm.service.springconfig.annotation.JsonBody;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
@Controller
@RequestMapping("/wangda/test/")
public class TestController {
    private final static Logger logger = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/just")
    @JsonBody
    public String test() {
        return "succ";
    }
}
