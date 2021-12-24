package com.funicorn.basic.common.netty.core;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Aimee
 * @since 2021/10/19
 */
public class NettyDataEncoder extends ChannelOutboundHandlerAdapter {

	@Override
	public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws IOException {
		ByteBufOutputStream byteBufOutputStream = null;
		ByteArrayOutputStream byteArrayOutputStream = null;
		try {
			if (msg instanceof byte[]) {
				byte[] bytes = (byte[]) msg;
				ByteBuf out = ctx.alloc().buffer(bytes.length);
				byteBufOutputStream = new ByteBufOutputStream(out);
				byteArrayOutputStream = new ByteArrayOutputStream(bytes.length);
				byteArrayOutputStream.write(bytes);
				byteArrayOutputStream.writeTo(byteBufOutputStream);
				byteArrayOutputStream.flush();
				byteBufOutputStream.flush();
				ctx.write(out, promise);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (byteArrayOutputStream != null) {
				try {
					byteArrayOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (byteBufOutputStream!=null){
				try {
					byteBufOutputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
