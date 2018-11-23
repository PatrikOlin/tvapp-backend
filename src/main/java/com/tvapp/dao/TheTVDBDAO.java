package com.tvapp.dao;

import com.tvapp.model.ApiModel;
import com.tvapp.repository.ApiRepository;
import com.tvapp.themoviedb.Result;
import com.tvapp.themoviedb.ShowResult;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

public class TheTVDBDAO {

    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private String token;

    private final static String SEARCH_URL = "https://api.thetvdb.com/search/series";

    public TheTVDBDAO(String token) {

        setHeader();
    }

    public void setHeader() {
        String authHeader = getToken();

        headers = new HttpHeaders();
        headers.set("Authorization", authHeader);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    }

    public String getToken() {
//        ApiModel model = apiRepository.findByName("TheTVDB");
        return null;//"Bearer " + model.getToken();
    }

    public void refreshToken() {

    }

    public List<Result> SearchShow(String search) {
        restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(SEARCH_URL)
                .queryParam("name", search);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<ShowResult> response = restTemplate.exchange(
                builder.build().toUriString(),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<ShowResult>() {
                }
        );


        return response.getBody().getResults();
    }


}
