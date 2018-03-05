package com.lfaiska.marvel.graphql.repository;

import com.lfaiska.marvel.graphql.entity.Character;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CharacterRepository extends MongoRepository<Character, String> {
}
