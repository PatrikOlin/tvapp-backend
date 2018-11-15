package com.tvapp.rest;

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
    @Lob
    private byte[] thumbnail;

    public Show() {

    }

    public Show(String name, byte[] thumbnail) {
        this.setName(name);
        this.setThumbnail(thumbnail);
    }

    public Show(int id, String name, byte[] thumbnail) {
        this.setId(id);
        this.setName(name);
        this.setThumbnail(thumbnail);
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

    public byte[] getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(byte[] thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public String toString() {
        return "Show{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", thumbnail=" + thumbnail +
                '}';
    }
}
