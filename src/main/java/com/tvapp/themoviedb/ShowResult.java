package com.tvapp.themoviedb;

import java.util.List;

public class ShowResult {
    private int page;
    private int total_result;
    private int total_pages;
    private List<Result> results;

    public ShowResult() {
    }

    public ShowResult(int page, int total_result, int total_pages, List<Result> results) {
        this.page = page;
        this.total_result = total_result;
        this.total_pages = total_pages;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal_result() {
        return total_result;
    }

    public void setTotal_result(int total_result) {
        this.total_result = total_result;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ShowResult{" +
                "page=" + page +
                ", total_result=" + total_result +
                ", total_pages=" + total_pages +
                ", results=" + results +
                '}';
    }
}
