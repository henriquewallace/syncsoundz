package com.syncsoundz.server.domain.spotify;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserPlaylistRequest {
    private String user_id;
    private Integer limit;
    private Integer offset;
}
