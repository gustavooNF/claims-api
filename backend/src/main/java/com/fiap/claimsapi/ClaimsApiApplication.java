package com.fiap.claimsapi;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class ClaimsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClaimsApiApplication.class, args);
	}

}
