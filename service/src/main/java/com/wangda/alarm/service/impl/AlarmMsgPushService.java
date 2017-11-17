package com.wangda.alarm.service.impl;

import com.wangda.alarm.service.bean.biz.AlarmInfo;
import com.wangda.alarm.service.bean.biz.DeptHierarchyInfo;
import com.wangda.alarm.service.bean.biz.MsgPushContext;
import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmLevel;
import com.wangda.alarm.service.common.appmsg.GeTuiPusher;
import com.wangda.alarm.service.common.util.json.JsonUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-11-05
 */
@Service
public class AlarmMsgPushService {

    private final static Logger logger = LoggerFactory.getLogger(AlarmMsgPushService.class);
    private final static Integer SECION_LEVEL_DEPT_ID = 24;
    @Resource
    UserInfoService userInfoService;

    @Resource
    UserCidMappingService userCidMappingService;

    @Resource
    GeTuiPusher geTuiPusher;

    public void push(DeptHierarchyInfo deptHierarchyInfo, AlarmInfo info) {
        List<Integer> deptIds = deptIdsForPush(deptHierarchyInfo, info);
        if (CollectionUtils.isEmpty(deptIds)) {
            return;
        }
        List<String> accounts = userInfoService.queryAccountsByDeptIds(deptIds);

        if (CollectionUtils.isEmpty(accounts)) {
            logger.warn("找不到报警应该推送的用户消息", JsonUtil.of(info));
            return;
        }
        List<String> cids = userCidMappingService.queryCidsByAccounts(accounts);

        MsgPushContext context = new MsgPushContext();
        context.setCids(cids);
        context.setTitle(alarmHeader(info));
        context.setContent(alarmContext(info));
        geTuiPusher.push(context);
    }

    private String alarmHeader(AlarmInfo info) {
        StringBuilder builder = new StringBuilder();
        builder.append(info.getStationName()).append("设备发生报警");
        return builder.toString();
    }

    private String alarmStation(AlarmInfo info) {
        StringBuilder builder = new StringBuilder();
        builder.append(info.getSegmentName()).append("->")
                .append(info.getWorkshopName()).append("->")
                .append(info.getWorkAreaName()).append("->")
                .append(info.getStationName());
        return builder.toString();
    }

    private String alarmContext(AlarmInfo info) {
        StringBuilder builder = new StringBuilder();
        builder.append("报警车站:").append(alarmStation(info)).append("\n")
                .append("  报警设备:").append(info.getDeviceName()).append("\n")
                .append("  报警级别:").append(info.getAlarmLevel().getDesc()).append("\n")
                .append("  报警内容:").append(info.getAlarmContext());
        return builder.toString();
    }

    private List<Integer> deptIdsForPush(DeptHierarchyInfo deptHierarchyInfo, AlarmInfo info) {
        List<Integer> result = new ArrayList<>();
        result.add(deptHierarchyInfo.getSectionId());

        if (info.getAlarmLevel() == AlarmLevel.LEVEL_ONE) {
            result.add(SECION_LEVEL_DEPT_ID);
            result.add(deptHierarchyInfo.getSegmentId());
            result.add(deptHierarchyInfo.getWorkShopId());
            result.add(deptHierarchyInfo.getWorkAreaId());
            result.add(deptHierarchyInfo.getStationId());
            return result;
        }
        if (info.getAlarmLevel() == AlarmLevel.LEVEL_TWO) {
            result.add(deptHierarchyInfo.getSegmentId());
            result.add(deptHierarchyInfo.getWorkShopId());
            result.add(deptHierarchyInfo.getWorkAreaId());
            result.add(deptHierarchyInfo.getStationId());
            return result;
        }

        if (info.getAlarmLevel() == AlarmLevel.LEVEL_THREE) {
            result.add(deptHierarchyInfo.getWorkShopId());
            result.add(deptHierarchyInfo.getWorkAreaId());
            result.add(deptHierarchyInfo.getStationId());
            return result;
        }

        if (info.getAlarmLevel() == AlarmLevel.WARN) {
            result.add(deptHierarchyInfo.getWorkAreaId());
            result.add(deptHierarchyInfo.getStationId());
            return result;
        }

        return result;
    }
}
