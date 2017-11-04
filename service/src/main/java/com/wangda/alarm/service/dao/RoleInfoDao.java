package com.wangda.alarm.service.dao;

import com.wangda.alarm.service.dao.po.RolePo;
import org.springframework.stereotype.Repository;

/**
 * @author lixiaoxiong
 * @version 2017-11-04
 */
@Repository
public interface RoleInfoDao {
    RolePo queryRoleById(int id);
}
