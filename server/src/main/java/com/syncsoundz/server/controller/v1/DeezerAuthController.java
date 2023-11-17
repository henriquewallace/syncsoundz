package com.syncsoundz.server.controller.v1;

import com.syncsoundz.server.domain.AuthRedirectUrl;
import com.syncsoundz.server.domain.DeezerAuthRequest;
import com.syncsoundz.server.service.DeezerAuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/deezer")
public class DeezerAuthController {

    @Value("${DEEZER_APP_ID}")
    private String appId;

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
}
