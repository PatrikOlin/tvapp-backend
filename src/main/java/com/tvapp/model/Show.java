package com.tvapp.model;

import com.tvapp.dto.ShowDetailsDTO;

import javax.persistence.*;
import java.util.Date;
/**
 * @author Patrik Holmkvist & Patrik Olin
 * Datum: 2018-12-03
 * Kurs: Java EE
 * Labb: Projekt
 */
@Entity
@Table(name = "shows")
public class Show {

    @Id
    private int id;

    private String title;

    @Column(name = "image_url")
    private String poster_path;

    @Column(length = 2000)
    private String overview;

    @Column(name = "status_of_show")
    private String status;

    @Temporal(TemporalType.DATE)
    @Column(name = "next_air_date")
    private Date nextAirDate;

    public Show() {
    }

    public Show(String title, String imageUrl) {
        this.setTitle(title);
        this.setPoster_path(imageUrl);
    }

    public Show(int id, String title, String imageUrl) {
        this.setId(id);
        this.setTitle(title);
        this.setPoster_path(imageUrl);
    }

    public Show(String title, String imageUrl, String overview, String status) {
        this.setTitle(title);
        this.setPoster_path(imageUrl);
        this.setOverview(overview);
        this.setStatus(status);

    }

    public Show(ShowDetailsDTO show) {
        this.id = show.getId();
        this.title = show.getTitle();
        this.poster_path = show.getPoster_path();
        this.overview = show.getOverview();
        this.status = show.getStatus();
        if (show.getNext_episode_to_air() == null) {
            this.nextAirDate = null;
        } else {
            this.nextAirDate = show.getNext_episode_to_air().getAir_date();
        }
    }

    public Show(String title) {
        this.setTitle(title);
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

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getNextAirDate() {
        return nextAirDate;
    }

    public void setNextAirDate(Date nextAirDate) {
        this.nextAirDate = nextAirDate;
    }
}
