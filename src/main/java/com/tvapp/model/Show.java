package com.tvapp.model;

import javax.persistence.*;

@Entity
@Table(name = "shows")
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "shows_id")
    private int id;

    @Column(name = "shows_name")
    private String name;

    @Column(name = "shows_thumbnail")
    private String imageUrl;

    @Column(name = "shows_overview", length = 2000)
    private String overView;

    @Column(name = "shows_status")
    private String status;



    public Show() {

    }

    public Show(String name, String imageUrl) {
        this.setName(name);
        this.setImageUrl(imageUrl);
    }

    public Show(int id, String name, String imageUrl) {
        this.setId(id);
        this.setName(name);
        this.setImageUrl(imageUrl);
    }

    public Show(String name) {
        this.setName(name);
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Show{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageUrl=" + imageUrl +
                '}';
    }
}
