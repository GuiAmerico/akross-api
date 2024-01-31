package br.com.akross.akrossapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class AkrossApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AkrossApiApplication.class, args);
	}

}
