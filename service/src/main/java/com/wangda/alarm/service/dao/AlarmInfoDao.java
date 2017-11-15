package com.wangda.alarm.service.dao;

import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmLevel;
import com.wangda.alarm.service.dao.po.AlarmInfoPo;
import com.wangda.alarm.service.dao.po.AlarmListPo;
import com.wangda.alarm.service.dao.po.RealTimeAlarmListPo;
import com.wangda.alarm.service.dao.req.QueryAlarmDetailParam;
import com.wangda.alarm.service.dao.req.QueryAlarmListParam;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

/**
 * @author lixiaoxiong
 * @version 2017-10-25
 */
@Repository
public interface AlarmInfoDao {

    /**
     * 根据电务段跟报警级别来筛选数据
     * @param segment 电务段码
     * @param level 报警级别
     * @return 筛选的数据
     */
    List<AlarmInfoPo> queryAlarmBySegAndLev(@Param("segment") String segment,
            @Param("level")AlarmLevel level);

    int countAlarmByParam(QueryAlarmListParam  param);


    List<AlarmInfoPo> queryAlarmByDeptAndLevel(@Param("segmentCode") String segment,
            @Param("workshopCode") String workshopCode,
            @Param("workareaCode") String workareaCode,
            @Param("levels") List<AlarmLevel> levels, RowBounds rowBounds);

    int countAlarmByDeptAndLevel(@Param("segmentCode") String segment,
            @Param("workshopCode") String workshopCode,
            @Param("workareaCode") String workareaCode,
            @Param("levels") List<AlarmLevel> levels);

    List<AlarmInfoPo> queryAlarmDetail(QueryAlarmDetailParam param, RowBounds rowBounds);
    int countAlarmDetail(QueryAlarmDetailParam param);

    List<AlarmListPo> queryAlarmByParam(QueryAlarmListParam param, RowBounds rowBounds);

    int saveAlarmInfo(AlarmInfoPo po);

    int saveAlarmInfos(List<AlarmInfoPo> pos);

    List<AlarmInfoPo> queryAlarmByTimeRange(@Param("from")Date from, @Param("to") Date to);
    List<AlarmInfoPo> queryAlarmBySegmentInTimeRange(@Param("segmentCode") String segmentCode,
            @Param("from")Date from, @Param("to") Date to);

    List<RealTimeAlarmListPo> queryRealtimeAlarmList(@Param("segmentCode") String segment,
            @Param("workshopCode") String workshopCode,
            @Param("workareaCode") String workareaCode,
            @Param("stationCode") String stationCode,
            @Param("levels") List<AlarmLevel> levels, RowBounds rowBounds);

    int countRealtimeAlarmList(@Param("segmentCode") String segment,
            @Param("workshopCode") String workshopCode,
            @Param("workareaCode") String workareaCode,
            @Param("stationCode") String stationCode,
            @Param("levels") List<AlarmLevel> levels);
}
