package com.lfaiska.marvel.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import com.lfaiska.marvel.graphql.entity.Character;
import com.lfaiska.marvel.graphql.repository.CharacterRepository;
import com.lfaiska.marvel.graphql.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class QueryResolver implements GraphQLQueryResolver {

    @Autowired
    CharacterRepository repository;

    @Autowired
    CharacterService service;

    public Iterable<Character> findAllCharacters() {
        List<Character> characters = repository.findAll();
        //@TODO find a way to search this value in API
        if (characters.size() < 1491) {
            characters = service.getAllCharacters();
            repository.save(characters);
        }
        return characters;
    }

    public long countCharacters() {
        return repository.count();
    }
}
