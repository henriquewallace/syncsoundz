package com.syncsoundz.server.service;

import com.syncsoundz.server.domain.AuthRequest;
import com.syncsoundz.server.util.SpotifyApiPath;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AuthService {

    private final WebClient webClient;

    public AuthService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(SpotifyApiPath.AUTH_BASE_URL).build();
    }

    public String requestUserAuthorization(AuthRequest authRequest) {

        return SpotifyApiPath.AUTH_BASE_URL + UriComponentsBuilder
                .fromPath(SpotifyApiPath.REQUEST_AUTHORIZATION)
                .queryParam("client_id", authRequest.getClient_id())
                .queryParam("response_type", authRequest.getResponse_type())
                .queryParam("scope", authRequest.getScope())
                .queryParam("redirect_uri", authRequest.getRedirect_uri())
                .queryParam("code_challenge_method", authRequest.getCode_challenge_method())
                .queryParam("code_challenge", authRequest.getCode_challenge())
                .build().toUriString();
    }
}
