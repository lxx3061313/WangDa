package com.wangda.alarm.provider.biz;

import com.wangda.alarm.service.bean.biz.RoleInfo;
import com.wangda.alarm.service.bean.biz.UserInfo;
import com.wangda.alarm.service.bean.biz.UserLoginContext;
import com.wangda.alarm.service.bean.biz.UserSession;
import com.wangda.alarm.service.bean.standard.constant.CookieName;
import com.wangda.alarm.service.bean.vo.req.UpdatePassReq;
import com.wangda.alarm.service.common.util.TokenGenerator;
import com.wangda.alarm.service.common.util.pojo.BizException;
import com.wangda.alarm.service.dao.po.UserInfoPo;
import com.wangda.alarm.service.impl.LoginSessionService;
import com.wangda.alarm.service.impl.ResetPassService;
import com.wangda.alarm.service.impl.UserAuthService;
import com.wangda.alarm.service.impl.UserInfoService;
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
    UserInfoService userInfoService;

    @Resource
    LoginSessionService loginSessionService;

    @Resource
    ResetPassService resetPassService;

    public void auth(String userName, String password, HttpServletResponse response) {
        //密码输入次数大于3次
        if (loginSessionService.isForbiddenLogin(userName)) {
            throw new BizException("密码输入次数大于3次,禁用6个小时");
        }

        UserInfo auth = userAuthService.auth(userName, password);

        //验证成功
        if (auth != null) {
            // 登录成功删除密码输入次数
            loginSessionService.delLogError(userName);

            // 生成token
            String token = TokenGenerator.generate(auth.getAccount());
            UserSession session = UserSession.createBuilder()
                    .addToken(token)
                    .addUserName(auth.getAccount())
                    .addLoginTime(new Date())
                    .addRole(auth.getRoleInfo())
                    .addDept(auth.getDeptInfo())
                    .build();

            // 记录session
            loginSessionService.saveUserSession(session);
            Cookie cookie = new Cookie(CookieName.LOGIN_TOKEN, token);
            cookie.setDomain("mobile.wangda.com");
            cookie.setPath("/");
            response.addCookie(cookie);
        } else {
            //记录密码输入次数
            loginSessionService.recordLogError(userName);
            throw new BizException("用户名或密码错误");
        }
    }

    public void updatePassword(UpdatePassReq req) {
        UserSession user = UserLoginContext.getUser();
        if (user == null) {
            throw new BizException("当前用户不在线");
        }

        //密码输入次数大于3次
        if (loginSessionService.isForbiddenLogin(user.getUserName())) {
            throw new BizException("密码输入次数大于3次,禁用6个小时");
        }

        UserInfo auth = userAuthService.auth(user.getUserName(), req.getOriPass());
        if (auth == null) {
            throw new BizException("原始密码不正确, 不能更新密码");
        }

        resetPassService.resetPass(user.getUserName(), req.getNewPass(), auth.getSalt());
    }

    public void forgetPassword(String userName) {
        UserInfo userInfo = userInfoService.queryUserInfo(userName);
        if (userInfo == null) {
            throw new BizException("用户不存在");
        }

        // 重置密码
        resetPassService.resetPass(userName);
    }

    public void logout() {
        UserSession user = UserLoginContext.getUser();
        if (user == null) {
            throw new BizException("当前用户不在线");
        }
        loginSessionService.remoteUserSession(user.getToken());
    }
}
