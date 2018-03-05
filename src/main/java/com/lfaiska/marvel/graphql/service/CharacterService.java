package com.lfaiska.marvel.graphql.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lfaiska.marvel.graphql.entity.Character;
import com.lfaiska.marvel.graphql.entity.Response;
import com.lfaiska.marvel.graphql.repository.CharacterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CharacterService extends MarvelService {

    private final RestTemplate appRestClient;

    @Autowired
    private CharacterRepository characterRepository;

    @Autowired
    public CharacterService(@Qualifier("appRestClient") RestTemplate appRestClient) {
        this.appRestClient = appRestClient;
    }

    public List<Character> getCharacteres(int limit, int offset) {
        ObjectMapper mapper = new ObjectMapper();
        List<Character> characterList = new ArrayList<>();

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("limit", String.valueOf(limit));
        parameters.add("offset", String.valueOf(offset));

        Response response = appRestClient.getForObject(getTargetUrl("/v1/public/characters", parameters), Response.class);

        if (response.getCode().equals("200")) {
            response.getData().getResults().forEach(item -> {
                try {
                    Character character = buildCharacter(mapper, item);
                    characterList.add(character);
                    saveCharacter(character);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            });
            return characterList;
        } else {
            return null;
        }
    }

    private Character buildCharacter(ObjectMapper mapper, TreeNode item) throws JsonProcessingException {
        return  new Character(mapper.treeToValue(item, Character.class).getId(),
                mapper.treeToValue(item, Character.class).getName(),
                mapper.treeToValue(item, Character.class).getDescription(),
                mapper.treeToValue(item, Character.class).getThumbnail());
    }

    private void saveCharacter(Character character) {
        if (!characterRepository.exists(character.getId())) {
            characterRepository.save(character);
        }
    }
}
