package com.tvapp.rest;

import com.tvapp.Constants;
import com.tvapp.dto.EpisodeDTO;
import com.tvapp.dto.ShowDetailsDTO;
import com.tvapp.model.ApiModel;
import com.tvapp.model.Favorite;
import com.tvapp.repository.ApiRepository;
import com.tvapp.repository.FavoriteRepository;
import com.tvapp.themoviedb.MovieDBDAO;
import com.tvapp.themoviedb.domain.Result;
import com.tvapp.model.Show;
import com.tvapp.repository.ShowRepository;
import com.tvapp.themoviedb.domain.MovieDBSeason;
import com.tvapp.themoviedb.domain.MovieDBShowDetails;
import com.tvapp.thetvdb.TheTVDBDAO;
import com.tvapp.thetvdb.domain.TVDBEpisode;
import com.tvapp.thetvdb.domain.TVDBShowDetails;
import com.tvapp.utils.ShowResourceAssembler;
import com.tvapp.utils.services.TokenService;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/shows")
public class ShowController {

    private final ShowRepository showRepository;
    private final ApiRepository apiRepository;
    private final ShowResourceAssembler assembler;
    private MovieDBDAO movieDBDAO;
    private TheTVDBDAO theTVDBDAO;
    private TokenService tokenService;

    ShowController(ShowRepository showRepository, ApiRepository apiRepository, ShowResourceAssembler assembler) {
        this.showRepository = showRepository;
        this.apiRepository = apiRepository;
        this.assembler = assembler;
        this.tokenService = new TokenService(apiRepository);
        theTVDBDAO = new TheTVDBDAO();
        movieDBDAO = new MovieDBDAO(tokenService.getApiKeyForMovieDB().getApiKey());
    }

    // TODO: Behövs??
    @GetMapping
    public Resources<Resource<Show>> all() {
        List<Resource<Show>> shows = showRepository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());
        return new Resources<>(shows,
                linkTo(methodOn(ShowController.class).all()).withSelfRel());
    }

    // TODO: Behövs??
    @GetMapping("/{name}")
    public Resource<Show> one(@PathVariable String name) {
        Show show = showRepository.findByTitle(name);
        return assembler.toResource(show);
    }

    /**
     * Search for series
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
     * @param param to map id
     * @return Details of show
     */
    @GetMapping("/details")
    public ShowDetailsDTO getShow(@RequestParam Map<String, Integer> param) {
        MovieDBShowDetails movieDB = movieDBDAO.ShowDetails(param.get("show_id"));
        TVDBShowDetails tvDB = theTVDBDAO.showDetails(movieDB.getExternal_ids().getTvdb_id(), tokenService.checkExpirationDateForTVDBToken().getToken());

        return new ShowDetailsDTO(movieDB, tvDB);
    }

    // TODO: return season
    @GetMapping("/details/season")
    public MovieDBSeason getSeason(@RequestBody Map<String, String> body) {
        String id = body.get("show_id");
        String season = body.get("season");
        return movieDBDAO.ShowSeason(id, season);
    }

    // TODO: return episode
    @GetMapping("/details/episode")
    public List<TVDBEpisode> getEpisode(@RequestBody Map<String, String> body) {
        String id = body.get("show_id");
        String season = body.get("season");
        String episode = body.get("episode");
        String token = tokenService.checkExpirationDateForTVDBToken().getToken();
        return theTVDBDAO.getEpisode(id, season, episode, token);
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
