package io.uax.banco.controller;


import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class LogsStreamController {
    private final ExecutorService nonBlockingService = Executors.newCachedThreadPool();

    @GetMapping(path = "/importCustomer", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamLogs() {
        SseEmitter emitter = new SseEmitter();
        nonBlockingService.execute(() -> {
            try {
                for (String log : getLogs()) {
                    emitter.send(log);
                    Thread.sleep(1000);
                }
                emitter.complete();
            } catch (Exception ex) {
                emitter.completeWithError(ex);
            }
        });
        return emitter;
    }

    private Iterable<String> getLogs() throws IOException {
        Path logFile = Paths.get("myApp.log");
        return Files.readAllLines(logFile);
    }
}
