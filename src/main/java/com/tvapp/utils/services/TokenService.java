package com.tvapp.utils.services;

import com.tvapp.utils.constants.UrlConstants;
import com.tvapp.model.Token;
import com.tvapp.repository.ApiRepository;
import com.tvapp.thetvdb.TheTVDBDAO;

import java.util.Calendar;
import java.util.Date;
/**
 * @author Patrik Holmkvist & Patrik Olin
 * Datum: 2018-12-03
 * Kurs: Java EE
 * Labb: Projekt
 */
public class TokenService {

    private ApiRepository apiRepository;
    private TheTVDBDAO theTVDBDAO;

    public TokenService(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
        this.theTVDBDAO = new TheTVDBDAO();
    }

    /**
     * Checks if token has expired
     * @return the Token for TVDB
     */
    public Token checkExpirationDateForTVDBToken() {
        Token apiToken = apiRepository.findByName(UrlConstants.THE_TV_DB);
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
    public Token getApiKeyForMovieDB() {
        return apiRepository.findByName(UrlConstants.MOVIE_DB);
    }

    /**
     * Refresh the token for TVDB
     * @param apiToken is refreshed and
     * @return the token
     */
    public Token refreshTokenForTVDB(Token apiToken) {
        apiToken.setToken(theTVDBDAO.refreshToken(apiToken).getToken());
        apiToken.setCreationDate(new Date());
        return apiRepository.save(apiToken);
    }
}
