package com.syncsoundz.server.domain.deezer;

import lombok.Data;

@Data
public class DeezerAuthRequestToken {

    private String app_id;
    private String secret;
    private String code;
    private String output;
}
