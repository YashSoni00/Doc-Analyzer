package com.example.docanalyzer.error;

import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;

public class ErrorPages implements ErrorPageRegistrar {
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        registry.addErrorPages(new org.springframework.boot.web.server.ErrorPage("/error"));
    }
}
