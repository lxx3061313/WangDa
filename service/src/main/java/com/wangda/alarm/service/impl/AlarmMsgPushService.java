package com.wangda.alarm.service.impl;

import com.wangda.alarm.service.bean.biz.AlarmInfo;
import com.wangda.alarm.service.bean.biz.DeptHierarchyInfo;
import com.wangda.alarm.service.bean.standard.alarminfo.alarm.AlarmLevel;
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

    @Resource
    UserInfoService userInfoService;

    @Resource
    UserCidMappingService userCidMappingService;

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

        //todo 发送消息
    }

    private List<Integer> deptIdsForPush(DeptHierarchyInfo deptHierarchyInfo, AlarmInfo info) {
        List<Integer> result = new ArrayList<>();
        result.add(deptHierarchyInfo.getSectionId());

        if (info.getAlarmLevel() == AlarmLevel.LEVEL_TWO) {
            result.add(deptHierarchyInfo.getSegmentId());
            return result;
        }

        if (info.getAlarmLevel() == AlarmLevel.LEVEL_THREE) {
            result.add(deptHierarchyInfo.getSegmentId());
            result.add(deptHierarchyInfo.getWorkShopId());
            return result;
        }

        if (info.getAlarmLevel() == AlarmLevel.WARN) {
            result.add(deptHierarchyInfo.getSegmentId());
            result.add(deptHierarchyInfo.getWorkShopId());
            result.add(deptHierarchyInfo.getWorkAreaId());
            result.add(deptHierarchyInfo.getStationId());
            return result;
        }

        return result;
    }
}
