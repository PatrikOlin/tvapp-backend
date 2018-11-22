package com.tvapp.dao;

import com.tvapp.domain.Result;
import com.tvapp.domain.ShowResult;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowDAO {
    private final static String THEMOVIEDBKEY = "d8c2bb4b3c15ea3ac0f2e1e6e2439eef";

    private static String url = "https://api.themoviedb.org/3/search/tv";

    public List<Result> getShow(String search) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
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
}
