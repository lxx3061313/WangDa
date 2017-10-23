package com.wangda.alarm.service.tcplayer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @author lixiaoxiong
 * @version 2017-10-22
 */
public class TcpProtocaoImpl implements TcpProtocol {
    private int bufSize;

    public TcpProtocaoImpl(int bufSize) {
        this.bufSize = bufSize;
    }

    public void handleAccept(SelectionKey key) throws IOException {
        SocketChannel sc = ((ServerSocketChannel) key.channel()).accept();
        sc.configureBlocking(false);
        sc.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocate(bufSize));
    }

    public void handleRead(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
        int read = sc.read(byteBuffer);
        if (read == -1) {
            sc.close();
        } else if(read > 0) {
            key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        }
    }

    public void handleWrite(SelectionKey key) throws IOException{
        ByteBuffer byteBuffer = (ByteBuffer) key.attachment();
        byteBuffer.flip();
        SocketChannel sc = (SocketChannel) key.channel();
        sc.write(byteBuffer);
        if ((! byteBuffer.hasRemaining()) ) {
            key.interestOps(SelectionKey.OP_READ);
        }
        byteBuffer.compact();
    }
}
