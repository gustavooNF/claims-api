package com.fiap.claimsapi;

import com.fiap.claimsapi.interfaces.service.aws.MessageQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
@EnableAutoConfiguration
public class ClaimsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClaimsApiApplication.class, args);
	}

}
