package com.lfaiska.marvel.graphql.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.bind.DatatypeConverter;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;

public class MarvelService {
    @Value("${marvel.base_url}")
    protected String baseUrl;

    @Value("${marvel.keys.public}")
    protected String publicKey;

    @Value("${marvel.keys.private}")
    protected String privateKey;

    protected MessageDigest md;

    public MarvelService() {}

    protected URI getTargetUrl(String path, MultiValueMap<String, String> parameters) {
        UriComponentsBuilder targetUrlBuilder = UriComponentsBuilder.fromUriString(baseUrl);
        String ts = String.valueOf(new Date().getTime());
        return targetUrlBuilder.path(path)
                .queryParam("ts", ts)
                .queryParam("apikey", publicKey)
                .queryParam("hash", getHash(ts))
                .queryParams(parameters)
                .build()
                .encode()
                .toUri();
    }

    protected URI getTargetUrl(String path) {
        UriComponentsBuilder targetUrlBuilder = UriComponentsBuilder.fromUriString(baseUrl);
        String ts = String.valueOf(new Date().getTime());
        return targetUrlBuilder.path(path)
                .queryParam("ts", ts)
                .queryParam("apikey", publicKey)
                .queryParam("hash", getHash(ts))
                .build()
                .encode()
                .toUri();
    }

    private String getHash(String ts) {
        try {
            md = MessageDigest.getInstance("MD5");
            return DatatypeConverter.printHexBinary(md.digest((ts + privateKey + publicKey).getBytes())).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
