package com.example.netty.proto;

import java.net.InetSocketAddress;
import java.net.URI;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

public class HttpHandshakeHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
        String host = address.getHostName();
        int port = address.getPort();

        URI uri = new URI("http://" + host + ":" + port);
        HttpRequest request = new DefaultFullHttpRequest(
                HttpVersion.HTTP_1_1, HttpMethod.GET, uri.getRawPath());

        request.headers().set("Upgrade", "protobuf");

        ChannelFuture f = ctx.channel().writeAndFlush(request);
        f.get();

        ChannelPipeline pipeline = ctx.channel().pipeline();

        pipeline.remove(this);

        pipeline.addLast(
                new ProtobufVarint32FrameDecoder(),
                ProtoDecoder.DEFAULT,

                new ProtobufVarint32LengthFieldPrepender(),
                ProtoEncoder.DEFAULT,

                new ProtoClientExtendedHandler());

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        //TODO: move to separate handler
        cause.printStackTrace();
        try {
            ctx.channel().close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}
