package com.wangda.alarm.service.dao;

import com.wangda.alarm.service.dao.po.RolePo;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * @author lixiaoxiong
 * @version 2017-11-04
 */
@Repository
public interface RoleInfoDao {
    RolePo queryRoleById(int id);
    List<RolePo> queryRolesByids(List<Integer> ids);
    List<RolePo> queryRoldsByPid(int pid);
    List<RolePo> queryAllRoles();
}
