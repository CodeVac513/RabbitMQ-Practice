package com.example.HelloMessageQueue.step5;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String ERROR_QUEUE = "errorQueue";
    public static final String WARN_QUEUE = "warnQueue";
    public static final String INFO_QUEUE = "infoQueue";
    public static final String DIRECT_EXCHANGE = "logExchange";


    @Bean
    public Queue errorQueue() {
        return new Queue(ERROR_QUEUE, false); // 메시지는 volatile로 설정
    }

    @Bean
    public Queue warnQueue() {
        return new Queue(WARN_QUEUE, false); // 메시지는 volatile로 설정
    }

    @Bean
    public Queue infoQueue() {
        return new Queue(INFO_QUEUE, false); // 메시지는 volatile로 설정
    }

    @Bean
    public DirectExchange directExchange() {
        // 메시지를 수신하면 연결된 모든 큐로 브로드캐스트
        return new DirectExchange(DIRECT_EXCHANGE);
    }

    @Bean
    public Binding errorBinding() {
        return BindingBuilder.bind(errorQueue()).to(directExchange()).with("error");
    }

    @Bean
    public Binding warnBinding() {
        return BindingBuilder.bind(warnQueue()).to(directExchange()).with("warn");
    }

    @Bean
    public Binding infoBinding() {
        return BindingBuilder.bind(infoQueue()).to(directExchange()).with("info");
    }
}
