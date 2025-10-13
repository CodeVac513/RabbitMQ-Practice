package com.example.HelloMessageQueue.step3;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NotificationMessage {
    private String message;

    public NotificationMessage() {
    }

    public NotificationMessage(String message) {
        this.message = message;
    }
}
