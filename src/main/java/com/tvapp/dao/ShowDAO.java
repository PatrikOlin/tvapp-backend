package com.tvapp.dao;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ShowDAO {
    private final static String THEMOVIEDBKEY = "d8c2bb4b3c15ea3ac0f2e1e6e2439eef";

    private static String url = "https://api.themoviedb.org/3/search/tv";

//    public void getShow(String search) {
//        final String uri = String.format("https://api.themoviedb.org/3/search/tv?api_key=%s&query=%s", THEMOVIEDBKEY, search);
//        String result = "";
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//
//        Map<String, String> params = new HashMap<String, String>();
//        //params.put("api_key", "api_key=" + THEMOVIEDBKEY);
//        //params.put("query", "query=" + search);
//
//        try {
//        result = restTemplate.getForObject(uri, String.class);
//        } catch (HttpClientErrorException ex) {
//            String api = restTemplate.getUriTemplateHandler().toString();
//            System.out.println(ex.getStackTrace());
//            System.out.println(params);
//        }
//
//        System.out.println(result);
//
//    }

    public String getShow(String search) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("api_key", THEMOVIEDBKEY)
                .queryParam("query", search);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        HttpEntity<String> response = restTemplate.exchange(
                builder.build().toUriString(),
                HttpMethod.GET,
                entity,
                String.class
        );

//        HttpEntity<String> response = restTemplate.getForEntity(builder.build().encode().toUri(), String.class);

        return response.getBody();
    }
}
