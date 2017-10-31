package com.wangda.alarm.service.common.appmsg;

import com.wangda.alarm.service.bean.biz.MsgPushContext;

/**
 * @author lixiaoxiong
 * @version 2017-11-01
 */
public interface MsgPusher {
    void onSucc(MsgPushContext context);
    void push(MsgPushContext context);
    void onFail(MsgPushContext context, Exception e);
}
