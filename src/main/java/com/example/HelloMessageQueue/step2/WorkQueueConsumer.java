package com.example.HelloMessageQueue.step2;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class WorkQueueConsumer {

    public void workQueueTask(String message) {
        String[] messageParts = message.split("\\|");
        String originMessage = messageParts[0];
        int duration = Integer.parseInt(messageParts[1].trim());

        log.info("# Received: {} (duration: {}ms)", originMessage, duration);

        try {
            log.info("now...sleep time {}ms", duration);
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        log.info("# Completed: {}", originMessage);
    }
}
