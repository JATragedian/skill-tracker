package com.spb.skilltracker.service;

import com.spb.skilltracker.dto.JokeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class JokeClientService {

    private final WebClient webClient;

    public Mono<JokeResponse> getRandomJoke() {
        return webClient
                .get()
                .uri("https://official-joke-api.appspot.com/random_joke")
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        response -> Mono.error(new RuntimeException("Server error"))
                )
                .bodyToMono(JokeResponse.class)
                .timeout(Duration.ofSeconds(3))
                .retry(1);
    }
}
