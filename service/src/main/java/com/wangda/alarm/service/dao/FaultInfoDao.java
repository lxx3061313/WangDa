package com.wangda.alarm.service.dao;

import com.wangda.alarm.service.dao.po.FaultInfoPo;
import org.springframework.stereotype.Repository;

/**
 * @author lixiaoxiong
 * @version 2017-10-31
 */
@Repository
public interface FaultInfoDao {
    int saveFaultInfo(FaultInfoPo po);
}
