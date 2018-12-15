package com.tvapp.thetvdb.domain;
/**
 * @author Patrik Holmkvist & Patrik Olin
 * Datum: 2018-12-03
 * Kurs: Java EE
 * Labb: Projekt
 */
public class DataShowDetails {
    private TVDBShowDetails data;

    public DataShowDetails() {
    }

    public TVDBShowDetails getData() {
        return data;
    }

    public void setData(TVDBShowDetails data) {
        this.data = data;
    }
}
