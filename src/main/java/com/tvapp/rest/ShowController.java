package com.tvapp.rest;

import com.tvapp.dto.EpisodeDTO;
import com.tvapp.dto.ShowDetailsDTO;
import com.tvapp.model.Token;
import com.tvapp.repository.ApiRepository;
import com.tvapp.themoviedb.MovieDBDAO;
import com.tvapp.themoviedb.domain.*;
import com.tvapp.model.Show;
import com.tvapp.repository.ShowRepository;
import com.tvapp.thetvdb.TheTVDBDAO;
import com.tvapp.thetvdb.domain.TVDBEpisode;
import com.tvapp.thetvdb.domain.TVDBShowDetails;
import com.tvapp.utils.ShowResourceAssembler;
import com.tvapp.utils.services.TokenService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/shows")
public class ShowController {

    private final ShowRepository showRepository;
    private final ShowResourceAssembler assembler;
    private MovieDBDAO movieDBDAO;
    private TheTVDBDAO theTVDBDAO;
    private TokenService tokenService;

    ShowController(ShowRepository showRepository, ApiRepository apiRepository, ShowResourceAssembler assembler) {
        this.showRepository = showRepository;
        this.assembler = assembler;
        this.tokenService = new TokenService(apiRepository);
        theTVDBDAO = new TheTVDBDAO();
        movieDBDAO = new MovieDBDAO(tokenService.getApiKeyForMovieDB().getApiKey());
    }

//    // TODO: Behövs??
//    @GetMapping
//    public Resources<Resource<Show>> all() {
//        List<Resource<Show>> shows = showRepository.findAll().stream()
//                .map(assembler::toResource)
//                .collect(Collectors.toList());
//        return new Resources<>(shows,
//                linkTo(methodOn(ShowController.class).all()).withSelfRel());
//    }
//
//    // TODO: Behövs??
//    @GetMapping("/{name}")
//    public Resource<Show> one(@PathVariable String name) {
//        Show show = showRepository.findByTitle(name);
//        return assembler.toResource(show);
//    }

    /**
     * Search for series
     *
     * @param param searchQuery
     * @return List of search result
     */
    @GetMapping("/search")
    public List<Result> search(@RequestParam Map<String, String> param) {
        String searchTerm = param.get("searchQuery");
        return movieDBDAO.searchShows(searchTerm);
    }

    /**
     * Get a detail information of a show.
     *
     * @param param to map id
     * @return Details of show
     */
    @GetMapping("/details")
    public ShowDetailsDTO getShow(@RequestParam Map<String, String> param) {
        int showId = Integer.parseInt(param.get("show_id"));
        MovieDBShowDetails movieDB = movieDBDAO.ShowDetails(showId);
        ExternalSources sources = movieDBDAO.getExternalIds(showId);
        Token token = tokenService.checkExpirationDateForTVDBToken();
        TVDBShowDetails tvDB;

        try {
            tvDB = theTVDBDAO.showDetails(sources.getTvdb_id(), token.getToken());
        } catch (HttpClientErrorException ex) {
            token = tokenService.refreshTokenForTVDB(token);
            tvDB = theTVDBDAO.showDetails(sources.getTvdb_id(), token.getToken());
        }

        return new ShowDetailsDTO(movieDB, tvDB, sources);
    }

    @GetMapping("/details/season")
    public MovieDBSeason getSeason(@RequestBody Map<String, String> body) {
        String id = body.get("show_id");
        String season = body.get("season");
        return movieDBDAO.ShowSeason(id, season);
    }

    /**
     * Send request to gather episode information
     *
     * @param body to map id, season and episode
     * @return a EpisodeDTO
     */
    @GetMapping("/details/episode")
    public EpisodeDTO getDetailedEpisode(@RequestBody Map<String, Integer> body) {
        int movieDBId = body.get("show_id");
        int season = body.get("season");
        int episode = body.get("episode");
        int tvDBId = movieDBDAO.getExternalIds(movieDBId).getTvdb_id();

        MovieDBEpisode movieDBEpisode = movieDBDAO.getEpisode(movieDBId, season, episode);
        TVDBEpisode tvdbEpisode;
        Token token = tokenService.checkExpirationDateForTVDBToken();
        try {
            tvdbEpisode = theTVDBDAO.getEpisode(tvDBId, season, episode, token.getToken()).get(0);
        } catch (HttpClientErrorException ex) {
            token = tokenService.refreshTokenForTVDB(token);
            tvdbEpisode = theTVDBDAO.getEpisode(tvDBId, season, episode, token.getToken()).get(0);
        }

        return new EpisodeDTO(movieDBEpisode, tvdbEpisode);
    }

    // TODO: Behövs??
    @PostMapping
    public Show create(@RequestBody Map<String, String> body) {
        String name = body.get("name");
        return showRepository.save(new Show(name));
    }

    // TODO: Ändras?? Kanske inte behövs som resurs utan endast private
    @PutMapping("/{id}")
    public Show update(@PathVariable String id, @RequestBody Map<String, String> body) {
        int showId = Integer.parseInt(id);
        Show show = showRepository.findOne(showId);
        show.setTitle(body.get("name"));
        return showRepository.save(show);
    }

    // TODO: Ändras?? Kanske inte behövs som resurs utan endast som private
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable String id) {
        int seriesId = Integer.parseInt(id);
        showRepository.delete(seriesId);
        return true;
    }
}
