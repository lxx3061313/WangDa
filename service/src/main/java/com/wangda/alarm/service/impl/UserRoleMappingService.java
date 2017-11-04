package com.wangda.alarm.service.impl;

import com.wangda.alarm.service.bean.biz.UserRoleMappingInfo;
import com.wangda.alarm.service.dao.UserRoleMappingDao;
import com.wangda.alarm.service.dao.adaptor.UserRoleMappingAdaptor;
import com.wangda.alarm.service.dao.po.UserRoleMappingPo;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-11-04
 */
@Service
public class UserRoleMappingService {

    @Resource
    UserRoleMappingDao userRoleMappingDao;


    public int saveUserRoleMappings(List<UserRoleMappingInfo> infos) {
        if (CollectionUtils.isEmpty(infos)) {
            return 0;
        }

        List<UserRoleMappingPo> roleMappingPos = UserRoleMappingAdaptor
                .adaptToMappingPos(infos);
        return userRoleMappingDao.saveUserRoleMappings(roleMappingPos);
    }

    public List<UserRoleMappingInfo> queryMappingByRoleId(int roleId) {
        if (roleId <= 0) {
            return Collections.EMPTY_LIST;
        }

        List<UserRoleMappingPo> poList = userRoleMappingDao
                .queryMappingByRoleId(roleId);
        if (CollectionUtils.isEmpty(poList)) {
            return Collections.EMPTY_LIST;
        }

        return UserRoleMappingAdaptor.adaptToMappingInfos(poList);
    }

    public List<UserRoleMappingInfo> queryMappingByRoleIds(List<Integer> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return Collections.EMPTY_LIST;
        }

        List<UserRoleMappingPo> mappingPos = userRoleMappingDao
                .queryMappingsByRoleIds(roleIds);
        if (CollectionUtils.isEmpty(mappingPos)) {
            return Collections.EMPTY_LIST;
        }

        return UserRoleMappingAdaptor.adaptToMappingInfos(mappingPos);
    }

    public List<UserRoleMappingInfo> queryAllMappings() {
        List<UserRoleMappingPo> userRoleMappingPos = userRoleMappingDao.queryAllMappings();
        return UserRoleMappingAdaptor.adaptToMappingInfos(userRoleMappingPos);
    }
}
