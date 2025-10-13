package com.example.HelloMessageQueue.step4;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class NewsController {

    private final NewsPublisher newsPublisher;

    public NewsController(NewsPublisher newsPublisher) {
        this.newsPublisher = newsPublisher;
    }

    @MessageMapping("/subscribe")
    public void handleSubscribe(@Header("newsType") String newsType) {
        log.info("[#] newsType: {}", newsType);

        String newsMessage = newsPublisher.publish(newsType);
        log.info("# newsMessage: {}", newsMessage);
    }
}
