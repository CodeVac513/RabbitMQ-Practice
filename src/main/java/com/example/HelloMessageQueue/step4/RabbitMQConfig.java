package com.example.HelloMessageQueue.step4;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String FANOUT_EXCHANGE_FOR_NEWS = "newsExchange";

    public static final String JAVA_QUEUE = "javaQueue";
    public static final String SPRING_QUEUE = "springQueue";
    public static final String VUE_QUEUE = "vueQueue";


    @Bean
    public Queue javaQueue() {
        return new Queue(JAVA_QUEUE, false); // 메시지는 volatile로 설정
    }

    @Bean
    public Queue springQueue() {
        return new Queue(SPRING_QUEUE, false); // 메시지는 volatile로 설정
    }

    @Bean
    public Queue vueQueue() {
        return new Queue(VUE_QUEUE, false); // 메시지는 volatile로 설정
    }

    @Bean
    public FanoutExchange newsExchange() {
        // 메시지를 수신하면 연결된 모든 큐로 브로드캐스트
        return new FanoutExchange(FANOUT_EXCHANGE_FOR_NEWS);
    }

    @Bean
    public Binding javaBinding(Queue javaQueue, FanoutExchange newsExchange) {
        // BindingBuilder.bind().to() 를 통해 큐와 익스체인지를 연결
        return BindingBuilder.bind(javaQueue).to(newsExchange);
    }

    @Bean
    public Binding springBinding(Queue springQueue, FanoutExchange newsExchange) {
        // BindingBuilder.bind().to() 를 통해 큐와 익스체인지를 연결
        return BindingBuilder.bind(springQueue).to(newsExchange);
    }

    @Bean
    public Binding vueBinding(Queue vueQueue, FanoutExchange newsExchange) {
        // BindingBuilder.bind().to() 를 통해 큐와 익스체인지를 연결
        return BindingBuilder.bind(vueQueue).to(newsExchange);
    }

}
