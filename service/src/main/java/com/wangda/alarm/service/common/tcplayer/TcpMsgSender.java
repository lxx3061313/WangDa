package com.wangda.alarm.service.common.tcplayer;

/**
 * @author wangshuo
 * @version 2017-10-28
 */
public interface TcpMsgSender<T> {
    void send(T msg);
}
