package com.tvapp.utils.services;

import com.tvapp.Constants;
import com.tvapp.model.ApiModel;
import com.tvapp.repository.ApiRepository;
import com.tvapp.thetvdb.TheTVDBDAO;

import java.util.Calendar;
import java.util.Date;

public class TokenService {

    private ApiRepository apiRepository;
    private TheTVDBDAO theTVDBDAO;

    public TokenService(ApiRepository apiRepository) {
        this.apiRepository = apiRepository;
        this.theTVDBDAO = new TheTVDBDAO();
    }

    /**
     * Checks if token has expired
     * @return the ApiModel for TVDB
     */
    public ApiModel checkExpirationDateForTVDBToken() {
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
    public ApiModel getApiKeyForMovieDB() {
        return apiRepository.findByName(Constants.MOVIE_DB);
    }

    /**
     * Refresh the token for TVDB
     * @param apiToken is refreshed and
     * @return the token
     */
    public ApiModel refreshTokenForTVDB(ApiModel apiToken) {
        apiToken.setToken(theTVDBDAO.refreshToken(apiToken).getToken());
        apiToken.setCreationDate(new Date());
        return apiRepository.save(apiToken);
    }
}
