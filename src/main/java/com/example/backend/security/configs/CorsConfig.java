package com.example.backend.security.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component

public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        // för dev
        config.setAllowedOriginPatterns(List.of("http://localhost:8080", "http://localhost:5173"));
        List<String> patterns = List.of("GET", "POST", "PUT", "DELETE");
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        config.setAllowCredentials(true);
        config.setExposedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));

        // jwt + content
        config.setExposedHeaders(Arrays.asList("Authorization",  "Content-Type"));
        config.setAllowedHeaders(Arrays.asList("Authorization",  "Content-Type"));

        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
        src.registerCorsConfiguration("/**", config);
        return src;
    }
}
