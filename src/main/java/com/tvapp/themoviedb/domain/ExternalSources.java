package com.tvapp.themoviedb.domain;

public class ExternalSources {
    private String imdb_id;
    private String tvdb_id;
    private String facebook_id;
    private String instagram_id;
    private String twitter_id;

    public ExternalSources() {
    }

    public String getImdb_id() {
        return imdb_id;
    }

    public void setImdb_id(String imdb_id) {
        this.imdb_id = imdb_id;
    }

    public String getTvdb_id() {
        return tvdb_id;
    }

    public void setTvdb_id(String tvdb_id) {
        this.tvdb_id = tvdb_id;
    }

    public String getFacebook_id() {
        return facebook_id;
    }

    public void setFacebook_id(String facebook_id) {
        this.facebook_id = facebook_id;
    }

    public String getInstagram_id() {
        return instagram_id;
    }

    public void setInstagram_id(String instagram_id) {
        this.instagram_id = instagram_id;
    }

    public String getTwitter_id() {
        return twitter_id;
    }

    public void setTwitter_id(String twitter_id) {
        this.twitter_id = twitter_id;
    }
}
