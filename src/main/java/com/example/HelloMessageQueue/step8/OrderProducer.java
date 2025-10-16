package com.example.HelloMessageQueue.step8;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class OrderProducer {

    private final RabbitTemplate rabbitTemplate;

    public OrderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendShipping(String message) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDER_TOPIC_EXCHANGE,
                "order.completed",
                message);

        log.info("[주문 완료, 배송 지시 메시지 생성 : {} ]", message);
    }
}
