package com.example.netty.proto;

import com.example.netty.proto.messages.Container;
import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class ProtoDecoder extends SimpleChannelInboundHandler<ByteBuf> {
    public static final ProtoDecoder DEFAULT = new ProtoDecoder();

    private ProtoDecoder() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        CodedInputStream inputStream = CodedInputStream.newInstance(msg.array(), 0, msg.readableBytes());
        int msgType = inputStream.readRawVarint32();
        Container container = Container.newBuilder()
                .setMessageType(msgType)
                .setMessageBody(ByteString.copyFrom(inputStream.readRawBytes(inputStream.getBytesUntilLimit())))
                .build();
        ctx.fireChannelRead(container);
    }
}