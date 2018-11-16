package com.tvapp.dao;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ShowDAO {
    private final static String THEMOVIEDBKEY = "d8c2bb4b3c15ea3ac0f2e1e6e2439eef";

    public void getShow(String search) {
        final String uri = String.format("https://api.themoviedb.org/3/search/tv?api_key=%s&query=%s", THEMOVIEDBKEY, search);
        String result = "";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        Map<String, String> params = new HashMap<String, String>();
        //params.put("api_key", "api_key=" + THEMOVIEDBKEY);
        //params.put("query", "query=" + search);

        try {
        result = restTemplate.getForObject(uri, String.class);
        } catch (HttpClientErrorException ex) {
            String api = restTemplate.getUriTemplateHandler().toString();
            System.out.println(ex.getStackTrace());
            System.out.println(params);
        }

        System.out.println(result);

    }
}
