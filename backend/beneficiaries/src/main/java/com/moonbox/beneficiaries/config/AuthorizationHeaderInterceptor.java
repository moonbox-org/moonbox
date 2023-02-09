package com.moonbox.beneficiaries.config;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.io.IOException;

public class AuthorizationHeaderInterceptor implements ClientHttpRequestInterceptor {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer ";

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        Jwt token = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpHeaders headers = request.getHeaders();
        headers.add(AUTHORIZATION_HEADER, BEARER + token.getTokenValue());
        return execution.execute(request, body);
    }
}
