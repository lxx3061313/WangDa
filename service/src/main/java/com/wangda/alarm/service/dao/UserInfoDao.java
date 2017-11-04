package com.wangda.alarm.service.dao;

import com.wangda.alarm.service.dao.po.UserInfoPo;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author lixiaoxiong
 * @version 2017-10-25
 */
@Repository
public interface UserInfoDao {
    UserInfoPo authUser(String account);
    UserInfoPo queryUserInfoByAccount(String account);
    List<UserInfoPo> queryAllUsers();
    List<UserInfoPo> queryUsersByIds(List<Integer> ids);
    int updatePassword(@Param("password") String password,
            @Param("account") String accout);
}
