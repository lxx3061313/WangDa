package com.wangda.alarm.service.impl;

import com.google.common.base.Preconditions;
import com.wangda.alarm.service.bean.biz.BizOpLog;
import com.wangda.alarm.service.bean.biz.OpLogType;
import com.wangda.alarm.service.bean.biz.UserSession;
import com.wangda.alarm.service.bean.biz.UserSession.Builder;
import com.wangda.alarm.service.dao.BizOpLogDao;
import com.wangda.alarm.service.dao.po.BizOpLogPo;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-11-04
 */
@Service
public class OpLogService extends AbstractBizOpLogService<BizOpLog>{

    @Resource
    BizOpLogDao bizOpLogDao;

    public void createLoginLog(String userName) {
        BizOpLog opLog = logBuilder().addBizId("0")
                .addOperator(userName)
                .addOpDesc("登录app")
                .addCreateTime(new Date())
                .addOpLogLabel(OpLogType.LOG_IN_OUT)
                .generate();
        saveLog(opLog);
    }

    public void createLogoutLog(String userName) {
        BizOpLog opLog = logBuilder().addBizId("0")
                .addOperator(userName)
                .addOpLogLabel(OpLogType.LOG_IN_OUT)
                .addCreateTime(new Date())
                .addOpDesc("退出app")
                .generate();
        saveLog(opLog);
    }

    public void createWatchInfoLog(String userName, String context) {
        BizOpLog opLog = logBuilder().addBizId("0")
                .addOperator(userName)
                .addOpLogLabel(OpLogType.WATCH_INFO)
                .addCreateTime(new Date())
                .addOpDesc(context)
                .generate();
        saveLog(opLog);
    }

    public void createChangePassword(String userName) {
        BizOpLog opLog = logBuilder().addBizId("0")
                .addOperator(userName)
                .addOpLogLabel(OpLogType.OPERATE_PASSWORD)
                .addCreateTime(new Date())
                .addOpDesc("修改密码")
                .generate();
        saveLog(opLog);
    }

    public static Builder logBuilder() {
        return new Builder();
    }

    @Override
    BizOpLogPo adapt(BizOpLog bizOpLog) {
        BizOpLogPo po = new BizOpLogPo();
        po.setBizId(bizOpLog.getBizId());
        po.setOperator(bizOpLog.getOperator());
        po.setOpLabel(bizOpLog.getOpLabel().name());
        po.setCreateTime(bizOpLog.getCreateTime());
        return po;
    }

    static class Builder {
        private String bizId;
        private String operator;
        private String opDesc;
        private Date createTime;
        private OpLogType logType;

        private Builder addOperator(String operator) {
            this.operator = operator;
            return this;
        }

        private Builder addOpLogLabel(OpLogType type) {
            this.logType = type;
            return this;
        }

        private Builder addOpDesc(String desc) {
            this.opDesc = desc;
            return this;
        }

        private Builder addCreateTime(Date time) {
            this.createTime = time;
            return this;
        }

        private Builder addBizId(String id) {
            this.bizId = id;
            return this;
        }

        public BizOpLog generate() {
            Preconditions.checkNotNull(operator, "日志操作人不能为空");
            Preconditions.checkNotNull(opDesc, "日志描述不能为空");
            Preconditions.checkNotNull(createTime, "日志创建时间不能为空");
            BizOpLog opLog = new BizOpLog();
            opLog.setBizId(this.bizId);
            opLog.setOpDesc(this.opDesc);
            opLog.setOperator(this.operator);
            opLog.setCreateTime(this.createTime);
            return opLog;
        }
    }
}
