package com.example.netty.proto;

import java.util.Arrays;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class FirstFrameDecoder extends ByteToMessageDecoder {

    private static final byte END_OF_LINE = 0x0A;
    private static final byte RETURN_OF_CURSOR = 0x0D;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

        in.markReaderIndex();

        int length = 0;
        byte currentByte = 0;
        while (in.isReadable()) {
            length++;
            byte nextByte = in.readByte();

            if(nextByte == RETURN_OF_CURSOR && in.isReadable()) {
                length++;
                nextByte = in.readByte();
            }

            if (currentByte == END_OF_LINE && nextByte == END_OF_LINE) {
                in.resetReaderIndex();
                byte[] bytes = new byte[length];//= in.readBytes(length);
                in.readBytes(bytes);
                System.out.println("Unread bytes: " + in.readableBytes());



                String protocol = detectProtocol(bytes);

                System.out.println("Protocol: " + protocol);
                if(protocol == null) {
                    System.out.println("Protocol is not defined");
                    ctx.channel().close();
                    return;
                }

                switch (protocol) {
                    case "protobuf":
//                        ByteBuf unreadBuffer = in.readBytes(in.readableBytes());

                        ByteBuf unreadBuffer = Unpooled.buffer(in.readableBytes());
                        in.readBytes(unreadBuffer, in.readableBytes());

                        addProtoHandlers(ctx.pipeline());
                        out.add(unreadBuffer);
                        break;
                    case "websocket":
                        in.resetReaderIndex();

                        ByteBuf newOut = Unpooled.buffer(in.readableBytes());
                        in.readBytes(newOut, in.readableBytes());

//                        in.retain();
                        addHttpHandlers(ctx.pipeline());
                        out.add(newOut);
                        break;
                    default:
                        System.out.println("Unsupported protocol:" + protocol);
                        ctx.channel().close();
                        return;
                }

//                out.add(unreadBuffer);

                return;
            }
            currentByte = nextByte;
        }

        in.resetReaderIndex();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.channel().close();
    }

    private String detectProtocol(byte[] bytes) {
        String str = new String(bytes);
        System.out.println("detectProtocol: " + str);

        String[] lines = str.split("\\n");
        System.out.println(Arrays.toString(lines));

        for (String line : lines) {
            if (line.trim().startsWith("Upgrade:")) {
                String[] header = line.trim().split(":");
                if (header.length == 2) {
                    return header[1].trim().toLowerCase();
                }
            }
        }

        return null;
    }

    private void addProtoHandlers(ChannelPipeline pipeline) {
        pipeline.addLast(
                new ProtobufVarint32FrameDecoder(),
                ProtoDecoder.DEFAULT,

                new ProtobufVarint32LengthFieldPrepender(),
                ProtoEncoder.DEFAULT,

                new ProtoServerHandler()
        );

        pipeline.remove(this);
    }

    private void addHttpHandlers(ChannelPipeline pipeline) {
        pipeline.addLast("HttpServerCodec", new HttpServerCodec());
        pipeline.addLast("HttpObjectAggregator", new HttpObjectAggregator(1024000));
        pipeline.addLast("HttpRequestHandler", new HttpRequestHandler());

        pipeline.remove(this);
    }
}
