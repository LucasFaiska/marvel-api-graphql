package com.lfaiska.marvel.graphql.controller;

import com.lfaiska.marvel.graphql.service.CharacterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/characters")
public class CharacterController {

    @Autowired
    private CharacterService characterService;

    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    public String getAllCharacters(Model model){
        model.addAttribute("", characterService.getCharacteres(100, 100));
        return "jsonTemplate";
    }

}
