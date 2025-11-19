package com.spb.skilltracker.controller;

import com.spb.skilltracker.dto.JokeResponse;
import com.spb.skilltracker.service.JokeClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reactive/jokes")
public class JokeController {

    private final JokeClientService jokeClientService;

    @GetMapping
    public Mono<JokeResponse> getJoke() {
        return jokeClientService.getRandomJoke();
    }
}
