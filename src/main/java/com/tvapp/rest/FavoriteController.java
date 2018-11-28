package com.tvapp.rest;

import com.tvapp.dto.ShowDetailsDTO;
import com.tvapp.model.ApiModel;
import com.tvapp.model.Episode;
import com.tvapp.model.Favorite;
import com.tvapp.model.Show;
import com.tvapp.repository.ApiRepository;
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
    private MovieDBDAO movieDBDAO;
    private TheTVDBDAO theTVDBDAO;

    public FavoriteController(FavoriteRepository favoriteRepository, ApiRepository apiRepository, ShowRepository showRepository) {
        this.favoriteRepository = favoriteRepository;
        this.apiRepository = apiRepository;
        this.showRepository = showRepository;
        this.tokenService = new TokenService(apiRepository);
        theTVDBDAO = new TheTVDBDAO();
        movieDBDAO = new MovieDBDAO(tokenService.getApiKeyForMovieDB().getApiKey());
    }

    // TODO: 2018-11-27 Ändra favorite till watchlist kanske?
    /**
     * Return users watchlist
     * @param header user_id
     * @return List of shows
     */
    @GetMapping
    public List<Show> getFavorites(@RequestHeader Map<String, String> header) {
        int userId = Integer.parseInt(header.get("user_id"));
        return showRepository.findAllShowsByUserId(userId);
    }

    /**
     * Add show to favorite in database
     * @param body to map id
     * @return a show
     */
    @PostMapping
    public Show addToFavorite(@RequestBody Map<String, String> body) {
        int showId = Integer.parseInt(body.get("show_id"));
        int userId = Integer.parseInt(body.get("user_id"));
        ApiModel token = tokenService.checkExpirationDateForTVDBToken();
        TVDBShowDetails tvDB = null;

        MovieDBShowDetails movieDB = movieDBDAO.ShowDetails(body.get("show_id"));
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

    // TODO: watchedEpisodeList
    @PostMapping("episode")
    public Episode watchedEpisodeList(@RequestBody Map<String, String> body) {

        return null;
    }
}
