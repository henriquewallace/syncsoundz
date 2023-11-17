package com.syncsoundz.server.service;

import com.syncsoundz.server.domain.DeezerAuthRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class DeezerAuthService {

    private final WebClient webClient;
    private final String DEEZER_BASE_URL = "https://connect.deezer.com";

    public DeezerAuthService(WebClient.Builder  webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(DEEZER_BASE_URL).build();
    }

    public String requestUserAuthorization(DeezerAuthRequest deezerAuthRequest) {
        return DEEZER_BASE_URL + UriComponentsBuilder
                .fromPath("/oauth/auth.php")
                .queryParam("app_id", deezerAuthRequest.getApp_id())
                .queryParam("redirect_uri", deezerAuthRequest.getRedirect_uri())
                .queryParam("perms", deezerAuthRequest.getPerms())
                .build().toUriString();
    }
}
