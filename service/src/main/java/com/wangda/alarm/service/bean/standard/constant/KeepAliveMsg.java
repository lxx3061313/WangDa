package com.wangda.alarm.service.bean.standard.constant;

import org.apache.mina.core.buffer.IoBuffer;

/**
 * @author lixiaoxiong
 * @version 2017-10-25
 */
public class KeepAliveMsg {
    public static IoBuffer buffer = IoBuffer.allocate(16);

    static {
        buffer.put((byte) 11);
        buffer.put((byte) 0);
        buffer.put((byte) 0);
        buffer.put((byte) 0);
        buffer.put((byte) 15);
        buffer.put((byte) 255);
        buffer.put((byte) 255);
        buffer.put((byte) 255);
        buffer.put((byte) 255);
        buffer.put((byte) 255);
        buffer.put((byte) 255);
        buffer.put((byte) 255);
        buffer.put((byte) 255);
        buffer.put((byte) 255);
        buffer.put((byte) 255);
        buffer.put((byte) 255);
    }
    public static byte[] msg() {
        return buffer.array();
    }

    public static boolean isKeepAliveMsg(byte [] msg) {
        if (msg.length != 16) {
            return false;
        }

        byte[] standard = msg();
        for (int i=0; i<16; ++i) {
            if (standard[i] != msg[i]) {
                return false;
            }
        }

        return true;
    }
}
