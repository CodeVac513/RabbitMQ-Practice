package com.example.HelloMessageQueue.step3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationPublisher publisher;

    @PostMapping
    public String sendNotification(@RequestBody String message) {
        publisher.publish(message);
        return "[#] Notification sent: " + message + "\n";
    }
}
