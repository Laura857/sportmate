package com.example.sportmate.config;

import com.example.sportmate.DataTest;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.impl.DefaultClaims;
import io.jsonwebtoken.impl.DefaultJws;
import io.jsonwebtoken.impl.DefaultJwsHeader;
import io.jsonwebtoken.impl.DefaultJwtParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;

import static com.example.sportmate.config.JWTAuthorizationFilter.HEADER;
import static com.example.sportmate.config.JWTAuthorizationFilter.SECRET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class JWTAuthorizationFilterTest implements DataTest {
    @MockBean
    JwtParser jwtParser;

    @Autowired
    JWTAuthorizationFilter jwtAuthorizationFilter;

    @Test
    void checkJWTToken_should_return_true_when_token_contains_bearer_prefix() {
        final MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addHeader(HEADER, TOKEN_WITH_BEARER_PREFIX);

        assertThat(jwtAuthorizationFilter.checkJWTToken(mockRequest))
                .isTrue();
    }

    @Test
    void checkJWTToken_should_return_false_when_token_not_contains_bearer_prefix() {
        final MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addHeader(HEADER, TOKEN_WITHOUT_BEARER_PREFIX);

        assertThat(jwtAuthorizationFilter.checkJWTToken(mockRequest))
                .isFalse();
    }

    @Test
    void checkJWTToken_should_return_false_when_token_is_null() {
        final MockHttpServletRequest mockRequest = new MockHttpServletRequest();

        assertThat(jwtAuthorizationFilter.checkJWTToken(mockRequest))
                .isFalse();
    }

    /*@Test
    void validateToken_should_return_claims_token() {
        final MockHttpServletRequest mockRequest = new MockHttpServletRequest();
        mockRequest.addHeader(HEADER, "toto");

        jwtParser.setSigningKey(SECRET.getBytes());
        when(jwtParser.parseClaimsJws("toto"))
                .thenReturn(new DefaultJws<>(new DefaultJwsHeader(), new DefaultClaims(), ""));

        assertThat(jwtAuthorizationFilter.validateToken(mockRequest))
                .isEqualTo(new DefaultClaims());
    }*/
}