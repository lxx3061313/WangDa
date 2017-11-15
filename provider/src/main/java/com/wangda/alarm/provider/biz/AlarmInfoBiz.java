package com.wangda.alarm.provider.biz;

import com.wangda.alarm.provider.bean.AlarmDetailResp;
import com.wangda.alarm.provider.bean.AlarmDetailVo;
import com.wangda.alarm.provider.bean.AlarmListResp;
import com.wangda.alarm.provider.bean.AlarmOutlineVo;
import com.wangda.alarm.provider.bean.AlarmStatisticsVo;
import com.wangda.alarm.provider.bean.RealTimeAlarmReq;
import com.wangda.alarm.provider.bean.SegmentAlarmStaticsVo;
import com.wangda.alarm.provider.bean.StatisItemVo;
import com.wangda.alarm.provider.bean.adaptor.AlarmVoAdaptor;
import com.wangda.alarm.provider.bean.adaptor.QueryAlarmListAdaptor;
import com.wangda.alarm.service.bean.biz.AlarmInfo;
import com.wangda.alarm.service.bean.biz.AlarmListInfo;
import com.wangda.alarm.service.bean.biz.RealTimeAlarmList;
import com.wangda.alarm.service.bean.biz.UserLoginContext;
import com.wangda.alarm.service.bean.biz.UserSession;
import com.wangda.alarm.service.bean.standard.DeptType;
import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmLevel;
import com.wangda.alarm.service.bean.standard.protocol.SegmentCode;
import com.wangda.alarm.service.bean.standard.protocol.StandardAlarmType;
import com.wangda.alarm.service.bean.vo.RealTimeAlarmVo;
import com.wangda.alarm.service.bean.vo.req.AlarmDetailReq;
import com.wangda.alarm.service.bean.vo.req.AlarmListReq;
import com.wangda.alarm.service.common.util.PageRequest;
import com.wangda.alarm.service.common.util.pojo.BizException;
import com.wangda.alarm.service.dao.req.QueryAlarmListParam;
import com.wangda.alarm.service.impl.AlarmInfoService;
import com.wangda.alarm.service.impl.DeptInfoService;
import com.wangda.alarm.service.impl.OpLogService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-26
 */
@Service
public class AlarmInfoBiz {

    @Resource
    AlarmInfoService alarmInfoService;

    @Resource
    OpLogService opLogService;

    @Resource
    DeptInfoService deptInfoService;

    @Value("${alarm_data_hours_befor}")
    private int alarmDataHoursBefore;


    public AlarmListResp queryAlarmList(AlarmListReq listReq) {
        AlarmListResp resp = new AlarmListResp();
        QueryAlarmListParam param = QueryAlarmListAdaptor.adaptToParam(listReq);
        List<AlarmListInfo> listInfos = alarmInfoService
                .queryAlarmListByParam(param);
        opLogService.createWatchInfoLog(UserLoginContext.getUser().getUserName(), "查看报警");
        List<AlarmOutlineVo> outlineVos = AlarmVoAdaptor.adaptOutlineVos(listInfos);
        resp.setAlarms(outlineVos);
        resp.setTotalCount(alarmInfoService.countAlarmByParam(param));
        return resp;
    }

    public AlarmDetailResp queryAlarmDetail(AlarmDetailReq req) {
        if (req.getAlarmType() == StandardAlarmType.UNKNOW) {
            throw new BizException("报警类型未知, 无法查看详情");
        }

        AlarmDetailResp resp = new AlarmDetailResp();

        //1. 分页查询详情
        List<AlarmInfo> infos = alarmInfoService
                .queryAlarmDetailByParam(req.getSegmentCode(), req.getWorkshopCode(),
                        req.getWorkareaCode(), req.getStationCode(), req.getAlarmLevel(),
                        req.getAlarmType()
                        , req.getDeviceName(),
                        new PageRequest(req.getCurrentPage(), req.getPageSize()));

        opLogService.createWatchInfoLog(UserLoginContext.getUser().getUserName(), "查看报警");
        List<AlarmDetailVo> detailVos = AlarmVoAdaptor.adaptDetailVos(infos);
        resp.setDetails(detailVos);

        //2. 符合条件的记录数
        resp.setTotalCount(
                alarmInfoService.countAlarmDetail(req.getSegmentCode(), req.getWorkshopCode(),
                        req.getWorkareaCode(), req.getStationCode(), req.getAlarmLevel(),
                        req.getAlarmType()
                        , req.getDeviceName()));
        return resp;
    }



