package com.tvapp.rest;

import com.tvapp.dto.EpisodeDTO;
import com.tvapp.dto.ShowDetailsDTO;
import com.tvapp.themoviedb.MovieDBDAO;
import com.tvapp.themoviedb.domain.Result;
import com.tvapp.model.Show;
import com.tvapp.repository.ShowRepository;
import com.tvapp.themoviedb.domain.MovieDBSeason;
import com.tvapp.themoviedb.domain.MovieDBShowDetails;
import com.tvapp.thetvdb.TheTVDBDAO;
import com.tvapp.thetvdb.domain.TVDBShowDetails;
import com.tvapp.utils.ShowResourceAssembler;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/shows")
public class ShowController {

    private String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NDMwNTI1N"
            +"DMsImlkIjoidHZhcHAiLCJvcmlnX2lhdCI6MTU0Mjk2NjE0MywidXNlcmlkIjo1MTQ"
            +"0MDEsInVzZXJuYW1lIjoicGF0cmlrLmhvbG1rdmlzdDN2NCJ9.0QgqrNpWh7vdKhhL"
            +"CCV-DupoQ03AcxMpuGZJ9GZTdPvheQLSczIASGwoTLPggfesKvUA3UCedeZ5tIIjMJ"
            +"EVrTBe_GoXKv0HWaHsB8XOrrRwiMt7FBYTgjZBvjxlLemCsu_J-uU3XNlbQcFRr4WR"
            +"mTQHXxpH-dXJJ8j9qtwV2jEZYLWHcU7yBm08psp_om9gxy31REC5pMCZbZkn85X21L"
            +"IVN_ubgwSA3R5Boc3nvgQlG9J4Ut74SHxG5f7ktee2rpwwY1s8F639KfU6SMUPWCxB"
            +"_MkPAtsy1AgmaURLfe8n3Bj3hXmrKDyWrkGuI9c1ZDeKP00uy3XFgVDv9k81Mw";

    private final ShowRepository showRepository;
    private final ShowResourceAssembler assembler;
    private MovieDBDAO movieDBDAO = new MovieDBDAO();
    private TheTVDBDAO theTVDBDAO = new TheTVDBDAO(token);

    ShowController(ShowRepository showRepository, ShowResourceAssembler assembler) {
        this.showRepository = showRepository;
        this.assembler = assembler;
    }

    @GetMapping
    public Resources<Resource<Show>> all() {
        List<Resource<Show>> shows = showRepository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(shows,
                linkTo(methodOn(ShowController.class).all()).withSelfRel());
    }

    @GetMapping("/{name}")
    public Resource<Show> one(@PathVariable String name) {

        Show show = showRepository.findByTitle(name);

        return assembler.toResource(show);
    }

    @GetMapping("/search")
    public List<Result> search(@RequestParam Map<String, String> param) {
        String searchTerm = param.get("searchQuery");
        //return showRepository.findByNameContaining(searchTerm);
        return movieDBDAO.searchShows(searchTerm);
    }

    @GetMapping("/details")
    public ShowDetailsDTO getShow(@RequestParam Map<String, String> param) {
        MovieDBShowDetails movieDB = movieDBDAO.ShowDetails(param.get("show_id"));
        TVDBShowDetails tvDB = theTVDBDAO.showDetails(movieDB.getExternal_ids().getTvdb_id());

        return new ShowDetailsDTO(movieDB, tvDB);
    }

//    @GetMapping("/details")
//    public MovieDBShowDetails getShow(@RequestParam Map<String, String> param) {
//        String id = param.get("show_id");
//        return movieDBDAO.ShowDetails(id);
//    }
//
//    @GetMapping("/tvdbdetails")
//    public TVDBShowDetails getTVDBShow(@RequestParam Map<String, String> param) {
//        String id = param.get("show_id");
//        return theTVDBDAO.showDetails(id);
//    }

    // TODO: return season
    @GetMapping("/details/season")
    public MovieDBSeason getSeason(@RequestParam Map<String, String> param) {
        String id = param.get("show_id");
        String season = param.get("season");
        return movieDBDAO.ShowSeason(id, season);
    }

    // TODO: return episode
    @GetMapping("/details/episode")
    public EpisodeDTO getEpisode() {
        return null;
    }

    // TODO: add show to favorite in db

    @PostMapping
    public Show create(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        return showRepository.save(new Show(name));
    }

    @PutMapping("/{id}")
    public Show update(@PathVariable String id, @RequestBody Map<String, String> body) {
        int seriesId = Integer.parseInt(id);
        Show show = showRepository.findOne(seriesId);
        show.setTitle(body.get("name"));
        return showRepository.save(show);
    }

    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable String id) {
        int seriesId = Integer.parseInt(id);
        showRepository.delete(seriesId);
        return true;
    }
}
