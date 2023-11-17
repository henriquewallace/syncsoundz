package com.syncsoundz.server.controller.v1;

import com.syncsoundz.server.domain.AuthRedirectUrl;
import com.syncsoundz.server.domain.DeezerAuthRequest;
import com.syncsoundz.server.domain.DeezerAuthRequestToken;
import com.syncsoundz.server.domain.DeezerAuthResponseToken;
import com.syncsoundz.server.service.DeezerAuthService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientException;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/deezer")
public class DeezerAuthController {

    @Value("${DEEZER_APP_ID}")
    private String appId;

    @Value("${DEEZER_SECRET_ID}")
    private String secretId;

    @Value("${REDIRECT_URI_GET_TOKEN_DEEZER}")
    private String redirectUriGetToken;

    private final DeezerAuthService deezerAuthService;

    public DeezerAuthController(DeezerAuthService deezerAuthService) {
        this.deezerAuthService = deezerAuthService;
    }

    @GetMapping("/login")
    public AuthRedirectUrl requestUserAccess() {
        DeezerAuthRequest deezerAuthRequest = new DeezerAuthRequest();
        deezerAuthRequest.setApp_id(appId);
        deezerAuthRequest.setRedirect_uri(redirectUriGetToken);
        deezerAuthRequest.setPerms("basic_access");

        AuthRedirectUrl authRedirectUrl = new AuthRedirectUrl();
        authRedirectUrl.setAuthRedirectUrl(deezerAuthService.requestUserAuthorization(deezerAuthRequest));

        return authRedirectUrl;
    }

    @GetMapping(value = "/get-token", produces = MediaType.APPLICATION_JSON_VALUE)
    public DeezerAuthResponseToken getTokenAccess(@RequestParam("code") String code, HttpServletResponse response) throws IOException {
        DeezerAuthRequestToken deezerAuthRequestToken = new DeezerAuthRequestToken();
        deezerAuthRequestToken.setApp_id(appId);
        deezerAuthRequestToken.setSecret(secretId);
        deezerAuthRequestToken.setCode(code);
        deezerAuthRequestToken.setOutput("json");

        response.sendRedirect("http://localhost:3000");

        return deezerAuthService.getAccessToken(deezerAuthRequestToken);
    }
}
