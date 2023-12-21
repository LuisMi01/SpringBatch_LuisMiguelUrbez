package io.uax.banco.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@RestController
public class LogController {

    private final SseEmitter emitter = new SseEmitter();

    @GetMapping("/importCustomers")
    public SseEmitter handle() {
        return emitter;
    }

    public void sendLog(String log) throws IOException {
        emitter.send(log);
    }
}