package com.wangda.alarm.service.impl;

import com.google.common.base.Strings;
import com.wangda.alarm.service.bean.biz.UserCidMappingInfo;
import com.wangda.alarm.service.dao.UserCidMappingDao;
import com.wangda.alarm.service.dao.adaptor.UserCidMappingAdaptor;
import com.wangda.alarm.service.dao.po.UserCidMappingPo;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-11-05
 */
@Service
public class UserCidMappingService {

    @Resource
    UserCidMappingDao  userCidMappingDao;

    public int saveMapping(UserCidMappingInfo info) {
        if (info == null) {
            return 0;
        }

        UserCidMappingPo mappingPo = UserCidMappingAdaptor.adaptToMappingPo(info);
        return userCidMappingDao.saveUserCidMapping(mappingPo);
    }

    public String queryCidByAccount(String account) {
        if (Strings.isNullOrEmpty(account)) {
            return null;
        }

        return userCidMappingDao.queryCidByAccount(account);
    }

    public List<String> queryCidsByAccounts(List<String> accounts) {
        if (CollectionUtils.isEmpty(accounts)) {
            return Collections.EMPTY_LIST;
        }

        return userCidMappingDao.queryCidsByAccounts(accounts);
    }
}
