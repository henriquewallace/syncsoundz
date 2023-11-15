package com.syncsoundz.server.controller.v1;

import com.syncsoundz.server.domain.AuthRedirectUrl;
import com.syncsoundz.server.domain.AuthRequest;
import com.syncsoundz.server.domain.AuthRequestToken;
import com.syncsoundz.server.domain.AuthResponseToken;
import com.syncsoundz.server.service.SpotifyAuthService;
import com.syncsoundz.server.util.CodeChallengeUtil;
import com.syncsoundz.server.util.CodeVerifierUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientException;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1")
public class SpotifyAuthController {

    @Value("${SPOTIFY_CLIENT_ID}")
    private String clientId;

    @Value("${REDIRECT_URI_GET_TOKEN}")
    private String redirectUrlGetToken;

    private String codeVerifier;

    private final SpotifyAuthService spotifyAuthService;
    private final AuthRequest authRequest;

    public SpotifyAuthController(SpotifyAuthService spotifyAuthService) {
        this.spotifyAuthService = spotifyAuthService;
        this.authRequest = new AuthRequest();
        authRequest.setCode_challenge_method("S256");
        authRequest.setResponse_type("code");
        authRequest.setScope("user-read-private user-read-email");
    }

    @GetMapping("/login")
    public AuthRedirectUrl requestUserAccess() {
        codeVerifier = CodeVerifierUtil.generate();
        String codeChallenge = CodeChallengeUtil.generate(codeVerifier);

        authRequest.setCode_challenge(codeChallenge);
        authRequest.setClient_id(clientId);
        authRequest.setRedirect_uri(redirectUrlGetToken);

        AuthRedirectUrl authRedirectUrl = new AuthRedirectUrl();
        authRedirectUrl.setAuthRedirectUrl(spotifyAuthService.requestUserAuthorization(authRequest));

        return authRedirectUrl;
    }

    @GetMapping(value = "/get-token", produces = MediaType.TEXT_HTML_VALUE)
    public String getAccessToken(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
        AuthRequestToken authRequestToken = new AuthRequestToken();
        authRequestToken.setCode(code);
        authRequestToken.setCode_verifier(codeVerifier);
        authRequestToken.setRedirect_uri(authRequest.getRedirect_uri());
        authRequestToken.setClient_id(authRequest.getClient_id());
        authRequestToken.setGrant_type("authorization_code");

        AuthResponseToken authResponseToken = new AuthResponseToken();
        try {
            authResponseToken.setAccess_token(spotifyAuthService.getAccessToken(authRequestToken).getAccess_token());

        } catch (WebClientException error) {
            System.out.println("Error: " + error.getMessage());
        }

        response.sendRedirect("http://localhost:3000");

        return authResponseToken.getAccess_token();
    }
}
