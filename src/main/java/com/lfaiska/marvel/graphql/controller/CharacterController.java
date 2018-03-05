package com.lfaiska.marvel.graphql.controller;

import com.lfaiska.marvel.graphql.entity.Character;
import com.lfaiska.marvel.graphql.repository.CharacterRepository;
import com.lfaiska.marvel.graphql.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/characters")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @Autowired
    private CharacterRepository characterRepository;

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public @ResponseBody
    List<Character> getAllCharacters(Model model){
        List<Character> characters = characterRepository.findAll();
        //@TODO find a way to search this value in API
        if (characters.size() < 1491) {
            characters = fetchAllCharactersFromMarvelApi();
        }
        return characters;
    }

    private List<Character> fetchAllCharactersFromMarvelApi() {
        List<Character> characters = new ArrayList<>();
        int total = 1500;
        int offset = 100;
        IntStream list = IntStream.rangeClosed(0, total/offset);
        list.forEach(i -> characters.addAll(characterService.getCharacteres(100, offset*i)));
        return characters;
    }
}
