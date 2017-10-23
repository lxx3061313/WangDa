package com.wangda.alarm.service.tcplayer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import javax.annotation.PostConstruct;

/**
 * @author lixiaoxiong
 * @version 2017-10-22
 */
public class TcpServerSelector {

    @PostConstruct
    void init() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(8818));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        TcpProtocol protocol = new TcpProtocaoImpl(256);
        while (true) {
            if (selector.select(3000) == 0) {
                System.out.println(".");
                continue;
            }

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (next.isAcceptable()) {
                    protocol.handleAccept(next);
                }

                if (next.isReadable()) {
                    protocol.handleRead(next);
                }

                if (next.isWritable()) {
                    protocol.handleWrite(next);
                }

                iterator.remove();
            }
        }
    }
}
