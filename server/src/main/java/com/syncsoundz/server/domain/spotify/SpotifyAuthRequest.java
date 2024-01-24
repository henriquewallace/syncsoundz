package com.syncsoundz.server.domain.spotify;


import lombok.Data;

@Data
public class SpotifyAuthRequest {

    private String client_id;
    private String response_type;
    private String scope;
    private String redirect_uri;
    private String code_challenge_method;
    private String code_challenge;
}
