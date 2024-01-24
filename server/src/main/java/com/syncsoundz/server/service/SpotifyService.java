package com.syncsoundz.server.service;

import com.syncsoundz.server.domain.spotify.UserPlaylistSet;
import com.syncsoundz.server.util.SpotifyApiPath;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SpotifyService {

    private final WebClient webClient;

    public SpotifyService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(SpotifyApiPath.BASE_URL).build();
    }

    public UserPlaylistSet getUserPlaylist(String userId, Integer limit, Integer offset, String jwtToken) {
         WebClient.RequestBodySpec bodySpec = (WebClient.RequestBodySpec) this.webClient.get().uri(uriBuilder -> uriBuilder
                .path(SpotifyApiPath.USERS_PLAYLIST)
                .queryParam("limit", limit)
                .queryParam("offset", offset)
                .build(userId));

        WebClient.ResponseSpec responseSpec = bodySpec.header("Authorization", jwtToken).retrieve();

        return responseSpec.bodyToMono(UserPlaylistSet.class).block();
    }
}
