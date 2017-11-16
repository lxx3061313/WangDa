package com.wangda.alarm.provider.biz;

import com.wangda.alarm.provider.bean.OpLogReq;
import com.wangda.alarm.provider.bean.OpLogVo;
import com.wangda.alarm.provider.bean.adaptor.BizOpLogVoAdaptor;
import com.wangda.alarm.provider.bean.adaptor.UserInfoVoAdaptor;
import com.wangda.alarm.service.bean.biz.BizOpLog;
import com.wangda.alarm.service.bean.biz.UserInfo;
import com.wangda.alarm.service.bean.biz.UserLoginContext;
import com.wangda.alarm.service.bean.biz.UserSession;
import com.wangda.alarm.service.bean.vo.UserInfoVo;
import com.wangda.alarm.service.common.util.PageRequest;
import com.wangda.alarm.service.dao.adaptor.OpLogAdaptor;
import com.wangda.alarm.service.dao.adaptor.UserInfoAdaptor;
import com.wangda.alarm.service.impl.OpLogService;
import com.wangda.alarm.service.impl.UserInfoService;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

/**
 * @author zhangxin
 * @version 2017-11-05
 */
@Service
public class UserInfoBiz {

    @Resource
    UserInfoService userInfoService;

    @Resource
    OpLogService opLogService;

    public List<UserInfoVo> queryOnlineUsers() {
        UserSession user = UserLoginContext.getUser();
        List<UserInfo> userInfos = userInfoService.queryUserInfosRecru(user.getUserName());
        return UserInfoVoAdaptor.adaptToUserInfoVos(userInfos);
    }

    public UserInfoVo queryCurrentUser() {
        UserSession user = UserLoginContext.getUser();
        return UserInfoVoAdaptor.adaptToUserInfoVo(user);
    }

    public OpLogVo queryOpLogsWhthinUser(OpLogReq req) {
        UserSession user = UserLoginContext.getUser();
        List<UserInfo> userInfos = userInfoService.queryUserInfosRecru(user.getUserName());
        if (CollectionUtils.isEmpty(userInfos)) {
            return new OpLogVo();
        }

        List<String> accounts = userInfos.stream().map(UserInfo::getAccount)
                .collect(Collectors.toList());
        List<BizOpLog> bizOpLogs = opLogService.queryOplogsByOperators(accounts, new PageRequest(req.getCurrentPage(),
                req.getPageSize()));
        return BizOpLogVoAdaptor.adaptToOplogVo(bizOpLogs, bizOpLogs.size());
    }
}
