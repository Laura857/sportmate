package com.example.sportmate;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SportmateApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void homeResponse() {
		final TestRestTemplate testRestTemplate = new TestRestTemplate();
		final String body = testRestTemplate.getForObject("http://127.0.0.1:8080/", String.class);
		assertThat(body).isEqualTo("Spring is here");
	}

}
