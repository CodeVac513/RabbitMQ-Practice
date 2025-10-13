package com.example.HelloMessageQueue.step5;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LogConsumer {

    @RabbitListener(queues = RabbitMQConfig.ERROR_QUEUE)
    public void consumeError(String message) {
        log.info("[ERROR] 에러 메시지 : {}", message);
    }

    @RabbitListener(queues = RabbitMQConfig.WARN_QUEUE)
    public void consumeWarn(String message) {
        log.info("[WARN] 경고 메시지 : {}", message);
    }

    @RabbitListener(queues = RabbitMQConfig.INFO_QUEUE)
    public void consumeInfo(String message) {
        log.info("[INFO] 일반 메시지 : {}", message);
    }

    @RabbitListener(queues = RabbitMQConfig.ALL_LOG_QUEUE)
    public void consumeAllLog(String message) {
        log.info("[ALL LOG] 모든 메시지 : {}", message);
    }
}
