package com.lfaiska.marvel.graphql.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfaiska.marvel.graphql.Application;
import com.lfaiska.marvel.graphql.entity.Character;
import com.lfaiska.marvel.graphql.entity.Response;
import org.eclipse.jetty.http.HttpMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.bind.DatatypeConverter;
import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static jdk.nashorn.internal.runtime.regexp.joni.Config.log;

@Service
public class CharacterService extends MarvelService {

    private final RestTemplate appRestClient;
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    public CharacterService(@Qualifier("appRestClient") RestTemplate appRestClient) {
        this.appRestClient = appRestClient;
    }

    public List<Character> getCharacteres() {
        ObjectMapper mapper = new ObjectMapper();
        List<Character> characterList = new ArrayList<>();

        Response response = appRestClient.getForObject(getTargetUrl("/v1/public/characters"), Response.class);

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
