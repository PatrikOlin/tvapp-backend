package com.tvapp.rest;

import com.tvapp.dto.ShowDetailsDTO;
import com.tvapp.model.ApiModel;
import com.tvapp.model.Episode;
import com.tvapp.model.Favorite;
import com.tvapp.model.Show;
import com.tvapp.repository.ApiRepository;
import com.tvapp.repository.EpisodeRepository;
import com.tvapp.repository.FavoriteRepository;
import com.tvapp.repository.ShowRepository;
import com.tvapp.themoviedb.MovieDBDAO;
import com.tvapp.themoviedb.domain.MovieDBShowDetails;
import com.tvapp.thetvdb.TheTVDBDAO;
import com.tvapp.thetvdb.domain.TVDBShowDetails;
import com.tvapp.utils.services.TokenService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/favorite")
public class FavoriteController {

    private TokenService tokenService;
    private final FavoriteRepository favoriteRepository;
    private final ApiRepository apiRepository;
    private final ShowRepository showRepository;
    private final EpisodeRepository episodeRepository;
    private MovieDBDAO movieDBDAO;
    private TheTVDBDAO theTVDBDAO;

    public FavoriteController(FavoriteRepository favoriteRepository,
                              ApiRepository apiRepository,
                              ShowRepository showRepository,
                              EpisodeRepository episodeRepository) {
        this.favoriteRepository = favoriteRepository;
        this.apiRepository = apiRepository;
        this.showRepository = showRepository;
        this.episodeRepository = episodeRepository;
        this.tokenService = new TokenService(apiRepository);
        theTVDBDAO = new TheTVDBDAO();
        movieDBDAO = new MovieDBDAO(tokenService.getApiKeyForMovieDB().getApiKey());
    }

    // TODO: 2018-11-27 Ändra favorite till watchlist kanske?

    /**
     * Return users watchlist
     *
     * @param header user_id
     * @return List of shows
     */
    @GetMapping
    public List<Show> getFavorites(@RequestHeader Map<String, String> header) {
        int userId = Integer.parseInt(header.get("user_id"));
        return showRepository.findAllShowsByUserId(userId);
    }

    // TODO: Kolla så det inte blir dubletter om nödvändigt
    /**
     * Add show to favorite in database
     *
     * @param body to map id
     * @return a show
     */
    @PostMapping
    public Show addToFavorite(@RequestBody Map<String, Integer> body) {
        int showId = body.get("show_id");
        int userId = body.get("user_id");
        ApiModel token = tokenService.checkExpirationDateForTVDBToken();
        TVDBShowDetails tvDB;

        MovieDBShowDetails movieDB = movieDBDAO.ShowDetails(showId);
        try {
            tvDB = theTVDBDAO.showDetails(movieDB.getExternal_ids().getTvdb_id(), token.getToken());
        } catch (HttpClientErrorException ex) {
            token = tokenService.refreshTokenForTVDB(token);
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

    // TODO: 2018-11-29 if show is unique remove from shows.

    /**
     * Removes a show from favorite and episodes
     * linked to the favorite id. Checks also if unique show.
     * If its true then it delete it from shows also.
     *
     * @param body
     */
    @DeleteMapping
    public void removeFromFavorites(@RequestBody Map<String, Integer> body) {
        int showId = body.get("show_id");
        int userId = body.get("user_id");

        Favorite favorite = favoriteRepository.getFavoriteByUserIdLikeAndShowIdLike(userId, showId);
        List<Episode> episodes = episodeRepository.findAllByFavoriteId(favorite.getId());
        episodes.forEach(episode -> episodeRepository.delete(episode));
        if (favoriteRepository.countByShowId(showId) == 1) {
            favoriteRepository.delete(favorite);
            Show show = showRepository.findOne(showId);
            showRepository.delete(show);
        } else {
            favoriteRepository.delete(favorite);
        }
    }

    /**
     * Add a watched episode to database.
     *
     * @param body to map key and value.
     */
    @PostMapping("episode")
    public void watchedEpisodeList(@RequestBody Map<String, Integer> body) {
        int showId = body.get("show_id");
        int userId = body.get("user_id");
        int season = body.get("season");
        int episode = body.get("episode");

        Favorite favorite = favoriteRepository.getFavoriteByUserIdLikeAndShowIdLike(userId, showId);
        episodeRepository.save(new Episode(favorite.getId(), season, episode));
    }

//    @DeleteMapping("episode")
//    public void removeEpisode(@RequestBody Map<String, Integer> body) {
//        int favoriteId = body.get("fav_id");
//        episodeRepository.delete(favoriteId);
//    }

//    @GetMapping("/{id}")
//    public String countFavoriteShow(@PathVariable String id) {
//
//        return "Total Shows: " + favoriteRepository.countByShowId(Integer.parseInt(id));
//    }
}
