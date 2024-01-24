package com.syncsoundz.server.domain.spotify;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Followers {
    private String href;
    private Integer total;
}
