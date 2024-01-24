package com.syncsoundz.server.service;

import com.syncsoundz.server.util.SpotifyApiPath;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SpotifyService {

    private final WebClient webClient;

    public SpotifyService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(SpotifyApiPath.AUTH_BASE_URL).build();
    }
}
