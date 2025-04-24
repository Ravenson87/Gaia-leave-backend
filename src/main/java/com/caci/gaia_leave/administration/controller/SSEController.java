package com.caci.gaia_leave.administration.controller;

import com.caci.gaia_leave.administration.service.SSEService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@Validated
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/v1/sse")
@RequiredArgsConstructor
public class SSEController {

    private final SSEService sseService;

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<String>> handleUserDeactivationOrDeletion(
            @Valid
            @RequestParam("user_id")
            @NotNull(message = "User id can not be null")
            Integer userId
    ) {
       return sseService.handleUserDeactivationOrDeletion(userId);
    }
}
