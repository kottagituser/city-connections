package com.mc.code.challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class McCodeChallengeApplication {

	public static void main(String[] args) {
		//System.out.println("Authorization=Basic "+ Base64.getEncoder().encodeToString(("mastercard:mastercard").getBytes()));
		SpringApplication.run(McCodeChallengeApplication.class, args);
	}

}
