package com.lfaiska.marvel.graphql.data.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfaiska.marvel.graphql.data.entities.Character;
import com.lfaiska.marvel.graphql.data.network.Response;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import javax.validation.Payload;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CharacterService {
    private final RestTemplate restTemplate;

    public CharacterService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public List<Character> getCharacteres() {
        ObjectMapper mapper = new ObjectMapper();
        List<Character> characterList = new ArrayList<>();
        Response response = restTemplate.getForObject("http://gateway.marvel.com/v1/public/characters?ts=123456789&apikey=3178e20fab3bd91fdc47872159cbcc87&hash=5292eb891193e819f2f0184c3a3f40c9", Response.class);
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
