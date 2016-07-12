package com.example.netty.proto;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class HttpFrameDecoder extends ByteToMessageDecoder {

    private static final byte HTTP_END_OF_LINE = 0x0A;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        in.markReaderIndex();

        int length = 0;
        byte currentByte = 0;
        while (in.isReadable()) {
            length++;
            byte nextByte = in.readByte();
            if (currentByte == HTTP_END_OF_LINE && nextByte == HTTP_END_OF_LINE) {
                in.resetReaderIndex();
                out.add(in.readBytes(length));
                return;
            }
            currentByte = nextByte;
        }

        in.resetReaderIndex();
    }

    @Override
    protected void handlerRemoved0(ChannelHandlerContext ctx) throws Exception {
        System.out.println("HttpFrameDecoder removed");

//
//        ChannelPipeline pipeline = ctx.channel().pipeline();
//
//        pipeline.addLast(
//                new ProtobufVarint32FrameDecoder(),
//                ProtoDecoder.DEFAULT,
//
//                new ProtobufVarint32LengthFieldPrepender(),
//                ProtoEncoder.DEFAULT,
//
//                new ProtoServerHandler()
//
//        );


    }
}
