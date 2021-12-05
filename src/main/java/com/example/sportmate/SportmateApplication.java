package com.example.sportmate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SportmateApplication extends SpringBootServletInitializer {
	@GetMapping("/")
	String home(){
		return "Spring is here";
	}

	public static void main(String[] args) {
		SpringApplication.run(SportmateApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(SportmateApplication.class);
	}

}
