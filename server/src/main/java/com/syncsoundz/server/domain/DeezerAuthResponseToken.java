package com.syncsoundz.server.domain;

import lombok.Data;

@Data
public class DeezerAuthResponseToken {

    private String access_token;
    private Integer expires;
}
