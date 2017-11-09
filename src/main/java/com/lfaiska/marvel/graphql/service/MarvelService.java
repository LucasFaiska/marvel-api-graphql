package com.lfaiska.marvel.graphql.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.bind.DatatypeConverter;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public class MarvelService {
    protected UriComponentsBuilder targetUrlBuilder;

    @Value("${marvel.keys.public}")
    protected String publicKey;

    @Value("${marvel.keys.private}")
    protected String privateKey;

    protected MessageDigest md;

    public MarvelService() {
        targetUrlBuilder = UriComponentsBuilder.fromUriString("http://gateway.marvel.com/");
    }

    protected URI getTargetUrl(String path) {
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
