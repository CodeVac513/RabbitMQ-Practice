package com.example.HelloMessageQueue.step2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
@RestController
public class WorkQueueController {
    /*
     * curl -X POST "http://localhost:8080/api/workqueue?message=Task1&duration=2000"
       curl -X POST "http://localhost:8080/api/workqueue?message=Task2&duration=4000"
       curl -X POST "http://localhost:8080/api/workqueue?message=Task3&duration=5000"
     *  */

    private final WorkQueueProducer workQueueProducer;

    @PostMapping("/workqueue")
    public String workQueue(@RequestParam String message, @RequestParam int duration) {
        workQueueProducer.sendWorkQueue(message, duration);
        return "Work queue sent = " + message + ", (" + duration + ")";
    }
}
