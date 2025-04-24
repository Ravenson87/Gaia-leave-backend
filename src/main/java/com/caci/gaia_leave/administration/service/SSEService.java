package com.caci.gaia_leave.administration.service;


import com.caci.gaia_leave.administration.repository.request.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;


@Service
@RequiredArgsConstructor
public class SSEService {
    private final UserRepository userRepository;

    public Flux<ServerSentEvent<String>> handleUserDeactivationOrDeletion(Integer userId) {

       return Flux.interval(Duration.ofSeconds(5))
                .map(sequence -> ServerSentEvent.<String>builder()
                        .id(String.valueOf(sequence))
                        .event("user_deactivated")
                        .data("user_id" + userId + " - status_" + userRepository.findById(userId).map(user -> user.getStatus())
                                .orElse(false))
                        .build());
    }
}
