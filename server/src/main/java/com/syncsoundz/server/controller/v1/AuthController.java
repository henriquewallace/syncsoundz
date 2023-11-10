package com.syncsoundz.server.controller.v1;

import com.syncsoundz.server.domain.AuthRedirectUrl;
import com.syncsoundz.server.domain.AuthRequest;
import com.syncsoundz.server.service.AuthService;
import com.syncsoundz.server.util.CodeChallengeUtil;
import com.syncsoundz.server.util.CodeVerifierUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    @Value("${SPOTIFY_CLIENT_ID}")
    private String clientId;

    @Value("${REDIRECT_URI_GET_TOKEN}")
    private String redirectUrlGetToken;
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/login")
    public AuthRedirectUrl requestUserAccess() {
        String codeVerifier = CodeVerifierUtil.generate();
        String codeChallenge = CodeChallengeUtil.generate(codeVerifier);

        AuthRequest authRequest = new AuthRequest();
        authRequest.setClient_id(clientId);
        authRequest.setCode_challenge_method("S256");
        authRequest.setCode_challenge(codeChallenge);
        authRequest.setResponse_type("code");
        authRequest.setRedirect_uri(redirectUrlGetToken);
        authRequest.setScope("user-read-private user-read-email");

        AuthRedirectUrl authRedirectUrl = new AuthRedirectUrl();
        authRedirectUrl.setAuthRedirectUrl(authService.requestUserAuthorization(authRequest));

        return authRedirectUrl;
    }
}
