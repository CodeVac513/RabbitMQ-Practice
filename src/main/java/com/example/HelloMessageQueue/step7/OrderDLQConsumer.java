package com.example.HelloMessageQueue.step7;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderDLQConsumer {

    private final RabbitTemplate rabbitTemplate;

    public OrderDLQConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.DLQ)
    public void process(String message) {
        log.info("DLQ message : {}", message);

        try {
            String fixMessage = "success";

            rabbitTemplate.convertAndSend(
                    RabbitMQConfig.ORDER_EXCHANAGE,
                    "order.completed.shipping",
                    fixMessage);

            log.info("DLQ message sent : {}", fixMessage);
        } catch (Exception e) {
            log.error("### [DLQ Consumer ERROR] {}", e.getMessage());
        }
    }
}
