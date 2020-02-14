package com.julio.backendmc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.julio.backendmc.services.DBService;

@Configuration
@Profile("test") // Se escoje a test de application.properties
public class TestConfig {

	@Autowired
	private DBService dbService;

	@Bean
	public boolean instantiateDatabase() throws ParseException {

		dbService.instantiateTestDatabase();
		return true;

	}
}
