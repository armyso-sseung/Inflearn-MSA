package com.example.apigatewayservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class GatewaysController {
    private final Environment env;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in Gateway Service"
                + "\nPORT(local.server.port) = " + env.getProperty("local.server.port")
                + "\nPORT(server.port) = " + env.getProperty("server.port")
                + "\ntoken secret = " + env.getProperty("token.secret")
                + "\ntoken expiration time = " + env.getProperty("token.expiration_time"));
    }
}
