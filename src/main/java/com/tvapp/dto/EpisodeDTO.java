package com.tvapp.dto;

import com.tvapp.themoviedb.domain.MovieDBEpisode;
import com.tvapp.thetvdb.domain.TVDBEpisode;

import java.util.Date;

public class EpisodeDTO {


    // MovieDB
    private Date air_date;
    private int episode_number;
    private int movieDBId;
    private int tvDBID;
    private String title;
    private String overview;
    private int season_number;
    private String still_path;

    // TvDB
    private String imdbId;
    private double siteRating;
    private double siteRatingCount;

    public EpisodeDTO() {
    }

    public EpisodeDTO(MovieDBEpisode movieDBEpisode, TVDBEpisode tvdbEpisode) {
        this.air_date = movieDBEpisode.getAir_date();
        this.episode_number = movieDBEpisode.getEpisode_number();
        this.movieDBId = movieDBEpisode.getId();
        this.tvDBID = tvdbEpisode.getId();
        this.title = movieDBEpisode.getName();
        this.overview = movieDBEpisode.getOverview();
        this.season_number = movieDBEpisode.getSeason_number();
        this.still_path = movieDBEpisode.getStill_path();
        this.imdbId = tvdbEpisode.getImdbId();
        this.siteRating = tvdbEpisode.getSiteRating();
        this.siteRatingCount = tvdbEpisode.getSiteRatingCount();
    }

    public Date getAir_date() {
        return air_date;
    }

    public void setAir_date(Date air_date) {
        this.air_date = air_date;
    }

    public int getEpisode_number() {
        return episode_number;
    }

    public void setEpisode_number(int episode_number) {
        this.episode_number = episode_number;
    }

    public int getMovieDBId() {
        return movieDBId;
    }

    public void setMovieDBId(int movieDBId) {
        this.movieDBId = movieDBId;
    }

    public int getTvDBID() {
        return tvDBID;
    }

    public void setTvDBID(int tvDBID) {
        this.tvDBID = tvDBID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getSeason_number() {
        return season_number;
    }

    public void setSeason_number(int season_number) {
        this.season_number = season_number;
    }

    public String getStill_path() {
        return still_path;
    }

    public void setStill_path(String still_path) {
        this.still_path = still_path;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public double getSiteRating() {
        return siteRating;
    }

    public void setSiteRating(double siteRating) {
        this.siteRating = siteRating;
    }

    public double getSiteRatingCount() {
        return siteRatingCount;
    }

    public void setSiteRatingCount(double siteRatingCount) {
        this.siteRatingCount = siteRatingCount;
    }
}
