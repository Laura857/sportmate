package com.example.sportmate.config;

import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@SpringBootTest
class CorsConfigTest {
    @Autowired
    CorsConfig corsConfig;

    @Test
    void addCorsMappings() {
        final CorsRegistry corsRegistry = new CorsRegistry();

        assertDoesNotThrow(() -> corsConfig.addCorsMappings(corsRegistry));

        /*final CorsRegistry corsRegistryResponse = new CorsRegistry();
        corsRegistryResponse.addMapping("/api/**")
                .allowedOrigins(String.valueOf(asList("GET", "POST", "PUT", "DELETE")))
                .allowedMethods("http://localhost:3000")
                .allowCredentials(false);

        assertThat(corsRegistryResponse)
                .isEqualTo(corsRegistry);*/
    }

}