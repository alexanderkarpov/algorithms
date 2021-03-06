package com.example.netty.proto;

import com.example.netty.proto.messages.Container;
import com.google.protobuf.ByteString;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ProtoClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte[] payload = {0, 1, 2, 3};
        Container protoMessage = Container.newBuilder()
                .setMsgId("PREVED-" + System.currentTimeMillis())
                .setMessageType(1)
                .setMessageBody(ByteString.copyFrom(payload))
                .build();

        System.out.println("Sending " + protoMessage);
        final ChannelFuture f = ctx.writeAndFlush(protoMessage);
        f.addListener((ChannelFutureListener) future -> System.out.println("!! the message was successfully sent"));
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Container m = (Container) msg;
        System.out.println("Received: " + m);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
