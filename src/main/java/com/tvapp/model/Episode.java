package com.tvapp.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "episode")
public class Episode {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "favorite_id")
    private int favoriteId;

    @Column(name = "season_no")
    private int seasonNumber;

    @Column(name = "episode_no")
    private int episodeNumber;

    public Episode() {
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
