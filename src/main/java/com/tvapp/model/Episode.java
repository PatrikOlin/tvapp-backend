package com.tvapp.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "episode")
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "episode_id")
    private int id;

    @Column(name = "episode_title")
    private String title;

    @Column(name = "episode_overview", length = 2000)
    private String overview;

    @Column(name = "episode_length")
    private int length;

    @Column(name = "episode_air_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date airDate;

    public Episode() {
    }

    public Episode(String title, String overview, int length, Date airDate) {
        this.title = title;
        this.overview = overview;
        this.length = length;
        this.airDate = airDate;
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

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public Date getAirDate() {
        return airDate;
    }

    public void setAirDate(Date airDate) {
        this.airDate = airDate;
    }

    @Override
    public String toString() {
        return "Episode{" +
                "title='" + title + '\'' +
                ", overview='" + overview + '\'' +
                ", length=" + length +
                ", airDate=" + airDate +
                '}';
    }
}
