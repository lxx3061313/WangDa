package com.wangda.alarm.provider.controller;

import com.wangda.alarm.provider.bean.QueryHisAlarmReq;
import com.wangda.alarm.provider.bean.SegmentAlarmStaticsVo;
import com.wangda.alarm.provider.biz.AlarmInfoBiz;
import com.wangda.alarm.provider.biz.QueryAlarmBiz;
import com.wangda.alarm.service.bean.biz.MsgPushContext;
import com.wangda.alarm.service.bean.biz.UserRoleMappingInfo;
import com.wangda.alarm.service.common.appmsg.GeTuiPusher;
import com.wangda.alarm.service.common.springconfig.annotation.JsonBody;
import com.wangda.alarm.service.common.util.DateFormatUtil;
import com.wangda.alarm.service.common.util.SplitterUtil;
import com.wangda.alarm.service.common.util.mail.MailSender;
import com.wangda.alarm.service.dao.UserInfoDao;
import com.wangda.alarm.service.dao.po.UserInfoPo;
import com.wangda.alarm.service.impl.UserCidMappingService;
import com.wangda.alarm.service.impl.UserInfoService;
import com.wangda.alarm.service.impl.UserRoleMappingService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhangxin
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

    @Resource
    MailSender mailSender;

    @Resource
    GeTuiPusher geTuiPusher;

    @Resource
    AlarmInfoBiz alarmInfoBiz;


    @Resource
    UserCidMappingService userCidMappingService;

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
    public void queryAlarmFromPt(@RequestBody QueryHisAlarmReq req) {
        queryAlarmBiz.queryAlarm(req.getCode(), req.getFrom(), req.getTo());
    }

    @RequestMapping("/mail")
    @JsonBody
    public void testMail() {
        try {
            mailSender.sendMail("lxx3061313@163.com", "我要发邮件", "密码修改成功");
        } catch (MessagingException e) {
            logger.error("mail send error", e);
        }
    }


    @RequestMapping("/getui")
    @JsonBody
    public void testGeTui() {
        MsgPushContext context = new MsgPushContext();
        context.setTitle("我是推送消息的标题");
        context.setContent("我是推送消息的内容");
        context.setUsername("lxx");

        String cid = userCidMappingService.queryCidByAccount("lxx");
        context.setCids(Arrays.asList(cid));
        geTuiPusher.push(context);
    }

    @RequestMapping("/segmentStatis")
    @JsonBody
    public SegmentAlarmStaticsVo querySegmentStatistics(String segmentCode) {
        return alarmInfoBiz.querySegmentStatic(segmentCode);
    }
}
