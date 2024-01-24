package com.syncsoundz.server.domain.spotify;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SimplifiedPlaylist {
    private String collaborative;
    private String description;
    private ExternalUrls external_urls;
    private String href;
    private String id;
    private List<Image> images;
    private String name;
    private PlaylistOwner owner;
    private Boolean public_;
    private String snapshot_id;
    private Tracks tracks;
    private String type;
    private String uri;
}
