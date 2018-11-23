package com.tvapp.thetvdb;

import com.tvapp.themoviedb.domain.MovieDBShowResult;
import com.tvapp.themoviedb.domain.Result;
import com.tvapp.thetvdb.domain.DataShowDetails;
import com.tvapp.thetvdb.domain.TVDBShowDetails;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class TheTVDBDAO {

    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private String token;
    private UriComponentsBuilder builder;

    private final static String SEARCH_URL = "https://api.thetvdb.com/search/series";
    private final static String SHOW_DETAILS_URL = "https://api.thetvdb.com/series/";

    public TheTVDBDAO(String token) {
        this.token = token;
        setHeader();
    }

    private void setHeader() {
        String authHeader = "Bearer " + token;

        headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    }

    /**
     * Search for a show
     * @param search query
     * @return a List<> of Result
     */
    public List<Result> searchShow(String search) {
        restTemplate = new RestTemplate();

        builder = UriComponentsBuilder.fromHttpUrl(SEARCH_URL)
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

    public TVDBShowDetails showDetails(String id) {
        restTemplate = new RestTemplate();

        builder = UriComponentsBuilder.fromHttpUrl(SHOW_DETAILS_URL + id);

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


}
