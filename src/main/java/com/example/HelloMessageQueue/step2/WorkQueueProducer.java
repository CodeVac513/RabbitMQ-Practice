package com.example.HelloMessageQueue.step2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class WorkQueueProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendWorkQueue(String workQueueMessage, int duration) {
        String message = workQueueMessage + "| " + duration;
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, message);
        log.info("Sent workqueue {}", message);
    }
}