    public SegmentAlarmStaticsVo querySegmentStatic(String segmentCode) {
        Map<AlarmLevel, Integer> integerMap = alarmInfoService
                .querySegmentAlarmCount(segmentCode, beforeHoursFromNow(alarmDataHoursBefore),
                        new Date());

        SegmentAlarmStaticsVo vo = new SegmentAlarmStaticsVo();
        vo.setLevelOneCount(integerMap.get(AlarmLevel.LEVEL_ONE));
        vo.setLevelTwoCount(integerMap.get(AlarmLevel.LEVEL_TWO));
        vo.setLevelThreeCount(integerMap.get(AlarmLevel.LEVEL_THREE));
        vo.setWarnCount(integerMap.get(AlarmLevel.WARN));
        return vo;
    }

    /**
     * 查询36小时内的报警数据
     *
     * @return AlarmStatisticsVo
     */
    public AlarmStatisticsVo queryAlarmStatic() {
        List<AlarmInfo> infos = alarmInfoService
                .queryAlarmByTimerange(beforeHoursFromNow(alarmDataHoursBefore),
                        new Date());

        AlarmStatisticsVo vo = new AlarmStatisticsVo();
        // 一级报警个数
        vo.setLevelOneCount(
                infos.stream().filter(p -> p.getAlarmLevel() == AlarmLevel.LEVEL_ONE).count());

        // 二级报警个数
        vo.setLevelTwoCount(
                infos.stream().filter(p -> p.getAlarmLevel() == AlarmLevel.LEVEL_TWO).count());

        // 三级报警个数
        vo.setLevelThreeCount(
                infos.stream().filter(p -> p.getAlarmLevel() == AlarmLevel.LEVEL_THREE).count());

        // 预警个数
        vo.setWarnCount(infos.stream().filter(p -> p.getAlarmLevel() == AlarmLevel.WARN).count());

        List<StatisItemVo> itemVos = new ArrayList<>();
        StatisItemVo cd = new StatisItemVo();
        cd.setSegmentCode(SegmentCode.CDD.getCode());
        cd.setSegmentName(SegmentCode.CDD.getDesc());
        cd.setAlarmCount((int) infos.stream().filter(p -> p.getSegmentCode()
                .equals(SegmentCode.CDD.getCode())).count());
        itemVos.add(cd);

        StatisItemVo cq = new StatisItemVo();
        cq.setSegmentCode(SegmentCode.CQD.getCode());
        cq.setSegmentName(SegmentCode.CQD.getDesc());
        cq.setAlarmCount((int) infos.stream().filter(p -> p.getSegmentCode()
                .equals(SegmentCode.CQD.getCode())).count());
        itemVos.add(cq);

        StatisItemVo gy = new StatisItemVo();
        gy.setSegmentCode(SegmentCode.GYD.getCode());
        gy.setSegmentName(SegmentCode.GYD.getDesc());
        gy.setAlarmCount((int) infos.stream().filter(p -> p.getSegmentCode()
                .equals(SegmentCode.GYD.getCode())).count());
        itemVos.add(gy);

        StatisItemVo dz = new StatisItemVo();
        dz.setSegmentCode(SegmentCode.DZD.getCode());
        dz.setSegmentName(SegmentCode.DZD.getDesc());
        dz.setAlarmCount((int) infos.stream().filter(p -> p.getSegmentCode()
                .equals(SegmentCode.DZD.getCode())).count());
        itemVos.add(dz);

        StatisItemVo gyb = new StatisItemVo();
        gyb.setSegmentCode(SegmentCode.GYBD.getCode());
        gyb.setSegmentName(SegmentCode.GYBD.getDesc());
        gyb.setAlarmCount((int) infos.stream().filter(p -> p.getSegmentCode()
                .equals(SegmentCode.GYBD.getCode())).count());
        itemVos.add(gyb);

        vo.setSegmentCount(itemVos);

        opLogService.createWatchInfoLog(UserLoginContext.getUser().getUserName(), "查看报警统计信息");
        return vo;
    }

