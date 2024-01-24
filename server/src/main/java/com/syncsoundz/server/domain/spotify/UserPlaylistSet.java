package com.syncsoundz.server.domain.spotify;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserPlaylistSet {
    private String href;
    private Integer limit;
    private String next;
    private Integer offset;
    private String previous;
    private Integer total;
    private List<SimplifiedPlaylist> items;
}
