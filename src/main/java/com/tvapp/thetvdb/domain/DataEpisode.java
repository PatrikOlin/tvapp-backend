package com.tvapp.thetvdb.domain;

import java.util.List;

public class DataEpisode {
    private Link links;
    private List<TVDBEpisode> data;

    public DataEpisode() {
    }

    public Link getLinks() {
        return links;
    }

    public void setLinks(Link links) {
        this.links = links;
    }

    public List<TVDBEpisode> getData() {
        return data;
    }

    public void setData(List<TVDBEpisode> data) {
        this.data = data;
    }
}
