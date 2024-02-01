package com.wanted.preonboarding.websocket.handler;

import com.wanted.preonboarding.util.StringUtil;
import com.wanted.preonboarding.websocket.service.WebSocketService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

@ChannelHandler.Sharable
@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    /**
     * 클라이언트 접속 해제
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        try {
            Channel channel = ctx.channel();

            Object userId = StringUtil.getKeyFromValue(WebSocketService.channelKeys, channel.id());

            if (userId != null) {
                synchronized (WebSocketService.channelGroup) {
                    WebSocketService.channelKeys.remove(userId);
                }
            }

        } catch(Exception e) {
            log.error("Error ServerHandler : {}", e.getMessage());
        }
    }
}
