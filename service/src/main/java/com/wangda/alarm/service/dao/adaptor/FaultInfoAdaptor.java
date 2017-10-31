package com.wangda.alarm.service.dao.adaptor;

import com.wangda.alarm.service.bean.standard.alarminfo.fault.FaultContext;
import com.wangda.alarm.service.bean.standard.alarminfo.fault.FaultRecord;
import com.wangda.alarm.service.dao.po.FaultInfoPo;
import java.util.Date;

/**
 * @author lixiaoxiong
 * @version 2017-10-31
 */
public class FaultInfoAdaptor {
    public static FaultInfoPo adaptToFaultInfoPo(FaultContext context) {
        FaultInfoPo po = new FaultInfoPo();
        po.setTargetTelecode(context.getHeader().getTargetTeleCode());
        po.setSourceTelecode(context.getHeader().getSourceTeleCode());

        FaultRecord faultRecord = context.getBody().getRecords().get(0);
        po.setFaultReason(faultRecord.getFaultReason() == null? "":faultRecord.getFaultReason());
        po.setNocTime(faultRecord.getNocTime());
        po.setProcesstime(faultRecord.getProcessTime());
        po.setRecoverTime(faultRecord.getRecoverTime());
        po.setReserveTime(faultRecord.getReserveTime());
        po.setCreatTime(new Date());
        return po;
    }
}
