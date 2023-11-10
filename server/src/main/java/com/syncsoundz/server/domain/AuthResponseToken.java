package com.syncsoundz.server.domain;

import lombok.Data;

@Data
public class AuthResponseToken {

    private String access_token;
    private String token_type;
    private String scope;
    private Integer expires_in;
    private String refresh_token;
}
