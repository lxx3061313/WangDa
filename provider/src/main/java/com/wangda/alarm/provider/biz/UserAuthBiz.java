package com.wangda.alarm.provider.biz;

import com.wangda.alarm.service.bean.biz.RoleInfo;
import com.wangda.alarm.service.bean.biz.UserSession;
import com.wangda.alarm.service.bean.standard.constant.CookieName;
import com.wangda.alarm.service.common.util.TokenGenerator;
import com.wangda.alarm.service.common.util.pojo.BizException;
import com.wangda.alarm.service.dao.po.UserInfoPo;
import com.wangda.alarm.service.impl.LoginSessionService;
import com.wangda.alarm.service.impl.UserAuthService;
import java.util.Date;
import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-26
 */
@Service
public class UserAuthBiz {

    @Resource
    UserAuthService userAuthService;

    @Resource
    LoginSessionService loginSessionService;

    @Resource
    public void auth(String userName, String password, HttpServletResponse response) {
        UserInfoPo auth = userAuthService.auth(userName, password);

        //验证成功
        if (auth != null) {
            String token = TokenGenerator.generate(auth.getUserName());
            UserSession build = UserSession.createBuilder()
                    .addToken(token)
                    .addUserName(auth.getUserName())
                    .addLoginTime(new Date())
                    .addRole(new RoleInfo())
                    .build();
            loginSessionService.saveUserSession(build);

            Cookie cookie = new Cookie(CookieName.LOGIN_TOKEN, token);
            cookie.setDomain("mobile.wangda.com");
            cookie.setPath("/");
            response.addCookie(cookie);
        } else {
            throw new BizException("用户名或密码错误");
        }
    }

}
