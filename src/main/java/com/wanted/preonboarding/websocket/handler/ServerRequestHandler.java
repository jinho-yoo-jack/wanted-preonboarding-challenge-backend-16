package com.wanted.preonboarding.websocket.handler;

import com.wanted.preonboarding.websocket.service.WebSocketService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

@Slf4j
@ChannelHandler.Sharable
@RequiredArgsConstructor
public class ServerRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    /**
     * 클라이언트 접속
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {
        try {
            // 웹소켓 접속 url Parsing
            QueryStringDecoder query = new QueryStringDecoder(request.uri());

            Map<String, List<String>> map = query.parameters();

            String userId = map.get("user_id").get(0);

            /*
                이미 접속 되었다면 접속 해제 {

                }
             */

            synchronized (WebSocketService.channelGroup) {
                WebSocketService.channelGroup.add(ctx.channel());
                WebSocketService.channelKeys.put(userId, ctx.channel().id());
            }
            String uri = StringUtils.substringBefore(request.uri(), "?");
            request.setUri(uri);
            ctx.fireChannelRead(request.retain());

        } catch (Exception e) {
            log.error("Error ServerRequestHandler : {}", e.getMessage());
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error(log.getName(), cause);
        log.debug("exceptionCaught : {}", cause);
        ctx.close();
    }
}
