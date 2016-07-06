package com.example.netty.proto;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class VersionInfoHandler extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (byteBuf.readableBytes() >= 4) {
            byteBuf.readBytes(byteBuf.readableBytes());
            ctx.pipeline().remove(this);

            ctx.pipeline().addLast(
                    new ProtobufVarint32FrameDecoder(),
                    ProtoDecoder.DEFAULT,

                    new ProtobufVarint32LengthFieldPrepender(),
                    ProtoEncoder.DEFAULT,

                    new ProtoClientExtendedHandler());


        }


    }

}
