package com.tvapp.thetvdb;

import com.tvapp.model.Token;
import com.tvapp.themoviedb.domain.MovieDBShowResult;
import com.tvapp.themoviedb.domain.Result;
import com.tvapp.thetvdb.domain.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;

import static com.tvapp.utils.constants.UrlConstants.*;

/**
 * @author Patrik Holmkvist & Patrik Olin
 * Datum: 2018-12-03
 * Kurs: Java EE
 * Labb: Projekt
 */
public class TheTVDBDAO {

    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private UriComponentsBuilder builder;

    private void setHeader(String token) {
        String authHeader = "Bearer " + token;

        headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * Search for a show
     *
     * @param search query
     * @return a List<> of Result
     */
    public List<Result> searchShow(String search, String token) {
        setHeader(token);
        restTemplate = new RestTemplate();

        builder = UriComponentsBuilder.fromHttpUrl(TVDB_SEARCH_URL)
                .queryParam("name", search);

        HttpEntity<?> entity = new HttpEntity<>(headers);


        ResponseEntity<MovieDBShowResult> response = restTemplate.exchange(
                builder.build().toUriString(),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<MovieDBShowResult>() {
                }
        );


        return response.getBody().getResults();
    }

    /**
     * Retrieve more details of a show from TvDB
     *
     * @param id    of show
     * @param token to gain access to tvDB
     * @return a Detailed show
     * @throws HttpClientErrorException
     */
    public TVDBShowDetails showDetails(int id, String token) throws HttpClientErrorException {
        setHeader(token);
        restTemplate = new RestTemplate();

        builder = UriComponentsBuilder.fromHttpUrl(TVDB_SHOW_DETAILS_URL + id);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<DataShowDetails> response = restTemplate.exchange(
                builder.build().toUriString(),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<DataShowDetails>() {
                }
        );

        return response.getBody().getData();
    }

    /**
     * Retrieve a episode from TvDB
     *
     * @param id      of Show
     * @param season  number
     * @param episode number
     * @param token   to gain access to TvDB
     * @return a episode
     */
    public List<TVDBEpisode> getEpisode(int id, int season, int episode, String token) {
        setHeader(token);
        restTemplate = new RestTemplate();

        builder = UriComponentsBuilder.fromHttpUrl(TVDB_SHOW_DETAILS_URL + id + TVDB_EPISODE_QUERY)
                .queryParam("airedSeason", season)
                .queryParam("airedEpisode", episode);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<DataEpisode> response = restTemplate.exchange(
                builder.build().toUriString(),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<DataEpisode>() {
                }
        );
        return response.getBody().getData();
    }

    /**
     * Refresh token if neccessary
     *
     * @param token to refresh
     * @return the token
     */
    public Token refreshToken(Token token) {
        restTemplate = new RestTemplate();

        headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        com.tvapp.thetvdb.domain.Token body = new com.tvapp.thetvdb.domain.Token(
                token.getApiKey(),
                token.getUserKey(),
                token.getUserName());

        HttpEntity<?> entity = new HttpEntity<>(body, headers);

        builder = UriComponentsBuilder.fromHttpUrl(TVDB_LOGIN_URL);

        ResponseEntity<Token> response = restTemplate.exchange(
                builder.build().toUriString(),
                HttpMethod.POST,
                entity,
                Token.class
        );

        return response.getBody();
    }


}
