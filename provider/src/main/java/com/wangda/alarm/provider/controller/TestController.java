package com.wangda.alarm.provider.controller;

import com.wangda.alarm.provider.biz.QueryAlarmBiz;
import com.wangda.alarm.service.bean.biz.UserRoleMappingInfo;
import com.wangda.alarm.service.common.springconfig.annotation.JsonBody;
import com.wangda.alarm.service.common.util.DateFormatUtil;
import com.wangda.alarm.service.common.util.SplitterUtil;
import com.wangda.alarm.service.dao.UserInfoDao;
import com.wangda.alarm.service.dao.po.UserInfoPo;
import com.wangda.alarm.service.impl.UserInfoService;
import com.wangda.alarm.service.impl.UserRoleMappingService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
@Controller
@RequestMapping("/door/")
public class TestController {
    private final static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Resource
    UserRoleMappingService userRoleMappingService;

    @Resource
    UserInfoDao userInfoDao;

    @Resource
    QueryAlarmBiz queryAlarmBiz;


    @RequestMapping("/initMapping")
    @JsonBody
    public String initMapping() {
        List<UserInfoPo> infoPos = userInfoDao.queryAllUsers();
        List<UserRoleMappingInfo> infos = new ArrayList<>();
        for (UserInfoPo po : infoPos) {
            List<Integer> roleIds = SplitterUtil.splitByCommaToIntList(po.getRoleId());
            for (Integer roleId : roleIds) {
                UserRoleMappingInfo info = new UserRoleMappingInfo();
                info.setUserId(po.getId());
                info.setRoleId(roleId);
                info.setCreateTime(new Date());
                infos.add(info);
            }
        }
        userRoleMappingService.saveUserRoleMappings(infos);
        return "succ";
    }

    @RequestMapping("/queryHisData")
    @JsonBody
    public void queryAlarmFromPt(String code, Date from, Date to) {
        queryAlarmBiz.queryAlarm(code, from, to);
    }
}
