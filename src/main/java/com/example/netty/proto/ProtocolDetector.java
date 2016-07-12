package com.example.netty.proto;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import static io.netty.handler.codec.http.HttpMethod.GET;

public class ProtocolDetector extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            handleHttpRequest(ctx, (HttpRequest) msg);
        } else {
            System.out.println("Unsupported protocol handshake message: " + msg);
            ctx.channel().close();
        }

        super.channelReadComplete(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, HttpRequest httpRequest) throws Exception {

        System.out.println("Http request received: " + httpRequest);

        Channel channel = ctx.channel();
        if (!httpRequest.getDecoderResult().isSuccess()) {
            System.out.println("Bad http request: " + httpRequest);
            channel.close();
            return;
        }

        if (httpRequest.getMethod() != GET) {
            System.out.println("Received unsupported HTTP method " + httpRequest.getMethod());
            channel.close();
            return;
        }

        String protocolId = httpRequest.headers().get("Upgrade");

        ChannelPipeline pipeline = channel.pipeline();
        if ("protobuf".equals(protocolId)) {
            addProtobufHandlers(pipeline);
        } else {
            System.out.println("unsupported protocol: " + protocolId);
        }

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        ChannelPipeline pipeline = ctx.pipeline();

        pipeline.remove("HttpServerCodec");
        pipeline.remove("HttpObjectAggregator");

        pipeline.addLast(
                new ProtobufVarint32FrameDecoder(),
                ProtoDecoder.DEFAULT,

                new ProtobufVarint32LengthFieldPrepender(),
                ProtoEncoder.DEFAULT,

                new ProtoServerHandler()

        );


        pipeline.remove("HttpFrameDecoder");

    }

    private void addProtobufHandlers(ChannelPipeline pipeline) {
        System.out.println("Adding proto handlers");
//
//        pipeline.remove("HttpFrameDecoder");
//        pipeline.remove("HttpServerCodec");
//        pipeline.remove("HttpObjectAggregator");
        pipeline.remove(this);


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
