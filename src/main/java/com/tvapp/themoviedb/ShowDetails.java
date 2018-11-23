package com.tvapp.themoviedb;

import java.util.List;

public class ShowDetails {
    private String first_air_date;
    private Episode last_episode_to_air;
    private String name;
    private Episode next_episode_to_air;
    private List<Season> seasons;
    private ExternalSources external_ids;

    public ShowDetails() {
    }

    public ShowDetails(String first_air_date, Episode last_episode_to_air, String name, Episode next_episode_to_air, List<Season> seasons, ExternalSources external_ids) {
        this.first_air_date = first_air_date;
        this.last_episode_to_air = last_episode_to_air;
        this.name = name;
        this.next_episode_to_air = next_episode_to_air;
        this.seasons = seasons;
        this.external_ids = external_ids;
    }

    public String getFirst_air_date() {
        return first_air_date;
    }

    public void setFirst_air_date(String first_air_date) {
        this.first_air_date = first_air_date;
    }

    public Episode getLast_episode_to_air() {
        return last_episode_to_air;
    }

    public void setLast_episode_to_air(Episode last_episode_to_air) {
        this.last_episode_to_air = last_episode_to_air;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Episode getNext_episode_to_air() {
        return next_episode_to_air;
    }

    public void setNext_episode_to_air(Episode next_episode_to_air) {
        this.next_episode_to_air = next_episode_to_air;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

    public ExternalSources getExternal_ids() {
        return external_ids;
    }

    public void setExternal_ids(ExternalSources external_ids) {
        this.external_ids = external_ids;
    }
}
