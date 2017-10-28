package com.wangda.alarm.service.impl;

import com.wangda.alarm.service.bean.standard.BizBeanType;
import com.wangda.alarm.service.bean.standard.DataType;
import com.wangda.alarm.service.bean.standard.DataTypeCode;
import com.wangda.alarm.service.bean.standard.VersionCode;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.QueryAlarmBody;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.QueryAlarmData;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.QueryAlarmHeader;
import com.wangda.alarm.service.bean.standard.alarminfo.resp.QueryAlarmParam;
import java.util.Date;

/**
 * @author lixiaoxiong
 * @version 2017-10-28
 */
public class QueryAlarmParamBuilder {

    private final static int PARAM_DATA_LEN = 21;
    private final static String SOURCE_TELECODE = "WEb";
    private final static byte DATA_NAME = (byte)0x42;

    /**
     * 电务段码
     */
    private String segmentCode;

    /**
     * 查询起始时间
     */
    private Date startTime;

    /**
     * 查询结束时间
     */
    private Date endTime;

    /**
     * 报警类型数量(写死)
     */
    private short alarmTypeNum = (short) 65535;


    public QueryAlarmParamBuilder addSegmentCode(String code) {
        this.segmentCode = code;
        return this;
    }

    public QueryAlarmParamBuilder addStartTime(Date time) {
        this.startTime = time;
        return this;
    }

    public QueryAlarmParamBuilder addEndTime(Date time) {
        this.endTime = time;
        return this;
    }

    public QueryAlarmParam build () {
        QueryAlarmParam param = new QueryAlarmParam();
        param.setBeanType(BizBeanType.DATA);
        param.setDataLength(PARAM_DATA_LEN);

        QueryAlarmData data = new QueryAlarmData();
        QueryAlarmHeader header = new QueryAlarmHeader();
        header.setTargetTeleCode(segmentCode);
        header.setSourceTeleCode(SOURCE_TELECODE);
        header.setDatacmd((DataType.CMD.getCode()));
        header.setVersion(VersionCode.E_ONE.getCode());
        header.setDataType(DataTypeCode.QUERY_CMD.getDataType());
        header.setDatasubType(DataTypeCode.QUERY_CMD.getDataSubType());
        data.setHeader(header);

        QueryAlarmBody body = new QueryAlarmBody();
        body.setDataname(DATA_NAME);
        body.setStarttime(this.startTime);
        body.setEndTime(this.endTime);
        body.setAlarmTypeNum(alarmTypeNum);
        data.setBody(body);
        param.setData(data);
        return param;
    }
    public static QueryAlarmParamBuilder builder() {
        return new QueryAlarmParamBuilder();
    }
}
