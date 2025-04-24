package com.caci.gaia_leave.administration.service;

import com.caci.gaia_leave.administration.model.request.User;
import com.caci.gaia_leave.administration.repository.request.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.time.Duration;



@Service
@RequiredArgsConstructor
public class SSEService {
    private final UserRepository userRepository;

    private final Sinks.Many<String> statusSink = Sinks.many().multicast().onBackpressureBuffer();

//    @GetMapping(value = "/stream", produces = "text/event-stream")
    public Flux<ServerSentEvent<String>> streamStatus() {
        return statusSink.asFlux()
                .map(status -> ServerSentEvent.builder(status).build());
    }

    // Ovaj endpoint pozivaš kad se status promeni
//    @PostMapping("/update")
    public void updateStatus(String userId) {
        statusSink.tryEmitNext(userId);
    }

//    public Flux<ServerSentEvent<String>> handleUserDeactivationOrDeletion(Integer userId) {
//
//
//
//       return Flux.interval(Duration.ofSeconds(1))
//                .map(sequence -> ServerSentEvent.<String>builder()
//                        .id(String.valueOf(sequence))
//                        .event("user_deactivated")
//                        .data("user_id" + userId + " - status" + status)
//                        .build());
//    }
}
