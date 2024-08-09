package com.kmks.w2.netty.decoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageEncoder extends MessageToByteEncoder<byte[]> {

    @Override
    protected void encode(ChannelHandlerContext ctx, byte[] msg, ByteBuf out) {
        // 可能会涉及到消息的分隔符或者固定长度的处理，
//        log.info("发送消息：{}",msg);
        out.writeBytes(Unpooled.copiedBuffer(msg));
    }
}

