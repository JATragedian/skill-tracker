package com.spb.skilltracker.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/reactive")
public class ReactiveDemoController {

    @GetMapping("/hello")
    public Mono<String> hello() {
        return Mono.just("Hello from WebFlux!");
    }

    @GetMapping("/numbers")
    public Flux<Integer> numbers() {
        return Flux.range(1, 5);
    }
}
