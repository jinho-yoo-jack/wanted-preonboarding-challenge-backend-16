package com.wanted.preonboarding.push.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.util.WebSocketUtil;
import com.wanted.preonboarding.websocket.service.WebSocketService;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 웹소켓 푸시 발송 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PushService {

    /**
     * 알람수신여부 설정된 사용자들을 추려서 웹소켓 발송
     */
    public void webSocketSendByUser(String userId, Object object) {
        try {
            if(WebSocketService.channelKeys.isEmpty()) {
                return;
            }

            Channel channel = WebSocketUtil.getChannel(userId);
            if(channel == null) {
                return;
            }

            if(channel.isOpen()) {
                channel.writeAndFlush(new TextWebSocketFrame(new ObjectMapper().writeValueAsString((object))));
            }
        } catch(Exception e) {
            log.error("Error webSocketSendByUser : {}", e.getMessage());
        }
    }
}
