package com.kmks.w2.netty.decoder;
import com.kmks.w2.utils.TcpUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class MessageDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        // 可能会涉及到消息的分隔符或者固定长度的处理，这里简单地假设消息是字符串
        if (in.readableBytes() > 0) {
            int length = in.readableBytes();
            byte[] bytes = new byte[length];
            in.readBytes(bytes);
            String message = new String(bytes, Charset.forName("GBK"));
            // 判断结尾是否时回车，解决半包问题
            if (!message.endsWith("\r\n")) {
                in.resetReaderIndex();
                return ;
            };
            // 将解码后的消息添加到输出列表中
            out.add(message);

        }
    }
}

