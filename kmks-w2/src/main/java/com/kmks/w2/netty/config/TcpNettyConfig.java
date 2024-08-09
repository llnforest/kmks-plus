package com.kmks.w2.netty.config;


import com.kmks.w2.netty.decoder.MessageDecoder;
import com.kmks.w2.netty.decoder.MessageEncoder;
import com.kmks.w2.netty.handler.TcpServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author Star
 * @description: TODO
 * @date 2023/12/7 10:29
 */
@Slf4j
@Configuration
@ConditionalOnProperty(value = "netty.tcp.enabled", havingValue = "true")
public class TcpNettyConfig implements CommandLineRunner {
    @Resource
    private TcpServerHandler tcpServerHandler;

    @Value("${netty.tcp.port}")
    private int tcpServerPort;
    private NioEventLoopGroup bossGroup = new NioEventLoopGroup();
    private NioEventLoopGroup workGroup = new NioEventLoopGroup();


    public ServerBootstrap tcpServerBootstrap(ChannelInitializer<SocketChannel> channelInitializer) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup,workGroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(channelInitializer)
            .option(ChannelOption.SO_BACKLOG, 128)
            .childOption(ChannelOption.SO_KEEPALIVE, true);
        return serverBootstrap;
    }

    public ChannelInitializer<SocketChannel> channelInitializer() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
                ChannelPipeline pipeline = ch.pipeline();
                // 接收时decode
                pipeline.addLast(new MessageDecoder());
                // 发送时encode
                pipeline.addLast(new MessageEncoder());
                pipeline.addLast(tcpServerHandler);
            }
        };
    }

    public void startInternal() {
        try {
            ChannelFuture future = tcpServerBootstrap(channelInitializer())
                .bind(tcpServerPort)
                .sync();
            log.info("===tcp启动成功, port={}===", tcpServerPort);
            future.channel().closeFuture().await();
        } catch (Exception e) {
            log.warn("===tcp出现异常, 原因：{}===", e.getMessage());
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        } finally {
            log.info("tcp服务启动结束");
        }
    }

    @Override
    public void run(String... args) {
        startInternal();
    }

}
