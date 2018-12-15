package com.tvapp.themoviedb.domain;

import io.swagger.annotations.ApiModelProperty;
/**
 * @author Patrik Holmkvist & Patrik Olin
 * Datum: 2018-12-03
 * Kurs: Java EE
 * Labb: Projekt
 */
public class Result {

    @ApiModelProperty(notes = "Id of the show")
    private Integer id;
    @ApiModelProperty(notes = "Title of show")
    private String name;
    private String poster_path;
    private String overview;

    public Result() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public boolean HalfOfInfoIsMissing() {
        if (this.getId() == null || this.getName() == null || this.getName().isEmpty()) return true;
        int count = 0;
        if (this.getPoster_path() == null || this.getPoster_path().isEmpty()) {
            count++;
        }
        if (this.getOverview() == null || this.getOverview().isEmpty()) {
            count++;
        }

        if (count == 2) {
            return true;
        }
        return false;
    }
}
