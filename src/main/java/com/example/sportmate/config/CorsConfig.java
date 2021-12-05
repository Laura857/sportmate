package com.example.sportmate.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfig implements WebMvcConfigurer {
    public static final String[] REQUEST_METHOD_SUPPORTED = {"GET", "POST", "PUT", "DELETE"};
    public static final String[] REQUEST_ORIGIN_ALLOWED = {
            "http://localhost:3000"
    };

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry){
        corsRegistry.addMapping("/api/**")
                .allowedOrigins(REQUEST_ORIGIN_ALLOWED)
                .allowedMethods(REQUEST_METHOD_SUPPORTED)
                .allowCredentials(false);
    }
}
