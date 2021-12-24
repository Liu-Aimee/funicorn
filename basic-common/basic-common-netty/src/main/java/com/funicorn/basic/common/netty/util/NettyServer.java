package com.funicorn.basic.common.netty.util;

import com.funicorn.basic.common.netty.core.BaseHandler;
import com.funicorn.basic.common.netty.core.CustomChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Aimee
 * @since 2021/4/9
 */
@Slf4j
public class NettyServer {

	/**
	 * 启动netty服务
	 * @param port 端口
	 * @param channelInitializer 自定义解析器
	 * @throws InterruptedException 线程异常
	 * */
	public static void start(int port, CustomChannelInitializer channelInitializer) throws InterruptedException {
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(boss, worker);
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.option(ChannelOption.SO_BACKLOG, 128);
		bootstrap.option(ChannelOption.TCP_NODELAY, true);
		bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.childHandler(channelInitializer);
		ChannelFuture f = bootstrap.bind(port).sync();
		if (f.isSuccess()) {
			log.info("==========>[ 端口[{}]驱动服务 ] 启动成功<==========",port);
		}
	}

	/**
	 * 启动netty服务
	 * @param port 端口
	 * @param <T> 解析类型
	 * @param baseHandler 自定义处理器
	 * @throws InterruptedException 线程异常
	 * */
	public static <T> void start(int port, BaseHandler<T> baseHandler) throws InterruptedException {
		EventLoopGroup boss = new NioEventLoopGroup();
		EventLoopGroup worker = new NioEventLoopGroup();
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(boss, worker);
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.option(ChannelOption.SO_BACKLOG, 128);
		bootstrap.option(ChannelOption.TCP_NODELAY, true);
		bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.childHandler(new CustomChannelInitializer(baseHandler));
		ChannelFuture f = bootstrap.bind(port).sync();
		if (f.isSuccess()) {
			log.info("==========>[ 端口[{}]驱动服务 ] 启动成功<==========",port);
		}
	}
}
