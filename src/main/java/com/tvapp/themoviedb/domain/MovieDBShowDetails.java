package com.tvapp.themoviedb.domain;

import java.util.List;

public class MovieDBShowDetails {
    private String name;
    private int id;
    private String first_air_date;
    private MovieDBEpisode last_episode_to_air;
    private MovieDBEpisode next_episode_to_air;
    private String overview;
    private String poster_path;
    private List<MovieDBSeason> seasons;
    private String status;

    public MovieDBShowDetails() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public MovieDBEpisode getLast_episode_to_air() {
        return last_episode_to_air;
    }

    public void setLast_episode_to_air(MovieDBEpisode last_episode_to_air) {
        this.last_episode_to_air = last_episode_to_air;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MovieDBEpisode getNext_episode_to_air() {
        return next_episode_to_air;
    }

    public void setNext_episode_to_air(MovieDBEpisode next_episode_to_air) {
        this.next_episode_to_air = next_episode_to_air;
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

    public List<MovieDBSeason> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<MovieDBSeason> seasons) {
        this.seasons = seasons;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
