package com.funicorn.basic.common.netty.core;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * @author Aimee
 * @since 2021/4/7 16:24
 *
 * 此抽象类可定义通用的逻辑
 */
@Slf4j
public abstract class BaseHandler<T> extends SimpleChannelInboundHandler<T> {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx,msg);
    }

    /**
     * 自定义通道设置
     * 1、出站解码器
     * 2、入站解码器
     * 3、指定自定义处理器
     * @param socketChannel 通道
     * @throws Exception 异常
     * @return SocketChannel
     * */
    public abstract SocketChannel initChannel(SocketChannel socketChannel) throws Exception;

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        log.warn("IP:({})客户端主动断线!",inetSocketAddress.getAddress().getHostAddress());
        log.info("移除客户端:({})",inetSocketAddress.getAddress().getHostAddress());
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        log.info("IP:({})客户端IP地址与服务端建立连接成功!", inetSocketAddress.getAddress().getHostAddress());
        super.channelActive(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case READER_IDLE:
                    handleReaderIdle(ctx);
                    break;
                case WRITER_IDLE:
                    handleWriterIdle(ctx);
                    break;
                case ALL_IDLE:
                    handleAllIdle(ctx);
                    break;
                default:
                    break;
            }
        }
    }
    protected void handleReaderIdle(ChannelHandlerContext ctx) {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        log.warn("channel:({})--ip:({})客户端预设时间内未发生读操作，请检查!",ctx.channel().id().toString(), inetSocketAddress.getAddress().getHostAddress());
    }

    protected void handleWriterIdle(ChannelHandlerContext ctx) {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        log.warn("channel:({})--ip:({})客户端预设时间内未发生写操作，请检查!",ctx.channel().id().toString(), inetSocketAddress.getAddress().getHostAddress());
    }

    protected void handleAllIdle(ChannelHandlerContext ctx) {
        InetSocketAddress inetSocketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        log.warn("channel:({})--ip:({})读写超时,请检查!",ctx.channel().id().toString(), inetSocketAddress.getAddress().getHostAddress());
    }
}
