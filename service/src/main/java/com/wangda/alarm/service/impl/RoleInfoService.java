package com.wangda.alarm.service.impl;

import com.wangda.alarm.service.bean.biz.RoleInfo;
import com.wangda.alarm.service.dao.RoleInfoDao;
import com.wangda.alarm.service.dao.adaptor.RoleInfoAdaptor;
import com.wangda.alarm.service.dao.po.RolePo;
import javax.annotation.Resource;
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
}
