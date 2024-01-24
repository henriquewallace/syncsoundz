package com.syncsoundz.server.domain.spotify;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlaylistOwner {
    private ExternalUrls external_urls;
    private Followers followers;
    private String href;
    private String id;
    private String type;
    private String uri;
    private String display_name;
}
