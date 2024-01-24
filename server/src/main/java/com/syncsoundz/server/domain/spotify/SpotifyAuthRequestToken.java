package com.syncsoundz.server.domain.spotify;

import lombok.Data;

@Data
public class SpotifyAuthRequestToken {

    private String code;
    private String grant_type;
    private String redirect_uri;
    private String client_id;
    private String code_verifier;
}
