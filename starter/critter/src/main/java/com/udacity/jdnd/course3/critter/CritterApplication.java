package com.udacity.jdnd.course3.critter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Launches the Spring application. Unmodified from starter code.
 */
@SpringBootApplication
@EnableJpaRepositories
public class CritterApplication {

	public static void main(String[] args) {
		SpringApplication.run(CritterApplication.class, args);
	}

}
