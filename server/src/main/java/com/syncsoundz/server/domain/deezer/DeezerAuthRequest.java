package com.syncsoundz.server.domain.deezer;

import lombok.Data;

@Data
public class DeezerAuthRequest {

    private String app_id;
    private String redirect_uri;
    private String perms;
}
