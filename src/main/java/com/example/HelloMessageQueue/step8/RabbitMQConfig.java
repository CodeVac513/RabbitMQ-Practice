package com.example.HelloMessageQueue.step8;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ORDER_COMPLETED_QUEUE = "order_completed_queue";
    public static final String ORDER_EXCHANAGE = "order_completed_exchange";

    public static final String DLQ = "dead_letter_queue";
    public static final String DLX = "dead_letter_exchange";

    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(ORDER_EXCHANAGE);
    }

    @Bean
    public TopicExchange deadLetterExchange() {
        return new TopicExchange(DLX);
    }

    // 메시지가 처리되지 못했을 경우, 자동으로 DLQ로 이동시킴
    @Bean
    public Queue orderQueue() {
        return QueueBuilder
                .durable(ORDER_COMPLETED_QUEUE)
                .withArgument("x-dead-letter-exchange", DLX) // x-dead-letter-exchange라던가 routing-key는 RabbitMQ에서 지원하는 예약어다.
                .withArgument("x-dead-letter-routing-key", DLQ)
                .ttl(5000)
                .build();
    }

    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DLQ);
    }

    @Bean
    public Binding orderCompletedBinding() {
        return BindingBuilder.bind(orderQueue()).to(orderExchange()).with("order.completed.#");
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(DLQ);
    }

}
