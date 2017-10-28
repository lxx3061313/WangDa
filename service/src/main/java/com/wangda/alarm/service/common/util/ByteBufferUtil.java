package com.wangda.alarm.service.common.util;

import com.wangda.alarm.service.bean.standard.protocol.ProtocalFieldsDesc;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CharsetDecoder;
import java.util.Date;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.mina.core.buffer.IoBuffer;

/**
 * @author lixiaoxiong
 * @version 2017-10-23
 */
public class ByteBufferUtil {
    public static ThreadLocal<Pair<Integer, Integer>> bufferContext = new ThreadLocal<>();

    public static void storeCtx(Pair<Integer, Integer> pair) {
        bufferContext.set(pair);
    }

    public static Pair<Integer, Integer> getCtx() {
        return bufferContext.get();
    }

    public static void storeCtx(IoBuffer buffer) {
        storeCtx(Pair.of(buffer.position(), buffer.limit()));
    }

    public static void recoverCtx(IoBuffer buffer) {
        Pair<Integer, Integer> ctx = getCtx();
        buffer.position(ctx.getLeft());
        buffer.limit(ctx.getRight());
    }

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
     * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序。
     * @param value
     *            要转换的int值
     * @return byte数组
     */
    public static byte[] intToBytes(int value)
    {
        byte[] byte_src = new byte[4];
        byte_src[3] = (byte) ((value & 0xFF000000)>>24);
        byte_src[2] = (byte) ((value & 0x00FF0000)>>16);
        byte_src[1] = (byte) ((value & 0x0000FF00)>>8);
        byte_src[0] = (byte) ((value & 0x000000FF));
        return byte_src;
    }

    /**
     * 低位在前,高位在后
     * @param value
     * @return
     */
    public static byte [] shortToBytes(short value) {
        byte[] byte_src = new byte[2];
        byte_src[1] = (byte) ((value & 0xFF00)>>8);
        byte_src[0] = (byte) ((value & 0x00FF));
        return byte_src;
    }

    public static byte[] stringToBytes(String str, int count) {
        char[] chars = str.toCharArray();
        byte[] result = new byte[count];
        int index = 0;
        for (char c : chars) {
            if (index >= count) {
                break;
            }
            result[index] = (byte) c;
            index++;
        }
        return result;
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

    public static short byteToShort(IoBuffer buffer) {
        byte []temp = new byte[2];
        buffer.get(temp);
        return bytesToShort(temp, 0);
    }

    public static short byteToShort(byte[] ary) {
        return bytesToShort(ary, 0);
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

    public static byte[] forward(IoBuffer buffer, int steps) {
        int position = buffer.position();

        IoBuffer result = IoBuffer.allocate(steps);
        for (int i=0;i<steps;++i) {
            result.put(buffer.get());
        }
        return result.array();
    }

    public static byte forwardOneStep(IoBuffer buffer) {
        return buffer.get();
    }

    public static byte extractByte(IoBuffer buffer, ProtocalFieldsDesc position) {
        try {
            storeCtx(buffer);
            return extractByte(buffer, position.getPosition(), position.getLimit());
        } finally {
            recoverCtx(buffer);
        }

    }

    public static byte extractByte(IoBuffer buffer, int position, int limit) {
        try {
            storeCtx(buffer);
            buffer.position(position);
            buffer.limit(limit);
            return buffer.get();
        } finally {
            recoverCtx(buffer);
        }

    }

    public static short extractShort(IoBuffer buffer, ProtocalFieldsDesc position) {
        try {
            storeCtx(buffer);
            buffer.position(position.getPosition());
            buffer.limit(position.getLimit());
            return byteToShort(buffer);
        } finally {
            recoverCtx(buffer);
        }
    }

    public static Date extractDate(IoBuffer buffer, int postion, int limit) {
        try {
            storeCtx(buffer);
            buffer.position(postion);
            buffer.limit(limit);
            return byteToDate(buffer);
        } finally {
            recoverCtx(buffer);
        }

    }

    public static String extractString(IoBuffer buffer, int postion, int limit, CharsetDecoder cd)
            throws CharacterCodingException {
        try {
            storeCtx(buffer);
            buffer.position(postion);
            buffer.limit(limit);
            return buffer.getString(cd);
        } finally {
            recoverCtx(buffer);
        }

    }

    public static String bytesToString(byte[] bytes, CharsetDecoder cd)
            throws CharacterCodingException {
        IoBuffer buffer = IoBuffer.allocate(bytes.length).setAutoExpand(true);
        for (byte b : bytes) {
            buffer.put(b);
        }
        return buffer.getString(cd);
    }

    public static String extractString(IoBuffer buffer, ProtocalFieldsDesc position, CharsetDecoder cd)
            throws CharacterCodingException {
        try {
            storeCtx(buffer);
            return extractString(buffer, position.getPosition(), position.getLimit(), cd);
        } finally {
            recoverCtx(buffer);
        }

    }

    public static byte[] extractByteArray(IoBuffer buffer, int postion, int limit, int count) {
        buffer.limit(limit);
        buffer.position(postion);
        byte [] temp = new byte[count];
        buffer.get(temp);
        return temp;
    }
    /**
     * 四个字节标识, 精确到秒
     * @param buffer
     * @return
     */
    public static Date byteToDate(IoBuffer buffer) {
        try {
            storeCtx(buffer);
            byte [] ntime = new byte[4];
            buffer.get(ntime);
            int ptimestamp = ByteBufferUtil.bytesToInt(ntime, 0);
            return new Date(ptimestamp * 1000L);
        } finally {
            recoverCtx(buffer);
        }
    }

    public static Date byteToDate(byte [] bytes) {
        int ptimestamp = ByteBufferUtil.bytesToInt(bytes, 0);
        return new Date(ptimestamp * 1000L);
    }

    /**
     * Date转字节数组, 低位在前,高位在后
     * @param date
     * @return
     */
    public static byte [] dateToBytes(Date date) {
        long timestamp = date.getTime() / 1000;
        byte[] bytes = intToBytes((int) timestamp);
        return bytes;
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
