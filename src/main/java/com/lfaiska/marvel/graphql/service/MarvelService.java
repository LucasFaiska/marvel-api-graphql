package com.lfaiska.marvel.graphql.service;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public class MarvelService {
    protected UriComponentsBuilder targetUrlBuilder;

    public MarvelService() {
        targetUrlBuilder = UriComponentsBuilder.fromUriString("http://gateway.marvel.com/");
    }
}
