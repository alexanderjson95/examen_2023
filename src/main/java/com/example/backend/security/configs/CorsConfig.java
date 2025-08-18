package com.example.backend.security.configs;

import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class CorsConfig {
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration config = new CorsConfiguration();
        // för dev
        List<String> ports = List.of("8080");
        List<String> patterns = List.of("GET", "POST", "PUT", "DELETE");
        config.setAllowedOriginPatterns(ports);
        config.setAllowedMethods(patterns);
        config.setAllowCredentials(true);

        // jwt + content
        config.setExposedHeaders(Arrays.asList("Authorization",  "Content-Type"));
        config.setAllowedHeaders(Arrays.asList("Authorization",  "Content-Type"));

        UrlBasedCorsConfigurationSource src = new UrlBasedCorsConfigurationSource();
        src.registerCorsConfiguration("/**", config);
        return src;
    }
}
