package com.lfaiska.marvel.graphql.controller;

import com.lfaiska.marvel.graphql.entity.Character;
import com.lfaiska.marvel.graphql.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/characters")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public @ResponseBody
    List<Character> getAllCharacters(Model model){
        return characterService.getCharacteres(100, 0);
    }
}
