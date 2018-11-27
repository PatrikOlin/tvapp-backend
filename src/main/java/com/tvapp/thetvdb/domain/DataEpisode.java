package com.tvapp.thetvdb.domain;

public class DataEpisode {
    private Link links;
    private TVDBEpisode data;

    public DataEpisode() {
    }

    public Link getLinks() {
        return links;
    }

    public void setLinks(Link links) {
        this.links = links;
    }

    public TVDBEpisode getData() {
        return data;
    }

    public void setData(TVDBEpisode data) {
        this.data = data;
    }
}
