package com.wangda.alarm.service.impl;

import com.wangda.alarm.service.bean.biz.DeptInfo;
import com.wangda.alarm.service.bean.biz.RoleInfo;
import com.wangda.alarm.service.bean.biz.UserInfo;
import com.wangda.alarm.service.common.util.WebUtil;
import com.wangda.alarm.service.dao.UserInfoDao;
import com.wangda.alarm.service.dao.adaptor.UserInfoAdaptor;
import com.wangda.alarm.service.dao.po.UserInfoPo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-11-04
 */
@Service
public class UserInfoService {

    @Resource
    UserInfoDao userInfoDao;

    @Resource
    DeptInfoService deptInfoService;

    @Resource
    RoleInfoService roleInfoService;

    public UserInfo queryUserInfo(String account) {
        UserInfoPo infoPo = userInfoDao.queryUserInfoByAccount(account);
        if (infoPo == null) {
            return null;
        }

        RoleInfo info = roleInfoService.queryRoleById(infoPo.getRoleId());
        DeptInfo deptInfo = deptInfoService.queryDeptById(infoPo.getDeptId());
        return UserInfoAdaptor.adaptToUserInfo(infoPo, info, deptInfo);
    }

    public void updatePassword(String accout, String newPass, String salt) {
        String saltpass = WebUtil.md5(newPass, salt);
        userInfoDao.updatePassword(saltpass, accout);
    }
}
