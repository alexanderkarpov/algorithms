package com.example.netty;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

/**
 * Created by alexander on 12/28/15.
 */
public class TimeDecoder extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf in, List<Object> out) throws Exception {

//        System.out.println("data received: "+in.readableBytes());

//        if (in.readableBytes() < 4) {
//            return;
//        }

        out.add(new UnixTime(in.readUnsignedInt()));
    }

}
