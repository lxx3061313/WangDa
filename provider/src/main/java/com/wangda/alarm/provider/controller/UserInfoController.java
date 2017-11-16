package com.wangda.alarm.provider.controller;

import com.wangda.alarm.provider.bean.OpLogReq;
import com.wangda.alarm.provider.bean.OpLogVo;
import com.wangda.alarm.provider.biz.UserInfoBiz;
import com.wangda.alarm.service.bean.vo.UserInfoVo;
import com.wangda.alarm.service.common.springconfig.annotation.JsonBody;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhangxin
 * @version 2017-10-27
 */
@Controller
@RequestMapping("/wangda/mobile/user")
public class UserInfoController {

    @Resource
    UserInfoBiz userInfoBiz;

    @RequestMapping("/onlineUsers")
    @JsonBody
    public List<UserInfoVo> queryOnlineUsers() {
        return userInfoBiz.queryOnlineUsers();
    }

    @RequestMapping("/userinfo")
    @JsonBody
    public UserInfoVo queryUserInfo() {
        return userInfoBiz.queryCurrentUser();
    }

    @RequestMapping("/oplogs")
    @JsonBody
    public OpLogVo queryOplog(@RequestBody OpLogReq req) {
        return userInfoBiz.queryOpLogsWhthinUser(req);
    }
}
