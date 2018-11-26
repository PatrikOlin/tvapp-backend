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
import com.tvapp.thetvdb.domain.TVDBShowDetails;
import com.tvapp.utils.ShowResourceAssembler;
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
    private final FavoriteRepository favoriteRepository;
    private MovieDBDAO movieDBDAO;
    private TheTVDBDAO theTVDBDAO;

    ShowController(ShowRepository showRepository, ApiRepository apiRepository, FavoriteRepository favoriteRepository, ShowResourceAssembler assembler) {
        this.showRepository = showRepository;
        this.apiRepository = apiRepository;
        this.favoriteRepository = favoriteRepository;
        this.assembler = assembler;
        theTVDBDAO = new TheTVDBDAO();
        movieDBDAO = new MovieDBDAO(getApiKeyForMovieDB().getApiKey());
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
    public ShowDetailsDTO getShow(@RequestParam Map<String, String> param) {
        MovieDBShowDetails movieDB = movieDBDAO.ShowDetails(param.get("show_id"));
        TVDBShowDetails tvDB = theTVDBDAO.showDetails(movieDB.getExternal_ids().getTvdb_id(), checkExpirationDateForTVDBToken().getToken());

        return new ShowDetailsDTO(movieDB, tvDB);
    }

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
    @PostMapping("/favorite")
    public Show addToFavorite(@RequestBody Map<String, String> body) {
        int showId = Integer.parseInt(body.get("show_id"));
        int userId = Integer.parseInt(body.get("user_id"));
        ApiModel token = checkExpirationDateForTVDBToken();
        TVDBShowDetails tvDB = null;

        MovieDBShowDetails movieDB = movieDBDAO.ShowDetails(body.get("show_id"));
        try {
            tvDB = theTVDBDAO.showDetails(movieDB.getExternal_ids().getTvdb_id(), token.getToken());
        } catch (HttpClientErrorException ex) {
            token = refreshTokenForTVDB(token);
            tvDB = theTVDBDAO.showDetails(movieDB.getExternal_ids().getTvdb_id(), token.getToken());
        }

        ShowDetailsDTO showDTO = new ShowDetailsDTO(movieDB, tvDB);
        Show show = showRepository.save(new Show(showDTO));
        Favorite fav = favoriteRepository.save(new Favorite(userId, showId));

        if (fav == null) {
            return null;
        }

        return show;
    }

    // TODO: Make a join query to fetch all shows
    @GetMapping("/favorite")
    public List<Show> getFavorites(@RequestHeader Map<String, String> header) {
        int userId = Integer.parseInt(header.get("user_id"));
        List<Show> shows = favoriteRepository.getAllByUserIdEquals(userId);

        return shows;
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
        int seriesId = Integer.parseInt(id);
        Show show = showRepository.findOne(seriesId);
        show.setTitle(body.get("name"));
        return showRepository.save(show);
    }

    // TODO: Ändras?? Kanske inte behövs som resurs utan endast private
    @DeleteMapping("/{id}")
    public boolean delete(@PathVariable String id) {
        int seriesId = Integer.parseInt(id);
        showRepository.delete(seriesId);
        return true;
    }

    // TODO: Bryta ut dessa 3 metoder till en service istället kanske om det med repos.

    /**
     * Checks if token has expired
     * @return the ApiModel for TVDB
     */
    private ApiModel checkExpirationDateForTVDBToken() {
        ApiModel apiToken = apiRepository.findByName(Constants.THE_TV_DB);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(apiToken.getCreationDate());
        calendar.add(Calendar.HOUR_OF_DAY, 24);
        if (calendar.getTime().before(new Date())) {
            apiToken = refreshTokenForTVDB(apiToken);
        }
        return apiToken;
    }

    /**
     * Get the movieDB apikey from database
     * @return they apikey
     */
    private ApiModel getApiKeyForMovieDB() {
        return apiRepository.findByName(Constants.MOVIE_DB);
    }

    /**
     * Refresh the token for TVDB
     * @param apiToken is refreshed and
     * @return the token
     */
    private ApiModel refreshTokenForTVDB(ApiModel apiToken) {
        apiToken.setToken(theTVDBDAO.refreshToken(apiToken).getToken());
        apiToken.setCreationDate(new Date());
        return apiRepository.save(apiToken);
    }
}
