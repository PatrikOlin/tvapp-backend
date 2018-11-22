package com.tvapp.dao;

import com.tvapp.themoviedb.Result;
import com.tvapp.themoviedb.ShowResult;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class ShowDAO {
    private final static String THEMOVIEDBKEY = "d8c2bb4b3c15ea3ac0f2e1e6e2439eef";

    private final static String SEARCH_URL = "https://api.themoviedb.org/3/search/tv";
    private final static String EPISODE_URL = "https://api.themoviedb.org/3/tv";


    public List<Result> getShows(String search) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(SEARCH_URL)
                .queryParam("api_key", THEMOVIEDBKEY)
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

    public String ShowDetails() {
        return null;
    }


}
