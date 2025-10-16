package com.example.HelloMessageQueue.step8;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class OrderConsumer {
    private final RabbitTemplate rabbitTemplate;
    private final RetryTemplate retryTemplate;


    public OrderConsumer(RabbitTemplate rabbitTemplate, RetryTemplate retryTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.retryTemplate = retryTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_COMPLETED_QUEUE)
    public void consume(String message) {
        retryTemplate.execute(context -> {
            try {
                System.out.println("# received message: " + message);
                System.out.println("# retry: " + context.getRetryCount());

                if ("fail".equalsIgnoreCase(message)) {
                    throw new RuntimeException(message);
                }

                System.out.println("# 메시지 처리 성공: " + message);
            } catch (Exception e) {
                if (context.getRetryCount() >= 2) {
                    rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_TOPIC_DLX, RabbitMQConfig.DEAD_LETTER_ROUTING_KEY, message);
                } else {
                    throw e;
                }
            }
            return null;
        });
    }
}
