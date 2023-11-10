package com.syncsoundz.server.domain;

import lombok.Data;

@Data
public class AuthRequestToken {

    private String code;
    private String grant_type;
    private String redirect_uri;
    private String client_id;
    private String code_verifier;
}
