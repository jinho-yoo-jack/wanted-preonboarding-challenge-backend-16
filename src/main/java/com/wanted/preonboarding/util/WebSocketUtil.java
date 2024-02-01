package com.wanted.preonboarding.util;

import com.wanted.preonboarding.websocket.service.WebSocketService;
import io.netty.channel.Channel;

public class WebSocketUtil {

    /**
     * channelKeys에서 사용자 Id에 해당하는 Channel 반환
     */
    public static Channel getChannel(String userId) {
        Channel channel = WebSocketService.channelGroup.find(WebSocketService.channelKeys.get(userId));
        if(channel == null) {
            return null;
        }
        return channel;
    }
}
