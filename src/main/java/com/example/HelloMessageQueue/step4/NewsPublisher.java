package com.example.HelloMessageQueue.step4;


import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NewsPublisher {
    private final RabbitTemplate rabbitTemplate;

    public NewsPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private String publishMessage(String news, String messageSuffix) {
        String message = news + messageSuffix;
        rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_EXCHANGE_FOR_NEWS, news, message);
        log.info("News Published: {}", message);
        return message;
    }

    public String publish(String news) {
        return publishMessage(news, " 관련 새소식이 있어요!");
    }

    public String publishAPI(String news) {
        return publishMessage(news, " 관련 새소식이 나왔습니다. (API)");
    }
}
