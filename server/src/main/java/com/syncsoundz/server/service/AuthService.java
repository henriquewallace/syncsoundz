package com.syncsoundz.server.service;

import com.syncsoundz.server.domain.AuthRequest;
import com.syncsoundz.server.domain.AuthRequestToken;
import com.syncsoundz.server.domain.AuthResponseToken;
import com.syncsoundz.server.util.SpotifyApiPath;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

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

    public AuthResponseToken getAccessToken(AuthRequestToken authRequestToken) {
        WebClient.RequestBodySpec bodySpec = this.webClient.post().uri(uriBuilder -> uriBuilder
                .path("/api/token")
                .queryParam("client_id", authRequestToken.getClient_id())
                .queryParam("code", authRequestToken.getCode())
                .queryParam("redirect_uri", authRequestToken.getRedirect_uri())
                .queryParam("grant_type", authRequestToken.getGrant_type())
                .queryParam("code_verifier", authRequestToken.getCode_verifier())
                .build());

        WebClient.ResponseSpec responseSpec = bodySpec.header("Content-Type","application/x-www-form-urlencoded").retrieve();

        return responseSpec.bodyToMono(AuthResponseToken.class).block();
    }
}
