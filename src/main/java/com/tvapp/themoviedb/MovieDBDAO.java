package com.tvapp.themoviedb;

import com.tvapp.themoviedb.domain.*;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static com.tvapp.Constants.*;
public class MovieDBDAO {

    private RestTemplate restTemplate;
    private HttpHeaders headers;
    private String apiKey;

    public MovieDBDAO(String apiKey) {
        this.apiKey = apiKey;
        headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
    }

    public List<Result> searchShows(String search) {
        restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(MOVIEDB_SEARCH_URL)
                .queryParam("api_key", apiKey)
                .queryParam("query", search);


        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<MovieDBShowResult> response = restTemplate.exchange(
                builder.build().toUriString(),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<MovieDBShowResult>() {
                }
        );

//        HttpEntity<String> response = restTemplate.getForEntity(builder.build().encode().toUri(), String.class);
        return response.getBody().getResults();
    }

    public MovieDBShowDetails ShowDetails(int id) {
        restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(MOVIEDB_SHOW_DETAILS_URL + id)
                .queryParam("api_key", apiKey)
                .queryParam("append_to_response", "external_ids");

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<MovieDBShowDetails> response = restTemplate.exchange(
                builder.build().toUriString(),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<MovieDBShowDetails>() {
                }
        );

        return response.getBody();
    }

    public MovieDBSeason ShowSeason(String id, String seasonNo) {
        restTemplate = new RestTemplate();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(MOVIEDB_SHOW_DETAILS_URL + id + "/season/" + seasonNo)
                .queryParam("api_key", apiKey);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<MovieDBSeason> response = restTemplate.exchange(
                builder.build().toUriString(),
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<MovieDBSeason>() {
                }
        );

        return response.getBody();
    }

    // TODO: 2018-11-28  
    public MovieDBEpisode getEpisode() {
        return null;
    }

}
