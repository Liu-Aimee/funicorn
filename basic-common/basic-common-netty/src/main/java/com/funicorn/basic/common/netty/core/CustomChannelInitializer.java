package com.funicorn.basic.common.netty.core;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * @author Aimee
 * @since 2021/4/9 14:21
 */
public class CustomChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final BaseHandler<?> baseHandler;

    public CustomChannelInitializer(BaseHandler<?> baseHandler){
        this.baseHandler = baseHandler;
    }

    /**
     * 自定义通道设置
     * 1、出站解码器
     * 2、入站解码器
     * 3、指定自定义处理器
     * @param socketChannel 通道
     * */
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception{
        SocketChannel socketChannel1 = baseHandler.initChannel(socketChannel);
        socketChannel1.pipeline().addLast(baseHandler);
    }
}
