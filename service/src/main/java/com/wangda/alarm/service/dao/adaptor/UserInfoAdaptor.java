package com.wangda.alarm.service.dao.adaptor;

import com.wangda.alarm.service.bean.biz.DeptInfo;
import com.wangda.alarm.service.bean.biz.RoleInfo;
import com.wangda.alarm.service.bean.biz.UserInfo;
import com.wangda.alarm.service.bean.biz.UserStatus;
import com.wangda.alarm.service.common.util.pojo.Sex;
import com.wangda.alarm.service.dao.po.UserInfoPo;
import java.util.List;

/**
 * @author lixiaoxiong
 * @version 2017-11-04
 */
public class UserInfoAdaptor {
    public static UserInfo adaptToUserInfo(UserInfoPo po, List<RoleInfo> roleInfos, DeptInfo deptInfo) {
        UserInfo userInfo = new UserInfo();
        userInfo.setAccount(po.getAccount());
        userInfo.setSalt(po.getSalt());
        userInfo.setName(po.getName());
        userInfo.setBirthday(po.getBirthday());
        userInfo.setSex(Sex.codeOf(po.getSex()));
        userInfo.setEmail(po.getEmail());
        userInfo.setPhone(po.getPhone());
        userInfo.setRoleInfo(roleInfos);
        userInfo.setDeptInfo(deptInfo);
        userInfo.setStatus(UserStatus.codeOf(po.getStatus()));
        return userInfo;
    }
}
