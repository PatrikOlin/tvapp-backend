package com.tvapp.rest;

import com.tvapp.dto.EpisodeDTO;
import com.tvapp.dto.ShowDetailsDTO;
import com.tvapp.model.Token;
import com.tvapp.repository.ApiRepository;
import com.tvapp.repository.WatchListRepository;
import com.tvapp.themoviedb.MovieDBDAO;
import com.tvapp.themoviedb.domain.*;
import com.tvapp.thetvdb.TheTVDBDAO;
import com.tvapp.thetvdb.domain.TVDBEpisode;
import com.tvapp.thetvdb.domain.TVDBShowDetails;
import com.tvapp.utils.constants.ReqConst;
import com.tvapp.utils.services.Base64Service;
import com.tvapp.utils.services.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Map;

/**
 * @author Patrik Holmkvist & Patrik Olin
 * Datum: 2018-12-03
 * Kurs: Java EE
 * Labb: Projekt
 */

@RestController
@CrossOrigin
@RequestMapping("/shows")
@Api(description = "Operations pertaining shows in ShowTime")
public class ShowController {

    private final WatchListRepository watchListRepository;
    private MovieDBDAO movieDBDAO;
    private TheTVDBDAO theTVDBDAO;
    private TokenService tokenService;

    ShowController(WatchListRepository watchListRepository,
                   ApiRepository apiRepository) {
        this.watchListRepository = watchListRepository;
        this.tokenService = new TokenService(apiRepository);
        theTVDBDAO = new TheTVDBDAO();
        movieDBDAO = new MovieDBDAO(tokenService.getApiKeyForMovieDB().getApiKey());
    }


    /**
     * Search for series
     *
     * @param param searchQuery
     * @return List of search result
     */
    @ApiOperation(value = "Search for a show", response = List.class)
    @RequestMapping(value = "/search", method = RequestMethod.GET, produces = "application/json")
    public List<Result> search(@RequestParam Map<String, String> param) {
        String searchTerm = param.get(ReqConst.SEARCH);
        List<Result> resultList = movieDBDAO.searchShows(searchTerm);
        resultList.removeIf(Result::HalfOfInfoIsMissing);
        return resultList;
    }

    /**
     * Get a detail information of a show.
     *
     * @param param to map id
     * @return Details of show
     */
    @GetMapping("/details")
    public ShowDetailsDTO getShow(@RequestParam Map<String, String> param,
                                  @RequestHeader Map<String, String> header) {
        int showId = Integer.parseInt(param.get(ReqConst.SHOWID));
        int userId = Integer.parseInt(Base64Service.decodeData(header.get(ReqConst.USERID)));
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

        ShowDetailsDTO showDetails = new ShowDetailsDTO(movieDB, tvDB, sources);
        int OnWatchList = watchListRepository.countByUserIdAndShowId(userId, showId);
        showDetails.setOnWatchList(OnWatchList == 1);

        return showDetails;
    }

    @GetMapping("/details/season")
    public MovieDBSeason getSeason(@RequestParam Map<String, String> param) {
        String id = param.get("show_id");
        String season = param.get("season");
        return movieDBDAO.ShowSeason(id, season);
    }

    /**
     * Send request to gather episode information
     *
     * @param param to map id, season and episode
     * @return a EpisodeDTO
     */
    @GetMapping("/details/episode")
    public EpisodeDTO getDetailedEpisode(@RequestParam Map<String, String> param) {
        int movieDBId = Integer.parseInt(param.get(ReqConst.SHOWID));
        int season = Integer.parseInt(param.get(ReqConst.SEASON));
        int episode = Integer.parseInt(param.get(ReqConst.EPISODE));
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
}
