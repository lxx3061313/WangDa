package com.wangda.alarm.provider.controller;

import com.wangda.alarm.service.bean.vo.UserInfoVo;
import com.wangda.alarm.service.common.springconfig.annotation.JsonBody;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lixiaoxiong
 * @version 2017-10-27
 */
@Controller
@RequestMapping("/wangda/mobile/user")
public class UserInfoController {

    @RequestMapping("/onlineUsers")
    @JsonBody
    public List<UserInfoVo> queryOnlineUsers() {
        return null;
    }

    @RequestMapping("/userinfo")
    @JsonBody
    public UserInfoVo queryUserInfo() {
        return null;
    }
}
