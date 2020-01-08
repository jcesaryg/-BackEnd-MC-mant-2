package com.julio.backendmc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.julio.backendmc.domain.Categoria;
import com.julio.backendmc.repositories.CategoriaRepository;

@SpringBootApplication
public class BackEndMcApplication implements CommandLineRunner { //CommandLineRunner Permite implementar un metodo auxiliar para ejecutar alguna opcion

	@Autowired
	private CategoriaRepository categoriaRepository; // Dependencia 
	
	public static void main(String[] args) {
		SpringApplication.run(BackEndMcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
		Categoria cat1 = new Categoria(null, "Informatica");//Se instancia datos  para ser llenado a la categoria
		Categoria cat2 = new Categoria(null, "Escritorio"); 
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		
	}
}
