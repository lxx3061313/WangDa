package com.wangda.alarm.service.common.util;

import com.wangda.alarm.service.common.util.json.JsonUtil;
import java.io.IOException;
import javax.servlet.ServletResponse;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhangxin
 *         Created on 17/3/12.
 */
public class WebUtil {
    public static Logger logger = LoggerFactory.getLogger(WebUtil.class);

    public static void responseJson(Object value, ServletResponse response){
        response.setContentType("application/json; charset=UTF-8");

        try {
            JsonUtil.instance().writeValue(response.getWriter(), value);
        } catch (IOException e) {
            logger.error("ServletResponse返回Json写流失败",e);
            throw UnsafeUtil.throwException(e);
        }
    }

    public static String md5(String password, String saltSource) {
        ByteSource salt = new Md5Hash(saltSource);
        return new SimpleHash("MD5", password, salt, 1024).toString();
    }
}
