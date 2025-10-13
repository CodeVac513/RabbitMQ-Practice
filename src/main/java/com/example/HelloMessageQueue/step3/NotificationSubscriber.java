package com.example.HelloMessageQueue.step3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationSubscriber {

    public static final String CLIENT_URL = "/topic/notifications";

    private final SimpMessagingTemplate simpMessagingTemplate;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void subscriber(String message) {
        // RabbitMQ Queue에서 메시지 수신
        // String message = (String) rabbitTemplate.receiveAndConvert(RabbitMQConfig.QUEUE_NAME);
        log.info("[#] Received Notification: {}", message);
        // convertAndSend 를 통해 특정 경로로 메시지를 전달함
        simpMessagingTemplate.convertAndSend(CLIENT_URL, message); // 클라이언트에 브로드캐스트
    }
}
