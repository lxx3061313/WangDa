package com.wangda.alarm.service.common.tcplayer.common;

import com.wangda.alarm.service.bean.standard.protocol.ProtocalFieldsDesc;
import com.wangda.alarm.service.common.util.ByteBufferUtil;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.function.Consumer;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.apache.mina.filter.codec.demux.MessageDecoder;
import org.apache.mina.filter.codec.demux.MessageDecoderResult;

/**
 * @author lixiaoxiong
 * @version 2017-10-25
 */
public abstract class WangDaContextDecoder<T> implements MessageDecoder {

    protected CharsetDecoder cd = Charset.forName("GBK").newDecoder();
    private final AttributeKey CONTEXT = new AttributeKey(getClass(), "conext");

    @Override
    public MessageDecoderResult decodable(IoSession session, IoBuffer in) {
        SessionRegCenter.reg(session);

        MessageDecoderResult decoderResult = intervalDecodeable(session, in);
        if (decoderResult == MessageDecoderResult.NOT_OK) {
            return decoderResult;
        } else {
            BufferContext context = getContext(session, in);
            IoBuffer buffer = context.getIoBuffer();
            int index = context.getPosition();
            in.position(index);
            while (in.hasRemaining()) {
                byte b = in.get();
                buffer.put(b);
                ++index;
            }
            if (index < context.getByteCount()) {
                context.setPosition(index);
                context.setEnough(false);
                return MessageDecoderResult.NEED_DATA;
            }

            buffer.flip();
            return decoderResult;
        }
    }

    @Override
    public MessageDecoderResult decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out)
            throws Exception {
        BufferContext context = getContext(session, in);
        return decodeData(session, context.getIoBuffer(), t -> {
            out.write(t);

            // in的位置设置为读完的状态, 即position = limit
            byte flag = ByteBufferUtil.extractByte(in, ProtocalFieldsDesc.PROTOCOL_DATA_TYPE);
            if (flag != 0x0F) {
                in.position(context.getIoBuffer().limit());
                context.reset();
                session.removeAttribute(CONTEXT);
            } else {
                in.position(in.limit());
            }
        });
    }

    @Override
    public void finishDecode(IoSession session, ProtocolDecoderOutput out) throws Exception {

    }

    public abstract MessageDecoderResult decodeData(IoSession session, IoBuffer in, Consumer<T> callback);
    public abstract MessageDecoderResult intervalDecodeable(IoSession session, IoBuffer in);


    private BufferContext getContext(IoSession session, IoBuffer in) {
        int position = in.position();
        BufferContext attribute = (BufferContext) session.getAttribute(CONTEXT);
        if (attribute == null) {
            byte[] forward = ByteBufferUtil
                    .forward(in, ProtocalFieldsDesc.PROTOCOL_DATA_LENGTH.getByteLth());
            attribute = new BufferContext();
            attribute.setPosition(0);
            attribute.setByteCount(ByteBufferUtil.bytesToInt(forward, 0) + 5);
            session.setAttribute(CONTEXT, attribute);
            in.position(position);
        }
        return attribute;
    }

    public class BufferContext {
        private final IoBuffer ioBuffer;
        private int byteCount;
        private int position;
        private boolean enough;

        public BufferContext() {
            ioBuffer = IoBuffer.allocate(200).setAutoExpand(true);
        }

        public IoBuffer getIoBuffer() {
            return ioBuffer;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public void reset() {
            this.ioBuffer.clear();
            this.position=0;
        }

        public int getByteCount() {
            return byteCount;
        }

        public void setByteCount(int byteCount) {
            this.byteCount = byteCount;
        }

        public boolean isEnough() {
            return enough;
        }

        public void setEnough(boolean enough) {
            this.enough = enough;
        }
    }
}
