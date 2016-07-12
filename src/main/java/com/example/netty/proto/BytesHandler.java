package com.example.netty.proto;

import java.util.Arrays;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class BytesHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte[] bytes = {31, 2, 8, 2, 18, 4, 0, 10, 20, 30, 26, 20, 77, 69, 68, 86, 69, 68, 45, 49, 52, 54, 56, 51, 48, 55, 48, 53, 56, 54, 56, 48, 31, 2, 8, 2, 18, 4, 0, 10, 20, 30, 26, 20, 77, 69, 68, 86, 69, 68, 45, 49, 52, 54, 56, 51, 48, 55, 48, 53, 56, 54, 56, 49};

        System.out.println("Sending " + Arrays.toString(bytes));
        final ChannelFuture f = ctx.writeAndFlush(bytes);
        f.addListener((ChannelFutureListener) future -> System.out.println("!! the message was successfully sent"));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byte[] bytes = (byte[]) msg;


        System.out.println("Received: " + Arrays.toString(bytes));
        //5, 2, 0, 10, 20, 30
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }


}
