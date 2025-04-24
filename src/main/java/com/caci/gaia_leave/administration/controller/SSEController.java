package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.service.SSEService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@Validated
@RestController
@RequestMapping("/api/v1/sse")
@RequiredArgsConstructor
public class SSEController {

    private final SSEService sseService;

    @GetMapping(value = "/stream", produces = "text/event-stream")
    public Flux<ServerSentEvent<String>> streamStatus() {
       return sseService.streamStatus();
    }
}
