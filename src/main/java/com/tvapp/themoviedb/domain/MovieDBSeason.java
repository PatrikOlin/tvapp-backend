package com.tvapp.themoviedb.domain;

import java.util.List;

public class MovieDBSeason {

    private String air_date;
    private int id;
    private String name;
    private String overview;
    private String poster_path;
    private int season_number;
    private List<MovieDBEpisode> episodes;

    public MovieDBSeason() {
    }

    public String getAir_date() {
        return air_date;
    }

    public void setAir_date(String air_date) {
        this.air_date = air_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public int getSeason_number() {
        return season_number;
    }

    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }

    public List<MovieDBEpisode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<MovieDBEpisode> episodes) {
        this.episodes = episodes;
    }
}
