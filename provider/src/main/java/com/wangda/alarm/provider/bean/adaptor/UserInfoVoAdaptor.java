package com.wangda.alarm.provider.bean.adaptor;

import com.wangda.alarm.service.bean.biz.DeptInfo;
import com.wangda.alarm.service.bean.biz.RoleInfo;
import com.wangda.alarm.service.bean.biz.UserInfo;
import com.wangda.alarm.service.bean.vo.UserInfoVo;
import com.wangda.alarm.service.dao.adaptor.UserRoleMappingAdaptor;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;

/**
 * @author lixiaoxiong
 * @version 2017-11-05
 */
public class UserInfoVoAdaptor {

    private final static String WEIZHI = "未知";

    public static UserInfoVo adaptToUserInfoVo(UserInfo userInfo) {
        UserInfoVo vo = new UserInfoVo();
        vo.setUserName(userInfo.getName());

        List<RoleInfo> roleInfo = userInfo.getRoleInfo();
        StringBuilder role = new StringBuilder();
        if (CollectionUtils.isNotEmpty(roleInfo)) {
            for (RoleInfo info : roleInfo) {
                role.append(info.getName())
                        .append("|");
            }
            String roleName = role.toString();
            vo.setRoleName(roleName.substring(0, roleName.length() - 1));
        } else {
            vo.setRoleName(WEIZHI);
        }

        DeptInfo deptInfo = userInfo.getDeptInfo();
        if (deptInfo != null) {
            vo.setStation(deptInfo.getDeptFullname());
        } else {
            vo.setStation(WEIZHI);
        }
        return vo;
    }

    public static List<UserInfoVo> adaptToUserInfoVos(List<UserInfo> userInfos) {
        if (CollectionUtils.isEmpty(userInfos)) {
            return Collections.EMPTY_LIST;
        }

        return userInfos.stream().map(UserInfoVoAdaptor::adaptToUserInfoVo)
                .collect(Collectors.toList());
    }
}
