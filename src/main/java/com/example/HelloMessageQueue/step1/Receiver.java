package com.example.HelloMessageQueue.step1;

import org.springframework.stereotype.Component;

@Component
public class Receiver {
    public void receiveMessage(String message) {
        System.out.println("[#] Message received: " + message);
    }
}
