package com.wangda.alarm.service.dao;

import com.wangda.alarm.service.dao.po.UserRoleMappingPo;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * @author lixiaoxiong
 * @version 2017-11-04
 */
@Repository
public interface UserRoleMappingDao {
    List<UserRoleMappingPo> queryMappingByUserId(int userId);
    List<UserRoleMappingPo> queryMappingByRoleId(int roleId);
    List<UserRoleMappingPo> queryMappingsByRoleIds(List<Integer> roleIds);
    List<UserRoleMappingPo> queryAllMappings();
    int saveUserRoleMapping(UserRoleMappingPo po);
    int saveUserRoleMappings(List<UserRoleMappingPo> pos);
}
