package io.uax.banco.controller;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Controller
public class LogStreamController {

    private final ExecutorService nonBlockingService = Executors.newCachedThreadPool();

    @GetMapping("/importCustomers")
    public String streamLogsPage() {
        return "import/importCustomers";
    }

    @GetMapping(path = "/importCustomers/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamLogs() {
        SseEmitter emitter = new SseEmitter();
        // Use an ExecutorService to handle the log reading in a separate thread
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                Files.lines(Paths.get("myApp.log")).forEach(line -> {
                    try {
                        emitter.send(line);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                emitter.complete();
            } catch (IOException e) {
                emitter.completeWithError(e);
            }
        });
        executor.shutdown();

        return emitter;
    }

    private Iterable<String> getLogs() throws IOException {
        Path logFile = Paths.get("myApp.log");
        return Files.readAllLines(logFile);
    }
}