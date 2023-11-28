package com.syncsoundz.server.service;

public interface AuthService<AuthRequest, AuthRequestToken, AuthResponseToken> {

    public String requestUserAuthorization(AuthRequest authRequest);

    public AuthResponseToken getAccessToken(AuthRequestToken authRequestToken);
}
