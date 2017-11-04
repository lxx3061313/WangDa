package com.wangda.alarm.service.impl;

import com.wangda.alarm.service.dao.BizOpLogDao;
import com.wangda.alarm.service.dao.po.BizOpLogPo;
import java.util.List;
import javax.annotation.Resource;

/**
 * @author lixiaoxiong
 * @version 2017-09-18
 */
public abstract class AbstractBizOpLogService<T> {

    @Resource
    BizOpLogDao bizOpLogDao;

    public void saveLog(T t) {
        bizOpLogDao.saveOpLog(this.adapt(t));
    }

    abstract BizOpLogPo adapt(T t);
}
