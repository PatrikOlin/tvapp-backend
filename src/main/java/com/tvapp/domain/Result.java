package com.tvapp.domain;

import java.util.List;

public class Result {

    private int id;
    private String name;
    private String poster_path;
    private String overview;


    public Result() {
    }

    public Result(int id, String name, String poster_path, String overview) {
        this.id = id;
        this.name = name;
        this.poster_path = poster_path;
        this.overview = overview;
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

    @Override
    public String toString() {
        return "Result{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", poster_path='" + poster_path + '\'' +
                ", overview='" + overview + '\'' +
                '}';
    }
}
