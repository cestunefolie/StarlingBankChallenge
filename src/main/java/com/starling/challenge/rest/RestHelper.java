package com.starling.challenge.rest;

import org.springframework.http.HttpHeaders;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

public final class RestHelper {
    public static HttpHeaders getHeaders(String token) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        headers.setAccept(List.of(APPLICATION_JSON));
        headers.setBearerAuth(token);
        return headers;
    }
}
