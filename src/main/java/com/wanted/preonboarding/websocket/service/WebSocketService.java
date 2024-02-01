package com.wanted.preonboarding.websocket.service;


import com.wanted.preonboarding.websocket.handler.ServerHandler;
import com.wanted.preonboarding.websocket.handler.ServerRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;

public class WebSocketService {

    // Websocket Port
    private static int port=8017;

    // 웹 소켓 푸시 발송 채널 그룹
    public static final ChannelGroup channelGroup = new DefaultChannelGroup("channelGroup", GlobalEventExecutor.INSTANCE);

    // 웹 소켓 푸시 수신 할 사용자별 매핑 정보
    public static final ConcurrentHashMap<String, ChannelId> channelKeys = new ConcurrentHashMap<>(); // key:userName, value:channelId

    // 서버 채널 그룹
    public static final EventLoopGroup bossGroup = new NioEventLoopGroup(1);

    // 클라이언트 채널 그룹
    public static final EventLoopGroup workGroup = new NioEventLoopGroup();

    // 웹 소켓 서버 시작
    public ChannelFuture start() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workGroup);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new WebSocketInitializer());

        ChannelFuture future = bootstrap.bind(port);
        future.syncUninterruptibly();
        return future;
    }

    // 웹 소켓 서버 종료
    public void destroy(Channel channel) {
        if(channel != null){
            channel.close();
        }
        bossGroup.shutdownGracefully();
        workGroup.shutdownGracefully();
    }

    public static class WebSocketInitializer extends ChannelInitializer<SocketChannel> {
        @Override
        protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(new HttpServerCodec());
            pipeline.addLast(new ChunkedWriteHandler());
            pipeline.addLast(new HttpObjectAggregator(65536));
            pipeline.addLast(new ServerRequestHandler());
            pipeline.addLast(new WebSocketServerProtocolHandler("/websocket"));
            pipeline.addLast(new ServerHandler());
        }
    }
}