    public RealTimeAlarmVo queryRealTimeAlarmVo(RealTimeAlarmReq req) {
        UserSession user = UserLoginContext.getUser();
        List<RealTimeAlarmList> result = new ArrayList<>();
        PageRequest request = new PageRequest(req.getCurrentPage(), req.getPageSize());
        int count = 0;
        // 没有部门信息
        if (user.getDeptInfo() == null) {
            result = alarmInfoService.queryAlarmByDeptAndLevel(null, null, null, null,
                    Arrays.asList(AlarmLevel.LEVEL_ONE, AlarmLevel.LEVEL_TWO,
                            AlarmLevel.LEVEL_THREE,
                            AlarmLevel.WARN), request);
            count = alarmInfoService.countRealTimeAlarmList(null, null, null, null,
                    Arrays.asList(AlarmLevel.LEVEL_ONE, AlarmLevel.LEVEL_TWO,
                            AlarmLevel.LEVEL_THREE,
                            AlarmLevel.WARN));
        } else {

            // 处级接受 一级报警
            if (user.getDeptInfo().getDeptType() == DeptType.SECTION) {
                result = alarmInfoService.queryAlarmByDeptAndLevel(null, null, null, null,
                        Arrays.asList(AlarmLevel.LEVEL_ONE), request);
                count = alarmInfoService.countRealTimeAlarmList(null, null, null, null,
                        Arrays.asList(AlarmLevel.LEVEL_ONE));
            }

            // 段级接受一级,二级报警
            else if (user.getDeptInfo() != null
                    && user.getDeptInfo().getDeptType() == DeptType.SEGMENT) {
                result = alarmInfoService
                        .queryAlarmByDeptAndLevel(user.getDeptInfo().getDeptSimplename(), null,
                                null, null,
                                Arrays.asList(AlarmLevel.LEVEL_ONE, AlarmLevel.LEVEL_TWO), request);
                count = alarmInfoService
                        .countRealTimeAlarmList(user.getDeptInfo().getDeptSimplename()
                                , null, null, null,
                                Arrays.asList(AlarmLevel.LEVEL_ONE, AlarmLevel.LEVEL_TWO));
            }

            // 车间接受一级,二级,三级报警
            else if (user.getDeptInfo() != null
                    && user.getDeptInfo().getDeptType() == DeptType.WORK_SHOP) {
                result = alarmInfoService
                        .queryAlarmByDeptAndLevel(null, user.getDeptInfo().getDeptSimplename(),
                                null, null,
                                Arrays.asList(AlarmLevel.LEVEL_ONE, AlarmLevel.LEVEL_TWO,
                                        AlarmLevel.LEVEL_THREE), request);
                count = alarmInfoService
                        .countRealTimeAlarmList(null, user.getDeptInfo().getDeptSimplename(),
                                null, null,
                                Arrays.asList(AlarmLevel.LEVEL_ONE, AlarmLevel.LEVEL_TWO,
                                        AlarmLevel.LEVEL_THREE));
            }

            // 工区接受一级,二级,三级,预警报警
            else if (user.getDeptInfo() != null &&
                    user.getDeptInfo().getDeptType() == DeptType.WORK_AREA) {
                result = alarmInfoService.queryAlarmByDeptAndLevel(null, null,
                        user.getDeptInfo().getDeptSimplename(), null,
                        Arrays.asList(AlarmLevel.LEVEL_ONE, AlarmLevel.LEVEL_TWO,
                                AlarmLevel.LEVEL_THREE,
                                AlarmLevel.WARN), request);
                count = alarmInfoService.countRealTimeAlarmList(null, null,
                        user.getDeptInfo().getDeptSimplename(), null,
                        Arrays.asList(AlarmLevel.LEVEL_ONE, AlarmLevel.LEVEL_TWO,
                                AlarmLevel.LEVEL_THREE,
                                AlarmLevel.WARN));
            }

            else if (user.getDeptInfo() != null &&
                    user.getDeptInfo().getDeptType() == DeptType.STATION) {
                result = alarmInfoService.queryAlarmByDeptAndLevel(null, null, null,
                        user.getDeptInfo().getDeptSimplename(),
                        Arrays.asList(AlarmLevel.LEVEL_ONE, AlarmLevel.LEVEL_TWO,
                                AlarmLevel.LEVEL_THREE,
                                AlarmLevel.WARN), request);
                count = alarmInfoService.countRealTimeAlarmList(null, null, null,
                        user.getDeptInfo().getDeptSimplename(),
                        Arrays.asList(AlarmLevel.LEVEL_ONE, AlarmLevel.LEVEL_TWO,
                                AlarmLevel.LEVEL_THREE,
                                AlarmLevel.WARN));
            }
        }

        return AlarmVoAdaptor.adaptRealAlarmVo(result, count);
    }

    private Date beforeHoursFromNow(int beforeHours) {
        return DateUtils.addHours(new Date(), -beforeHours);
    }
}
