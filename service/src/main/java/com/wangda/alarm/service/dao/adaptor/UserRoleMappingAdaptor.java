package com.wangda.alarm.service.dao.adaptor;

import com.wangda.alarm.service.bean.biz.UserRoleMappingInfo;
import com.wangda.alarm.service.dao.po.UserRoleMappingPo;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;

/**
 * @author lixiaoxiong
 * @version 2017-11-04
 */
public class UserRoleMappingAdaptor {
    public static UserRoleMappingInfo adaptToMappingInfo(UserRoleMappingPo po) {
        UserRoleMappingInfo info = new UserRoleMappingInfo();
        info.setId(po.getId());
        info.setUserId(po.getUserId());
        info.setRoleId(po.getRoleId());
        info.setCreateTime(po.getCreateTime());
        return info;
    }

    public static List<UserRoleMappingInfo> adaptToMappingInfos(List<UserRoleMappingPo> pos) {
        if (CollectionUtils.isEmpty(pos)) {
            return Collections.EMPTY_LIST;
        }

        return pos.stream().map(UserRoleMappingAdaptor::adaptToMappingInfo)
                .collect(Collectors.toList());
    }

    public static UserRoleMappingPo adaptToMappingPo(UserRoleMappingInfo info) {
        UserRoleMappingPo po = new UserRoleMappingPo();
        po.setUserId(info.getUserId());
        po.setRoleId(info.getRoleId());
        po.setCreateTime(info.getCreateTime());
        return po;
    }

    public static List<UserRoleMappingPo> adaptToMappingPos(List<UserRoleMappingInfo> infos) {
        if (CollectionUtils.isEmpty(infos)) {
            return Collections.EMPTY_LIST;
        }

        return infos.stream().map(UserRoleMappingAdaptor::adaptToMappingPo)
                .collect(Collectors.toList());
    }
}
