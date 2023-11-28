package com.syncsoundz.server.controller.v1;

import com.syncsoundz.server.domain.AuthRedirectUrl;
import com.syncsoundz.server.domain.SpotifyAuthRequest;
import com.syncsoundz.server.domain.SpotifyAuthRequestToken;
import com.syncsoundz.server.domain.SpotifyAuthResponseToken;
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
@RequestMapping("/api/v1/spotify")
public class SpotifyAuthController {

    @Value("${SPOTIFY_CLIENT_ID}")
    private String clientId;

    @Value("${REDIRECT_URI_GET_TOKEN}")
    private String redirectUrlGetToken;

    private String codeVerifier;

    private final SpotifyAuthService spotifyAuthService;
    private final SpotifyAuthRequest spotifyAuthRequest;

    public SpotifyAuthController(SpotifyAuthService spotifyAuthService) {
        this.spotifyAuthService = spotifyAuthService;
        this.spotifyAuthRequest = new SpotifyAuthRequest();
        spotifyAuthRequest.setCode_challenge_method("S256");
        spotifyAuthRequest.setResponse_type("code");
        spotifyAuthRequest.setScope("user-read-private user-read-email");
    }

    @GetMapping("/login")
    public AuthRedirectUrl requestUserAccess() {
        codeVerifier = CodeVerifierUtil.generate();
        String codeChallenge = CodeChallengeUtil.generate(codeVerifier);

        spotifyAuthRequest.setCode_challenge(codeChallenge);
        spotifyAuthRequest.setClient_id(clientId);
        spotifyAuthRequest.setRedirect_uri(redirectUrlGetToken);

        AuthRedirectUrl authRedirectUrl = new AuthRedirectUrl();
        authRedirectUrl.setAuthRedirectUrl(spotifyAuthService.requestUserAuthorization(spotifyAuthRequest));

        return authRedirectUrl;
    }

    @GetMapping(value = "/get-token", produces = MediaType.TEXT_HTML_VALUE)
    public String getAccessToken(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
        SpotifyAuthRequestToken spotifyAuthRequestToken = new SpotifyAuthRequestToken();
        spotifyAuthRequestToken.setCode(code);
        spotifyAuthRequestToken.setCode_verifier(codeVerifier);
        spotifyAuthRequestToken.setRedirect_uri(spotifyAuthRequest.getRedirect_uri());
        spotifyAuthRequestToken.setClient_id(spotifyAuthRequest.getClient_id());
        spotifyAuthRequestToken.setGrant_type("authorization_code");

        SpotifyAuthResponseToken spotifyAuthResponseToken = new SpotifyAuthResponseToken();
        try {
            spotifyAuthResponseToken.setAccess_token(spotifyAuthService.getAccessToken(spotifyAuthRequestToken).getAccess_token());

        } catch (WebClientException error) {
            System.out.println("Error: " + error.getMessage());
        }

        response.sendRedirect("http://localhost:3000");

        return spotifyAuthResponseToken.getAccess_token();
    }
}
