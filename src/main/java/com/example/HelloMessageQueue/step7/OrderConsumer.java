package com.example.HelloMessageQueue.step7;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class OrderConsumer {

    private static final int MAX_RETRIES = 3;
    private int retryCount = 0;

    @RabbitListener(queues = RabbitMQConfig.ORDER_COMPLETED_QUEUE, containerFactory = "rabbitListenerContainerFactory")
    public void processOrder(String message, Channel channel, @Header("amqp_deliveryTag") long tag) {

        try {
            // 실패 유발
            if ("fail".equalsIgnoreCase(message)) {
                if (retryCount < MAX_RETRIES) {
                    log.info("###### Fail & Retry = {}", retryCount);
                    retryCount++;
                    throw new RuntimeException(message);
                } else {
                    log.info("###### 재시도 최대 횟수 초과 : {}, DLQ로 이동", retryCount);
                    retryCount = 0;
                    channel.basicNack(tag, false, false);
                    return;
                }
            }
            // 성공 처리
            log.info("# 성공 : {}", message);
            channel.basicAck(tag, false);
            retryCount = 0;
        } catch (Exception e) {
            log.error("# error 발생 {}", e.getMessage());
            try {
                // 실패 시 basicReject, 메시지 재처리
                channel.basicReject(tag, true);
            } catch (IOException ex) {
                log.error("# Fail & reject message {}", ex.getMessage());
            }
        }

    }
}
