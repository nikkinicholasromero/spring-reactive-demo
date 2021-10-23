package com.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
public class GreetingHandler {
    @Autowired
    private GreetingService service;

    public Mono<ServerResponse> hello(ServerRequest request) {
        Greeting greeting = service.getGreeting(request.pathVariable("name"));

        try {
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(BodyInserters.fromValue(greeting));
        } catch (RuntimeException e) {
            return ServerResponse.badRequest().build();
        }
    }
}
