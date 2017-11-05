package com.wangda.alarm.service.dao.adaptor;

import com.wangda.alarm.service.bean.biz.BizOpLog;
import com.wangda.alarm.service.bean.biz.OpLogType;
import com.wangda.alarm.service.dao.po.BizOpLogPo;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;

/**
 * @author lixiaoxiong
 * @version 2017-11-05
 */
public class OpLogAdaptor {
    public static BizOpLog adaptBizOplog(BizOpLogPo po) {
        BizOpLog log = new BizOpLog();
        log.setBizId(po.getBizId());
        log.setOperator(po.getOperator());
        log.setOpDesc(po.getOpDesc());
        log.setOpLabel(OpLogType.nameOf(po.getOpLabel()));
        log.setCreateTime(po.getCreateTime());
        return log;
    }

    public static List<BizOpLog> adaptBizOplogs(List<BizOpLogPo> pos) {
        if (CollectionUtils.isEmpty(pos)) {
            return Collections.EMPTY_LIST;
        }
        return pos.stream().map(OpLogAdaptor::adaptBizOplog)
                .collect(Collectors.toList());
    }
}
