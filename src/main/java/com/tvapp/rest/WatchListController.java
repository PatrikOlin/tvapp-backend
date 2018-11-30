package com.tvapp.rest;

import com.tvapp.dto.ShowDetailsDTO;
import com.tvapp.model.Token;
import com.tvapp.model.Episode;
import com.tvapp.model.WatchList;
import com.tvapp.model.Show;
import com.tvapp.repository.ApiRepository;
import com.tvapp.repository.EpisodeRepository;
import com.tvapp.repository.WatchListRepository;
import com.tvapp.repository.ShowRepository;
import com.tvapp.themoviedb.MovieDBDAO;
import com.tvapp.themoviedb.domain.ExternalSources;
import com.tvapp.themoviedb.domain.MovieDBShowDetails;
import com.tvapp.thetvdb.TheTVDBDAO;
import com.tvapp.thetvdb.domain.TVDBShowDetails;
import com.tvapp.utils.services.Base64Service;
import com.tvapp.utils.services.TokenService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/watchlist")
public class WatchListController {

    private TokenService tokenService;
    private final WatchListRepository watchListRepository;
    private final ShowRepository showRepository;
    private final EpisodeRepository episodeRepository;
    private MovieDBDAO movieDBDAO;
    private TheTVDBDAO theTVDBDAO;

    public WatchListController(WatchListRepository watchListRepository,
                               ApiRepository apiRepository,
                               ShowRepository showRepository,
                               EpisodeRepository episodeRepository) {
        this.watchListRepository = watchListRepository;
        this.showRepository = showRepository;
        this.episodeRepository = episodeRepository;
        this.tokenService = new TokenService(apiRepository);
        theTVDBDAO = new TheTVDBDAO();
        movieDBDAO = new MovieDBDAO(tokenService.getApiKeyForMovieDB().getApiKey());
    }

    // TODO: Sortera listan efter next episode air date?
    /**
     * Return users watchlist
     *
     * @param header user_id
     * @return List of shows
     */
    @GetMapping
    public List<Show> getWatchList(@RequestHeader Map<String, String> header) {
        int userId = Integer.parseInt(Base64Service.decodeData(header.get("user_id")));
        List<Show> shows = showRepository.findAllShowsByUserId(userId);

        for (Show show : shows) {
            Show compareShow = getShow(show.getId());
            if (show.getId() == compareShow.getId()) {
                if ((!show.getStatus().equalsIgnoreCase(compareShow.getStatus()) ||
                        (!show.getNextAirDate().equalsIgnoreCase(compareShow.getNextAirDate())))) {
                    show = compareShow;
                    showRepository.save(show);
                }
            }
        }

        return shows;
    }

    /**
     * Add show to watchlist in database
     *
     * @param body to map id
     * @param header
     * @return a show
     */
    @PostMapping
    public Show addToWatchList(@RequestBody Map<String, Integer> body,
                               @RequestHeader Map<String, String> header) {
        int showId = body.get("show_id");
        int userId = Integer.parseInt(Base64Service.decodeData(header.get("user_id")));
        WatchList fav = null;

        Show show = showRepository.save(getShow(showId));
        if (watchListRepository.getWatchListByUserIdLikeAndShowIdLike(userId, showId) == null) {
            fav = watchListRepository.save(new WatchList(userId, showId));
        }

        if (fav == null) {
            return null;
        }
        return show;
    }

    // TODO: Lägga till filter på /api (Exception, swagger)
    // TODO: Få upp skiten på servern.

    /**
     * Removes a show from favorite and episodes
     * linked to the favorite id. Checks also if unique show.
     * If its true then it delete it from shows also.
     *
     * @param param
     * @param header
     */
    @DeleteMapping
    public void removeFromWatchList(@RequestParam Map<String, String> param,
                                    @RequestHeader Map<String, String> header) {
        int showId = Integer.parseInt(param.get("show_id"));
        int userId = Integer.parseInt(Base64Service.decodeData(header.get("user_id")));

        WatchList watchList = watchListRepository.getWatchListByUserIdLikeAndShowIdLike(userId, showId);
        List<Episode> episodes = episodeRepository.findAllByFavoriteId(watchList.getId());
        episodes.forEach(episode -> episodeRepository.delete(episode));
        if (watchListRepository.countByShowId(showId) == 1) {
            watchListRepository.delete(watchList);
            Show show = showRepository.findOne(showId);
            showRepository.delete(show);
        } else {
            watchListRepository.delete(watchList);
        }
    }

    /**
     * Add a watched episode to database.
     *
     * @param body to map key and value.
     */
    @PostMapping("/episode")
    public void watchedEpisodeList(@RequestBody Map<String, Integer> body, @RequestHeader Map<String, String> header) {
        int showId = body.get("show_id");
        int season = body.get("season");
        int episode = body.get("episode");
        int userId = Integer.parseInt(Base64Service.decodeData(header.get("user_id")));

        WatchList watchList = watchListRepository.getWatchListByUserIdLikeAndShowIdLike(userId, showId);
        Episode newEpisode = new Episode(watchList.getId(), season, episode);
        List<Episode> episodes = episodeRepository.findAllByFavoriteId(watchList.getId());

        for (Episode episodeToCheck : episodes) {
            if (newEpisode.getSeasonNumber() == episodeToCheck.getSeasonNumber() &&
                    newEpisode.getEpisodeNumber() == episodeToCheck.getEpisodeNumber()) return;
        }

        episodeRepository.save(newEpisode);
    }

    private Show getShow(int showId) {
        Token token = tokenService.checkExpirationDateForTVDBToken();
        TVDBShowDetails tvDB;

        MovieDBShowDetails movieDB = movieDBDAO.ShowDetails(showId);
        ExternalSources sources = movieDBDAO.getExternalIds(showId);
        try {
            tvDB = theTVDBDAO.showDetails(sources.getTvdb_id(), token.getToken());
        } catch (HttpClientErrorException ex) {
            token = tokenService.refreshTokenForTVDB(token);
            tvDB = theTVDBDAO.showDetails(sources.getTvdb_id(), token.getToken());
        }

        ShowDetailsDTO showDTO = new ShowDetailsDTO(movieDB, tvDB, sources);

        return new Show(showDTO);
    }
}
