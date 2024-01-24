package com.syncsoundz.server.service;

import com.syncsoundz.server.domain.deezer.DeezerAuthRequest;
import com.syncsoundz.server.domain.deezer.DeezerAuthRequestToken;
import com.syncsoundz.server.domain.deezer.DeezerAuthResponseToken;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class DeezerAuthService implements AuthService<DeezerAuthRequest, DeezerAuthRequestToken, DeezerAuthResponseToken> {

    private final WebClient webClient;
    private final String DEEZER_BASE_URL = "https://connect.deezer.com";

    public DeezerAuthService(WebClient.Builder  webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(DEEZER_BASE_URL).build();
    }

    @Override
    public String requestUserAuthorization(DeezerAuthRequest deezerAuthRequest) {
        return DEEZER_BASE_URL + UriComponentsBuilder
                .fromPath("/oauth/auth.php")
                .queryParam("app_id", deezerAuthRequest.getApp_id())
                .queryParam("redirect_uri", deezerAuthRequest.getRedirect_uri())
                .queryParam("perms", deezerAuthRequest.getPerms())
                .build().toUriString();
    }

    @Override
    public DeezerAuthResponseToken getAccessToken(DeezerAuthRequestToken deezerAuthRequestToken) {
        return this.webClient.get().uri(uriBuilder -> uriBuilder
                .path("/oauth/access_token.php")
                .queryParam("app_id", deezerAuthRequestToken.getApp_id())
                .queryParam("secret", deezerAuthRequestToken.getSecret())
                .queryParam("code", deezerAuthRequestToken.getCode())
                .queryParam("output", deezerAuthRequestToken.getOutput())
                .build()).retrieve().bodyToMono(DeezerAuthResponseToken.class).block();
    }
}
