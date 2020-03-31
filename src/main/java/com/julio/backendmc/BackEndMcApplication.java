package com.julio.backendmc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackEndMcApplication implements CommandLineRunner { // CommandLineRunner Permite implementar un metodo
																	// auxiliar para ejecutar alguna opcion

	public static void main(String[] args) {
		SpringApplication.run(BackEndMcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}