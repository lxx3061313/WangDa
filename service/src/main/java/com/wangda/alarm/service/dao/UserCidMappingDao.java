package com.wangda.alarm.service.dao;

import com.wangda.alarm.service.dao.po.UserCidMappingPo;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * @author lixiaoxiong
 * @version 2017-11-05
 */
@Repository
public interface UserCidMappingDao {
    int saveUserCidMapping(UserCidMappingPo po);
    String queryCidByAccount(String account);
    List<String> queryCidsByAccounts(List<String> accounts);
}
