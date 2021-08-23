package com.itheima.netty_chat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * Created with IntelliJ IDEA.
 *
 * @Description: 通道初始化器，用来加载通道处理器（ChannelHandler）
 * @author: Kang Yong
 * @date: 2021/8/23 10:30
 * @version: v1.0
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 初始化通道
     * 在这个方法中加载对应的ChannelHandler
     *
     * @param ch
     * @throws Exception
     */
    protected void initChannel(SocketChannel ch) throws Exception {
        // 获取管道，蒋一个一个的ChannelHandler添加到管道中
        ChannelPipeline pipeline = ch.pipeline();

        // 添加一个http的编解码器
        pipeline.addLast(new HttpServerCodec());
        // 添加一个用于支持大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());
        // 添加一个聚合器，这个聚合器主要是将HTTPMessage聚合成FullHttpRequest/Response
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));

        // 需要制定接受请求的路由
        // 必须使用以ws后缀结尾的url才能访问
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        // 添加在定义的Handler
        pipeline.addLast(new ChatHandler());

    }

}
