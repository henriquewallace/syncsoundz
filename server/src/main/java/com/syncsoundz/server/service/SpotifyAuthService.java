package com.syncsoundz.server.service;

import com.syncsoundz.server.domain.SpotifyAuthRequest;
import com.syncsoundz.server.domain.SpotifyAuthRequestToken;
import com.syncsoundz.server.domain.SpotifyAuthResponseToken;
import com.syncsoundz.server.util.SpotifyApiPath;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class SpotifyAuthService {

    private final WebClient webClient;

    public SpotifyAuthService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(SpotifyApiPath.AUTH_BASE_URL).build();
    }

    public String requestUserAuthorization(SpotifyAuthRequest spotifyAuthRequest) {

        return SpotifyApiPath.AUTH_BASE_URL + UriComponentsBuilder
                .fromPath(SpotifyApiPath.REQUEST_AUTHORIZATION)
                .queryParam("client_id", spotifyAuthRequest.getClient_id())
                .queryParam("response_type", spotifyAuthRequest.getResponse_type())
                .queryParam("scope", spotifyAuthRequest.getScope())
                .queryParam("redirect_uri", spotifyAuthRequest.getRedirect_uri())
                .queryParam("code_challenge_method", spotifyAuthRequest.getCode_challenge_method())
                .queryParam("code_challenge", spotifyAuthRequest.getCode_challenge())
                .build().toUriString();
    }

    public SpotifyAuthResponseToken getAccessToken(SpotifyAuthRequestToken spotifyAuthRequestToken) {
        WebClient.RequestBodySpec bodySpec = this.webClient.post().uri(uriBuilder -> uriBuilder
                .path("/api/token")
                .queryParam("client_id", spotifyAuthRequestToken.getClient_id())
                .queryParam("code", spotifyAuthRequestToken.getCode())
                .queryParam("redirect_uri", spotifyAuthRequestToken.getRedirect_uri())
                .queryParam("grant_type", spotifyAuthRequestToken.getGrant_type())
                .queryParam("code_verifier", spotifyAuthRequestToken.getCode_verifier())
                .build());

        WebClient.ResponseSpec responseSpec = bodySpec.header("Content-Type","application/x-www-form-urlencoded").retrieve();

        return responseSpec.bodyToMono(SpotifyAuthResponseToken.class).block();
    }
}
