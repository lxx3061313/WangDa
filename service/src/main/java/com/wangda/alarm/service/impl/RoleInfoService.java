package com.wangda.alarm.service.impl;

import com.wangda.alarm.service.bean.biz.RoleInfo;
import com.wangda.alarm.service.dao.RoleInfoDao;
import com.wangda.alarm.service.dao.adaptor.RoleInfoAdaptor;
import com.wangda.alarm.service.dao.po.RolePo;
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
public class RoleInfoService {

    @Resource
    RoleInfoDao roleInfoDao;

    RoleInfo queryRoleById(int id) {
        if (id < 0) {
            return null;
        }
        RolePo rolePo = roleInfoDao.queryRoleById(id);
        return RoleInfoAdaptor.adaptToRoleInfo(rolePo);
    }

    List<RoleInfo> queryRoleByIds(List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return Collections.EMPTY_LIST;
        }

        List<RolePo> rolePos = roleInfoDao.queryRolesByids(ids);
        return RoleInfoAdaptor.adaptToRoleInfos(rolePos);
    }

    List<RoleInfo> queryRoldsByPid(int pid) {
        if (pid < 0) {
            return Collections.EMPTY_LIST;
        }

        List<RolePo> rolePos = roleInfoDao.queryRoldsByPid(pid);
        return RoleInfoAdaptor.adaptToRoleInfos(rolePos);
    }

    List<RoleInfo> queryAllRoles() {
        List<RolePo> rolePos = roleInfoDao.queryAllRoles();
        return RoleInfoAdaptor.adaptToRoleInfos(rolePos);
    }
}
