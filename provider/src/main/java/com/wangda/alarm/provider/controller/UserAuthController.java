package com.wangda.alarm.provider.controller;

import com.wangda.alarm.provider.biz.UserAuthBiz;
import com.wangda.alarm.service.bean.vo.req.UpdatePassReq;
import com.wangda.alarm.service.common.springconfig.annotation.JsonBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lixiaoxiong
 * @version 2017-10-26
 */
@Controller
@RequestMapping("/wangda/mobile/auth")
public class UserAuthController {

    @Resource
    UserAuthBiz userAuthBiz;

    @RequestMapping("/login")
    @JsonBody
    public void login(String userName, String password, HttpServletResponse response) {
        userAuthBiz.auth(userName, password, response);
    }

    @RequestMapping("/retrievePass")
    @JsonBody
    public void retrievePassword(String userName) {

    }

    @RequestMapping("/updatePassword")
    @JsonBody
    public void updatePassword(@RequestBody UpdatePassReq req) {

    }

    @RequestMapping("/logout")
    @JsonBody
    public void logout() {

    }
}
