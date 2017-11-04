package com.wangda.alarm.service.impl;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.wangda.alarm.service.bean.biz.DeptInfo;
import com.wangda.alarm.service.bean.biz.RoleInfo;
import com.wangda.alarm.service.bean.biz.UserInfo;
import com.wangda.alarm.service.common.util.SplitterUtil;
import com.wangda.alarm.service.common.util.WebUtil;
import com.wangda.alarm.service.dao.UserInfoDao;
import com.wangda.alarm.service.dao.adaptor.UserInfoAdaptor;
import com.wangda.alarm.service.dao.po.UserInfoPo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-26
 */
@Service
public class UserAuthService {
    private final static Splitter COMMA_SPLITTER = Splitter.on(",");
    @Resource
    UserInfoDao userInfoDao;

    @Resource
    RoleInfoService roleInfoService;

    @Resource
    DeptInfoService deptInfoService;

    public UserInfo auth(String userName, String password) {
        UserInfoPo userInfoPo = userInfoDao.authUser(userName);
        if (userInfoPo == null) {
            return null;
        }

        String comparePass = WebUtil.md5(password, userInfoPo.getSalt());
        if (comparePass.equals(userInfoPo.getPassword())) {
            DeptInfo de = deptInfoService.queryDeptById(userInfoPo.getDeptId());
            List<RoleInfo> roles = roleInfoService.queryRoleByIds(
                    SplitterUtil.splitByCommaToIntList(userInfoPo.getRoleId()));
            return UserInfoAdaptor.adaptToUserInfo(userInfoPo, roles, de);
        }
        return null;
    }
}
