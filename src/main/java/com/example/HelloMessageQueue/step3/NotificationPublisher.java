package com.example.HelloMessageQueue.step3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publish(String message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_EXCHANGE, "", message); // Fanout에서 routing key는 무시됨
        log.info("[#] Published Notification: {}", message);
    }
}
