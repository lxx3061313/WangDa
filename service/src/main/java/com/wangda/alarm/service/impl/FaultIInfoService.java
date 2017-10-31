package com.wangda.alarm.service.impl;

import com.wangda.alarm.service.bean.standard.alarminfo.fault.FaultContext;
import com.wangda.alarm.service.dao.FaultInfoDao;
import com.wangda.alarm.service.dao.adaptor.FaultInfoAdaptor;
import com.wangda.alarm.service.dao.po.FaultInfoPo;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-31
 */
@Service
public class FaultIInfoService {

    @Resource
    FaultInfoDao faultInfoDao;

    public int saveFaultInfo(FaultContext context) {
        FaultInfoPo infoPo = FaultInfoAdaptor.adaptToFaultInfoPo(context);
        return faultInfoDao.saveFaultInfo(infoPo);
    }
}
