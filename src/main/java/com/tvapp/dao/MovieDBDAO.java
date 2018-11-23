package com.tvapp.dao;

import com.tvapp.themoviedb.Result;
import com.tvapp.themoviedb.Season;
import com.tvapp.themoviedb.ShowDetails;
import com.tvapp.themoviedb.ShowResult;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class MovieDBDAO {

    private RestTemplate restTemplate;
    private HttpHeaders headers;

    private final static String THE_MOVIE_DB_KEY = "d8c2bb4b3c15ea3ac0f2e1e6e2439eef";
    private final static String SEARCH_URL = "https://api.themoviedb.org/3/search/tv";
    private final static String SHOW_DETAILS_URL = "https://api.themoviedb.org/3/tv/";

    public MovieDBDAO() {
        headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    }

    public List<Result> searchShows(String search) {
        restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(SEARCH_URL)
                .queryParam("api_key", THE_MOVIE_DB_KEY)
                .queryParam("query", search);


        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<ShowResult> response = restTemplate.exchange(
                builder.build().toUriString(),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<ShowResult>() {
                }
        );

//        HttpEntity<String> response = restTemplate.getForEntity(builder.build().encode().toUri(), String.class);
        return response.getBody().getResults();
    }

    public ShowDetails ShowDetails(String id) {
        restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(SHOW_DETAILS_URL + id)
                .queryParam("api_key", THE_MOVIE_DB_KEY)
                .queryParam("append_to_response", "external_ids");

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<ShowDetails> response = restTemplate.exchange(
                builder.build().toUriString(),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<ShowDetails>() {
                }
        );

        return response.getBody();
    }

    public Season ShowSeason(String id, String seasonNo) {
        restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(SHOW_DETAILS_URL + id + "/season/" + seasonNo)
                .queryParam("api_key", THE_MOVIE_DB_KEY);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<Season> response = restTemplate.exchange(
                builder.build().toUriString(),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<Season>() {
                }
        );

        return response.getBody();
    }

}
