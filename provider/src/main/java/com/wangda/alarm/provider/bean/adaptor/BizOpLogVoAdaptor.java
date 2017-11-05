package com.wangda.alarm.provider.bean.adaptor;

import com.wangda.alarm.provider.bean.OpLogItemVo;
import com.wangda.alarm.provider.bean.OpLogVo;
import com.wangda.alarm.service.bean.biz.BizOpLog;
import com.wangda.alarm.service.common.util.DateFormatUtil;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections.CollectionUtils;

/**
 * @author lixiaoxiong
 * @version 2017-11-05
 */
public class BizOpLogVoAdaptor {
    public static OpLogItemVo adaptToOplogItem(BizOpLog logs) {
        OpLogItemVo vo = new OpLogItemVo();
        vo.setUserName(logs.getOperator());
        vo.setLogTime(DateFormatUtil.format2M2d2h2m(logs.getCreateTime()));
        vo.setLogContext(logs.getOpDesc());
        return vo;
    }

    public static OpLogVo adaptToOplogVo(List<BizOpLog> logs, int totalCount) {
        OpLogVo vo = new OpLogVo();
        vo.setTotalCount(totalCount);
        if (CollectionUtils.isEmpty(logs)) {
            vo.setLogs(Collections.EMPTY_LIST);
        } else {
            List<OpLogItemVo> collect = logs.stream().map(BizOpLogVoAdaptor::adaptToOplogItem)
                    .collect(Collectors.toList());
            vo.setLogs(collect);
        }
        return vo;
    }
}
