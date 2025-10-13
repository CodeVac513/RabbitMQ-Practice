package com.example.HelloMessageQueue.step5;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/logs")
@RestController
public class LogController {

    private final CustomExceptionHandler exceptionHandler;

    public LogController(CustomExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
    }

    @GetMapping("/error")
    public ResponseEntity<String> errorApi() {
        try {
            String value = null;
            value.getBytes(); // nullpointer
        } catch (Exception e) {
            exceptionHandler.handleException(e);
        }

        return ResponseEntity.ok("Controller nullpointer exception 처리");
    }

    @GetMapping("/warn")
    public ResponseEntity<String> warnApi() {
        try {
            throw new IllegalArgumentException("invalid argument입니다.");
        } catch (Exception e) {
            exceptionHandler.handleException(e);
        }
        return ResponseEntity.ok("Controller IllegalArgument exception 처리");
    }

    @PostMapping("/info")
    public ResponseEntity<String> infoApi(@RequestBody String message) {
        exceptionHandler.handleMessage(message);
        return ResponseEntity.ok("controller info log 발송 처리");
    }
}
