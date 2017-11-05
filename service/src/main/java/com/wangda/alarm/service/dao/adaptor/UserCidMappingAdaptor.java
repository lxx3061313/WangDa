package com.wangda.alarm.service.dao.adaptor;

import com.wangda.alarm.service.bean.biz.UserCidMappingInfo;
import com.wangda.alarm.service.dao.po.UserCidMappingPo;
import java.util.Date;

/**
 * @author lixiaoxiong
 * @version 2017-11-05
 */
public class UserCidMappingAdaptor {
    public static UserCidMappingPo adaptToMappingPo(UserCidMappingInfo info) {
        UserCidMappingPo po = new UserCidMappingPo();
        po.setAccount(info.getAccount());
        po.setCid(info.getCid());

        Date current = new Date();
        po.setCreateTime(current);
        po.setUpdateTime(current);
        return po;
    }

    public static UserCidMappingInfo adaptMappingInfo(UserCidMappingPo po) {
        UserCidMappingInfo info = new UserCidMappingInfo();
        info.setAccount(po.getAccount());
        info.setCid(po.getCid());
        return info;
    }
}
