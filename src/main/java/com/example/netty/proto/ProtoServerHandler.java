package com.example.netty.proto;

import com.example.netty.proto.messages.Container;
import com.google.protobuf.ByteString;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ProtoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        System.out.println("The channel is opened");
//
//        byte[] payload = {0, 10, 20, 30};
//        Container protoMessage = Container.newBuilder()
//                .setMsgId("MEDVED-" + System.currentTimeMillis())
//                .setMessageType(2)
//                .setMessageBody(ByteString.copyFrom(payload))
//                .build();
//        System.out.println("Sending '" + protoMessage + "'");
//        ctx.writeAndFlush(protoMessage); // (3)
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Container m = (Container) msg;
        System.out.println("Received: " + m);
//
//        byte[] payload = {0, 10, 20, 30};
//        Container protoMessage = Container.newBuilder()
//                .setMsgId("MEDVED-" + System.currentTimeMillis())
//                .setMessageType(2)
//                .setMessageBody(ByteString.copyFrom(payload))
//                .build();
//        System.out.println("Sending '" + protoMessage + "'");
//        ctx.writeAndFlush(protoMessage); // (3)
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("The channel is closed");
    }

}
