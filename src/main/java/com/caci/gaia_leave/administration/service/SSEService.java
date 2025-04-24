package com.caci.gaia_leave.administration.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class SSEService {

    public Flux<ServerSentEvent<String>> handleUserDeactivationOrDeletion() {
        return null;
    }
}
