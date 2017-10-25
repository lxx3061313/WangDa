package com.wangda.alarm.service.dao;

import com.wangda.alarm.service.dao.po.UserInfoPo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author lixiaoxiong
 * @version 2017-10-25
 */
@Repository
public interface UserInfoDao {
    UserInfoPo authUser(@Param("userName") String userName);
}
