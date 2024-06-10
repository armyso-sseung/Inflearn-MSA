package com.example.userservice.security;

import com.example.userservice.service.UserService;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.util.matcher.IpAddressMatcher;

import java.util.function.Supplier;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurity {
    private final BCryptPasswordEncoder passwordEncoder;
    private final Environment env;

    private final UserService userService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        IpAddressMatcher hasIpAddress = new IpAddressMatcher("192.162.27.0/24");

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userService).passwordEncoder(passwordEncoder);
        AuthenticationManager authenticationManager = authenticationManagerBuilder.build();

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests.requestMatchers("/**").access((authentication, context) -> new AuthorizationDecision(hasIpAddress.matches(context.getRequest()))))
                .addFilter(getAuthenticationFilter(authenticationManager))
                .authenticationManager(authenticationManager);



        return http.build();
    }

    private AuthenticationFilter getAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter();
        authenticationFilter.setAuthenticationManager(authenticationManager);

        return authenticationFilter;
    }
}
