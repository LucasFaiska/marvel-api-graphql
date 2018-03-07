package com.lfaiska.marvel.graphql.client;

import com.lfaiska.marvel.graphql.entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.bind.DatatypeConverter;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Component
public class MarvelClient {
    @Value("${marvel.base_url}")
    protected String baseUrl;

    @Value("${marvel.keys.public}")
    protected String publicKey;

    @Value("${marvel.keys.private}")
    protected String privateKey;

    protected MessageDigest md;

    private final RestTemplate appRestClient;

    @Autowired
    public MarvelClient(@Qualifier("appRestClient") RestTemplate appRestClient) {
        this.appRestClient = appRestClient;
    }

    public Response getCharacters(MultiValueMap<String, String> parameters) {
        return appRestClient.getForObject(buildUrl("/v1/public/characters", parameters), Response.class);
    }

    protected URI buildUrl(String path, MultiValueMap<String, String> parameters) {
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
