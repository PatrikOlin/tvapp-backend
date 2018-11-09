package com.tvapp.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class SeriesController {

    @Autowired
    SeriesRepository seriesRepository;

    @GetMapping("/series")
    public List<Series> index() {
        return seriesRepository.findAll();
    }

    @GetMapping("/series/{id}")
    public Series show(@PathVariable String id) {
        int seriesId = Integer.parseInt(id);
        return seriesRepository.findOne(seriesId);
    }

    @PostMapping("/series/search")
    public List<Series> search(@RequestBody Map<String, String> body) {
        String searchTerm = body.get("text");
        return seriesRepository.findByNameContaining(searchTerm);
    }

    @PostMapping("/series")
    public Series create(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        return seriesRepository.save(new Series(name));
    }

    @PutMapping("/series/{id}")
    public Series update(@PathVariable String id, @RequestBody Map<String, String> body) {
        int seriesId = Integer.parseInt(id);
        Series series = seriesRepository.findOne(seriesId);
        series.setName(body.get("name"));
        return seriesRepository.save(series);
    }

    @DeleteMapping("/series/{id}")
    public boolean delete(@PathVariable String id) {
        int seriesId = Integer.parseInt(id);
        seriesRepository.delete(seriesId);
        return true;
    }
}
