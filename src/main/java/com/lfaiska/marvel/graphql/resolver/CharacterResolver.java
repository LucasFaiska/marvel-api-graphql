package com.lfaiska.marvel.graphql.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.lfaiska.marvel.graphql.entity.Character;
import com.lfaiska.marvel.graphql.entity.Thumbnail;

public class CharacterResolver implements GraphQLResolver<Character> {

    public Thumbnail getThumbnail(Character character) {
        return character.getThumbnail();
    }
}
