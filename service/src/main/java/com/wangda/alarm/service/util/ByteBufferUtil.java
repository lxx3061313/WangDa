package com.wangda.alarm.service.util;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Stack;
import org.apache.mina.core.buffer.IoBuffer;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
public class ByteBufferUtil {

    /**
     * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序
     * @param ary
     * @param offset
     * @return
     */
    public static int bytesToInt(byte[] ary, int offset) {
        int value;
        value = (int) ((ary[offset]&0xFF)
                | ((ary[offset+1]<<8) & 0xFF00)
                | ((ary[offset+2]<<16)& 0xFF0000)
                | ((ary[offset+3]<<24) & 0xFF000000));
        return value;
    }

    /**
     * 低位在前，高位在后
     * @param ary 字节数组
     * @param offset 偏移量
     * @return 结果
     */
    public static short bytesToShort(byte[] ary, int offset) {
        short value;
        value = (short) ((ary[offset]&0xFF)
                | ((ary[offset+1]<<8) & 0xFF00));
        return value;
    }

    public static int byteToInt(byte b) {
        return b & 0xFF;
    }

    public static int IobufferToInt(IoBuffer buffer) {
        byte [] temp = new byte[4];
        buffer.get(temp);
        int i = bytesToInt(temp, 0);
        return i;
    }

    /**
     * 四个字节标识, 精确到秒
     * @param buffer
     * @return
     */
    public static Date byteToDate(IoBuffer buffer) {
        byte [] ntime = new byte[4];
        buffer.get(ntime);
        int ptimestamp = ByteBufferUtil.bytesToInt(ntime, 0);
        return new Date(ptimestamp * 1000L);
    }

    /**
     * 16进制转字节数组
     * @param hex 16进制字符串
     * @return 5F->95
     */
    public static byte[] hexString2Bytes(String hex) {

        if ((hex == null) || (hex.equals(""))) {
            return null;
        } else if (hex.length() % 2 != 0) {
            return null;
        } else {
            hex = hex.toUpperCase();
            int len = hex.length() / 2;
            byte[] b = new byte[len];
            char[] hc = hex.toCharArray();
            for (int i = 0; i < len; i++) {
                int p = 2 * i;
                b[i] = (byte) (charToByte(hc[p]) << 4 | charToByte(hc[p + 1]));
            }
            return b;
        }

    }
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

}
