package com.tvapp.dto;

import com.tvapp.themoviedb.domain.ExternalSources;
import com.tvapp.themoviedb.domain.MovieDBEpisode;
import com.tvapp.themoviedb.domain.MovieDBSeason;
import com.tvapp.themoviedb.domain.MovieDBShowDetails;
import com.tvapp.thetvdb.domain.TVDBShowDetails;

import java.util.List;

public class ShowDetailsDTO {
    private int id;
    private String title;
    private String first_air_date;
    private String overview;
    private String poster_path;
    private MovieDBEpisode last_episode_to_air;
    private MovieDBEpisode next_episode_to_air;
    private String status;
    private String airsDayOfWeek;
    private String airsTime;
    private double siteRating;
    private int siteRatingCount;
    private String imdbId;
    private List<MovieDBSeason> seasons;
    private ExternalSources externalSources;
    private boolean OnWatchList;

    public ShowDetailsDTO() {
        this.OnWatchList = false;
    }

    public ShowDetailsDTO(
            MovieDBShowDetails movieDBShowDetails,
            TVDBShowDetails tvdbShowDetails, ExternalSources externalSources) {
        this.id = movieDBShowDetails.getId();
        this.title = movieDBShowDetails.getName();
        this.first_air_date = movieDBShowDetails.getFirst_air_date();
        this.overview = movieDBShowDetails.getOverview();
        this.poster_path = movieDBShowDetails.getPoster_path();
        this.last_episode_to_air = movieDBShowDetails.getLast_episode_to_air();
        this.next_episode_to_air = movieDBShowDetails.getNext_episode_to_air();
        this.seasons = movieDBShowDetails.getSeasons();
        this.status = movieDBShowDetails.getStatus();

        this.airsDayOfWeek = tvdbShowDetails.getAirsDayOfWeek();
        this.airsTime = tvdbShowDetails.getAirsTime();
        this.siteRating = tvdbShowDetails.getSiteRating();
        this.siteRatingCount = tvdbShowDetails.getSiteRatingCount();
        this.imdbId = tvdbShowDetails.getImdbId();
        this.externalSources = externalSources;
        this.OnWatchList = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
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

    public MovieDBEpisode getLast_episode_to_air() {
        return last_episode_to_air;
    }

    public void setLast_episode_to_air(MovieDBEpisode last_episode_to_air) {
        this.last_episode_to_air = last_episode_to_air;
    }

    public MovieDBEpisode getNext_episode_to_air() {
        return next_episode_to_air;
    }

    public void setNext_episode_to_air(MovieDBEpisode next_episode_to_air) {
        this.next_episode_to_air = next_episode_to_air;
    }

    public List<MovieDBSeason> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<MovieDBSeason> seasons) {
        this.seasons = seasons;
    }

    public String getAirsDayOfWeek() {
        return airsDayOfWeek;
    }

    public void setAirsDayOfWeek(String airsDayOfWeek) {
        this.airsDayOfWeek = airsDayOfWeek;
    }

    public String getAirsTime() {
        return airsTime;
    }

    public void setAirsTime(String airsTime) {
        this.airsTime = airsTime;
    }

    public double getSiteRating() {
        return siteRating;
    }

    public void setSiteRating(double siteRating) {
        this.siteRating = siteRating;
    }

    public int getSiteRatingCount() {
        return siteRatingCount;
    }

    public void setSiteRatingCount(int siteRatingCount) {
        this.siteRatingCount = siteRatingCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public ExternalSources getExternalSources() {
        return externalSources;
    }

    public void setExternalSources(ExternalSources externalSources) {
        this.externalSources = externalSources;
    }

    public boolean getOnWatchList() {
        return OnWatchList;
    }

    public void setOnWatchList(boolean onWatchList) {
        OnWatchList = onWatchList;
    }
}
