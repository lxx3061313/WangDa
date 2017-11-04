package com.wangda.alarm.service.impl.filter;

import com.wangda.alarm.service.bean.biz.UserLoginContext;
import com.wangda.alarm.service.bean.biz.UserSession;
import com.wangda.alarm.service.bean.standard.constant.CookieName;
import com.wangda.alarm.service.common.util.RequestUtil;
import com.wangda.alarm.service.common.util.json.JsonUtil;
import com.wangda.alarm.service.common.util.pojo.APIResponse;
import com.wangda.alarm.service.impl.LoginSessionService;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-26
 */
@Service("loginFilter")
public class LoginFilter extends UFilter<UserSession> {

    public static List<String> excludUrls = new ArrayList<>();

    static {
        excludUrls.add("/wangda/mobile/auth/login");
        excludUrls.add("/wangda/mobile/auth/forgetPassword");
    }

    @Resource
    LoginSessionService loginSessionService;

    @Override
    protected UserSession authUser(String token, HttpServletRequest httpServletRequest) {
       return loginSessionService.queryUserSession(token);
    }

    @Override
    protected String cookieName() {
        return CookieName.LOGIN_TOKEN;
    }

    @Override
    protected void onUserLogin(UserSession user) {
        UserLoginContext.setUser(user);
    }

    @Override
    protected void onUserNotLogin(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        APIResponse<RedirectResponse> error = APIResponse.error(100, "登录信息失效, 请重新登录");
        error.setData(new RedirectResponse(""));
        response(error, response);
    }

    private void response(Object value, HttpServletResponse response) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        JsonUtil.instance().writeValue(response.getWriter(), value);
    }

    private static class RedirectResponse implements Serializable {

        /** 跳转地址 */
        private String target;

        public RedirectResponse() {
        }

        public RedirectResponse(String target) {
            this.target = target;
        }

        public String getTarget() {
            return target;
        }

        public void setTarget(String target) {
            this.target = target;
        }

        @Override
        public String toString() {
            return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
        }
    }

    @Override
    protected void onFinal() {
        UserLoginContext.release();
    }

    @Override
    protected boolean excludeUri(HttpServletRequest request, HttpServletResponse response) {
        String pathInfo = RequestUtil.getPathInfo(request);
        for (String url : excludUrls) {
            if (url.equals(pathInfo)) {
                return true;
            }
        }
        return false;
    }
}
