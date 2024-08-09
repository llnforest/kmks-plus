package com.kmks.w2.netty.handler;

/**
 * @author Star
 * @description: TODO
 * @date 2023/12/6 11:12
 */

import com.kmks.w2.netty.decoder.TcpMessageDecoder;
import com.kmks.w2.utils.TcpUtils;
import com.ruoyi.common.utils.LogUtils;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@ChannelHandler.Sharable
@Component
public class TcpServerHandler extends SimpleChannelInboundHandler<String> {

    @Resource
    private TcpMessageDecoder tcpMessageDecoder;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        try {
            this.tcpMessageDecoder.handleMessage(msg, ctx);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 客户端连接成功时触发
        LogUtils.tcp("Client connected: {}",ctx.channel().remoteAddress());

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        // 客户端断开连接时触发
        LogUtils.tcp("Client disconnected: {}",ctx.channel().remoteAddress());
        TcpUtils.removeCarChannel(ctx.channel());
    }



}

