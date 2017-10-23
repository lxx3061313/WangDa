package com.wangda.alarm.service.tcplayer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * @author lixiaoxiong
 * @version 2017-10-22
 */
@Service
public class TcpServerSelector implements ApplicationListener<ContextRefreshedEvent> {
    private final static Logger logger = LoggerFactory.getLogger(TcpServerSelector.class);

    private Selector selector;

    @PostConstruct
    void init() throws IOException {

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() != null) {
            return;
        }
        try {
            selector = Selector.open();
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(8818));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            TcpProtocol protocol = new TcpProtocaoImpl(256);
//            while (true) {
//                if (selector.select(3000) == 0) {
//                    System.out.println(".");
//                    continue;
//                }
//
//                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
//                while (iterator.hasNext()) {
//                    SelectionKey next = iterator.next();
//                    if (next.isAcceptable()) {
//                        protocol.handleAccept(next);
//                    }
//
//                    if (next.isReadable()) {
//                        protocol.handleRead(next);
//                    }
//
//                    if (next.isWritable()) {
//                        protocol.handleWrite(next);
//                    }
//
//                    iterator.remove();
//                }
//            }
        } catch (IOException e) {
            logger.error("tcp server start error", e);
        }
    }
}
