package com.tvapp.model;

import javax.persistence.*;
import java.util.Date;
/**
 * @author Patrik Holmkvist & Patrik Olin
 * Datum: 2018-12-03
 * Kurs: Java EE
 * Labb: Projekt
 */
@Entity
@Table(name = "episodes")
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @Column(name = "watchlist_id")
    private int favoriteId;

    @Column(name = "season_no")
    private int seasonNumber;

    @Column(name = "episode_no")
    private int episodeNumber;

    public Episode() {
    }

    public Episode(int favoriteId, int seasonNumber, int episodeNumber) {
        this.favoriteId = favoriteId;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFavoriteId() {
        return favoriteId;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }
}
