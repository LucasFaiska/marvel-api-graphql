package com.lfaiska.marvel.graphql;

import com.lfaiska.marvel.graphql.data.entities.Character;
import com.lfaiska.marvel.graphql.data.network.Response;
import com.lfaiska.marvel.graphql.data.services.CharacterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		CharacterService characterService = new CharacterService(new RestTemplateBuilder());
		List<Character> characterList = characterService.getCharacteres();
		log.info(characterList.get(0).getName());
	}
}
