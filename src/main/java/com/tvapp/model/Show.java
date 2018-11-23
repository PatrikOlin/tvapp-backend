package com.tvapp.model;

import javax.persistence.*;

@Entity
@Table(name = "shows")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shows_id")
    private int id;

    @Column(name = "shows_title")
    private String title;

    @Column(name = "shows_thumbnail")
    private String imageUrl;

    @Column(name = "shows_overview", length = 2000)
    private String overview;

    @Column(name = "shows_status")
    private String status;



    public Show() {

    }

    public Show(String title, String imageUrl) {
        this.setTitle(title);
        this.setImageUrl(imageUrl);
    }

    public Show(int id, String title, String imageUrl) {
        this.setId(id);
        this.setTitle(title);
        this.setImageUrl(imageUrl);
    }

    public Show(String title, String imageUrl, String overview, String status) {
        this.setTitle(title);
        this.setImageUrl(imageUrl);
        this.setOverview(overview);
        this.setStatus(status);

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    @Override
    public String toString() {
        return "TVDBShowDetails{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imageUrl=" + imageUrl +
                '}';
    }
}
