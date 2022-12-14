package com.crisalis.prueba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"com.crisalis.controllers"})
@EnableJpaRepositories(basePackages = "com.crisalis.repositories")
@EntityScan(basePackages = "com.crisalis.models")
@SpringBootApplication

public class CrisalisFinnegans2Application {

	public static void main(String[] args) {
		SpringApplication.run(CrisalisFinnegans2Application.class, args);
	}

}
