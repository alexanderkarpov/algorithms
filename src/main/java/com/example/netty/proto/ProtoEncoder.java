package com.example.netty.proto;

import com.example.netty.proto.messages.Container;
import com.google.protobuf.CodedOutputStream;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

@Sharable
public class ProtoEncoder extends MessageToByteEncoder<Container> {
    public static final ProtoEncoder DEFAULT = new ProtoEncoder();

    private ProtoEncoder() {

    }

    public static void encode(int messageType, byte[] payload, ByteBuf out) throws IOException {
        encode(messageType, payload, payload.length + 4, new ByteBufOutputStream(out));
    }

    public static byte[] encodeToArray(int messageType, byte[] payload) throws IOException {
        int initialCapacity = payload.length + 4;
        ByteArrayOutputStream output = new ByteArrayOutputStream(initialCapacity);
        encode(messageType, payload, initialCapacity, output);
        return output.toByteArray();
    }

    private static void encode(int messageType, byte[] payload, int initialCapacity, OutputStream output) throws IOException {
        CodedOutputStream codedOutputStream = CodedOutputStream.newInstance(output, initialCapacity);
        codedOutputStream.writeRawVarint32(messageType);
        codedOutputStream.writeRawBytes(payload);
        codedOutputStream.flush();
    }

    public static ByteBuf encode(int messageType, byte[] payload) throws IOException {
        int initialCapacity = payload.length + 4;
        ByteBuf out = Unpooled.buffer(initialCapacity, initialCapacity);
        encode(messageType, payload, out);
        return out;
    }

    public static ByteBuf encode(int messageType, byte[] payload, Channel channel) throws IOException {
        int initialCapacity = payload.length + 4;
        ByteBuf out = channel.alloc().buffer(initialCapacity, initialCapacity);
        encode(messageType, payload, out);
        return out;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Container msg, ByteBuf out) throws Exception {
        encode(msg.getMessageType(), msg.toByteString().toByteArray(), out);
    }
}
