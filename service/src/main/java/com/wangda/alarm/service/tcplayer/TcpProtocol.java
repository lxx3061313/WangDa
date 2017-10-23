package com.wangda.alarm.service.tcplayer;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * @author lixiaoxiong
 * @version 2017-10-22
 */
public interface TcpProtocol {
    void handleAccept(SelectionKey key) throws IOException;
    void handleRead(SelectionKey key) throws IOException;
    void handleWrite(SelectionKey key) throws IOException;
}
