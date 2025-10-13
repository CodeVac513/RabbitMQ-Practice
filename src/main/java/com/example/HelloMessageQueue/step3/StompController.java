package com.example.HelloMessageQueue.step3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Slf4j
@RequiredArgsConstructor
@Controller
public class StompController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/send")
    public void sendMessage(NotificationMessage notificationMessage) {
        String message = notificationMessage.getMessage();
        log.info("[#] message: {}", message);

        messagingTemplate.convertAndSend("/topic/notifications", message);
    }

}
