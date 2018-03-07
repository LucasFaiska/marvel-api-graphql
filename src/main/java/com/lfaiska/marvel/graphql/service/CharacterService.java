package com.lfaiska.marvel.graphql.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfaiska.marvel.graphql.client.MarvelClient;
import com.lfaiska.marvel.graphql.entity.Character;
import com.lfaiska.marvel.graphql.entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

@Service
public class CharacterService {

    @Autowired
    private MarvelClient client;

    public List<Character> getCharacteres(int limit, int offset) {
        ObjectMapper mapper = new ObjectMapper();
        List<Character> characterList = new ArrayList<>();
        Response response = client.getCharacters(buildParameters(limit, offset));

        if (response.getCode().equals("200")) {
            response.getData().getResults().forEach(item -> {
                try {
                    Character character = mapper.treeToValue(item, Character.class);
                    characterList.add(character);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
            return characterList;
        } else {
            return null;
        }
    }

    private MultiValueMap<String, String> buildParameters(int limit, int offset) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("limit", String.valueOf(limit));
        parameters.add("offset", String.valueOf(offset));
        return parameters;
    }

}
