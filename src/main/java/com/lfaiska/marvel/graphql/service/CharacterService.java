package com.lfaiska.marvel.graphql.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfaiska.marvel.graphql.entity.Character;
import com.lfaiska.marvel.graphql.entity.Response;
import org.eclipse.jetty.http.HttpMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Service
public class CharacterService extends MarvelService {

    private final RestTemplate appRestClient;

    @Autowired
    public CharacterService(@Qualifier("appRestClient") RestTemplate appRestClient) {
        this.appRestClient = appRestClient;
    }

    public List<Character> getCharacteres() {
        ObjectMapper mapper = new ObjectMapper();
        List<Character> characterList = new ArrayList<>();

        URI targetUrl = targetUrlBuilder.path("/v1/public/characters")
                .queryParam("ts", "123456789")
                .queryParam("apikey", "3178e20fab3bd91fdc47872159cbcc87")
                .queryParam("hash", "5292eb891193e819f2f0184c3a3f40c9")
                .build()
                .encode()
                .toUri();

        Response response = appRestClient.getForObject(targetUrl, Response.class);

        if (response.getCode().equals("200")) {
            response.getData().getResults().forEach(item -> {
                try {
                    characterList.add(mapper.treeToValue(item, Character.class));
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
            return characterList;
        } else {
            return null;
        }
    }
}
