package com.example.netty.proto;

import java.util.Arrays;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class BytesClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        sendProto(ctx);
//        sendHttp(ctx);
    }

    private void sendProto(ChannelHandlerContext ctx) {
        String httpHandshakeRequest = "GET /websocket HTTP/1.1\r\n" +
                "Host: 127.0.0.1:8084\r\n" +
                "Connection: Upgrade\r\n" +
                "Pragma: no-cache\r\n" +
                "Cache-Control: no-cache\r\n" +
                "Upgrade: protobuf\r\n" +
                "Origin: file://\r\n" +
                "Sec-WebSocket-Version: 13\r\n" +
                "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36\r\n" +
                "Accept-Encoding: gzip, deflate, sdch\r\n" +
                "Accept-Language: en-US,en;q=0.8\r\n" +
                "Sec-WebSocket-Key: LSgJeTRjqU+cA2U7eWzLPA==\r\n" +
                "Sec-WebSocket-Extensions: permessage-deflate; client_max_window_bits\r\n" +
                "\r\n";
        byte[] protoMessageBytes = {31, 2, 8, 2, 18, 4, 0, 10, 20, 30, 26, 20, 77, 69, 68, 86, 69, 68, 45, 49, 52, 54, 56, 51, 48, 55, 48, 53, 56, 54, 56, 48};

        sendBytes(ctx, httpHandshakeRequest.getBytes());
        sendBytes(ctx, protoMessageBytes);
        sendBytes(ctx, protoMessageBytes);
        sendBytes(ctx, protoMessageBytes);
        sendBytes(ctx, protoMessageBytes);

    }

    private void sendHttp(ChannelHandlerContext ctx) {
//        String httpHandshakeRequest = "GET /websocket HTTP/1.1\r\n" +
//                "Host: 127.0.0.1:8084\r\n" +
//                "Connection: Upgrade\r\n" +
//                "Pragma: no-cache\r\n" +
//                "Cache-Control: no-cache\r\n" +
//                "Upgrade: websocket\r\n" +
//                "Origin: file://\r\n" +
//                "Sec-WebSocket-Version: 13\r\n" +
//                "User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.106 Safari/537.36\r\n" +
//                "Accept-Encoding: gzip, deflate, sdch\r\n" +
//                "Accept-Language: en-US,en;q=0.8\r\n" +
//                "Sec-WebSocket-Key: LSgJeTRjqU+cA2U7eWzLPA==\r\n" +
//                "Sec-WebSocket-Extensions: permessage-deflate; client_max_window_bits\r\n" +
//                "\r\n";



        String httpHandshakeRequest =
                "GET http://server.example.com:443/ HTTP/1.1\n" +
                        "Upgrade: websocket\n" +
                        "\n";

        sendBytes(ctx, httpHandshakeRequest.getBytes());

//        String httpData =
//                "GET http://server.example.com:443/ HTTP/1.1\n" +
//                        "info: preved\n" +
//                        "\n";
//
//        sendBytes(ctx, httpData.getBytes());
//        sendBytes(ctx, httpData.getBytes());
//        sendBytes(ctx, httpData.getBytes());
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

    private void sendBytes(ChannelHandlerContext ctx, byte[] bytes) {
        System.out.println("Sending " + bytes.length + " bytes:" + Arrays.toString(bytes));
        final ChannelFuture f = ctx.writeAndFlush(bytes);
        f.addListener((ChannelFutureListener) future -> System.out.println("!! the message was successfully sent"));
    }


}
