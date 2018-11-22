package com.tvapp.rest;

import com.tvapp.model.Episode;
import com.tvapp.model.Show;
import com.tvapp.repository.EpisodeRepository;
import com.tvapp.repository.ShowRepository;
import com.tvapp.utils.ShowResourceAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("/episode")
public class EpisodeController {

    private final EpisodeRepository episodeRepository;

    EpisodeController(EpisodeRepository episodeRepository) {
        this.episodeRepository = episodeRepository;
    }

    @PostMapping
    public Episode create(@RequestBody Map<String, String> body) {
        String title = body.get("title");
        String overview = body.get("overview");
        int length = Integer.parseInt(body.get("length"));
        Date date = new Date();

        return episodeRepository.save(new Episode(
                title,
                overview,
                length,
                date
        ));
    }
}
