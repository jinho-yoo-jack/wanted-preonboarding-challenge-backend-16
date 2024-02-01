package com.wanted.preonboarding.config;

import com.wanted.preonboarding.websocket.service.WebSocketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ApplicationReadyListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.debug("[ApplicationReadyListener] APPLICATION READY");

        if(event instanceof ApplicationReadyEvent) {
            log.debug("[ApplicationReadyListener]  --> WebSocket Server Start");

            //Netty 웹소켓 서비스 시작
            WebSocketService webSocketService = new WebSocketService();
            webSocketService.start();

        }
    }
}
