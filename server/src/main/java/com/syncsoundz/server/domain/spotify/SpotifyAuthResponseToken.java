package com.syncsoundz.server.domain.spotify;

import lombok.Data;

@Data
public class SpotifyAuthResponseToken {

    private String access_token;
    private String token_type;
    private String scope;
    private Integer expires_in;
    private String refresh_token;
}
