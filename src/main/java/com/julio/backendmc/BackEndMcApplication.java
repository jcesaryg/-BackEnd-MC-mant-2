package com.julio.backendmc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.julio.backendmc.domain.Categoria;
import com.julio.backendmc.domain.Produto;
import com.julio.backendmc.repositories.CategoriaRepository;
import com.julio.backendmc.repositories.ProdutoRepository;

@SpringBootApplication
public class BackEndMcApplication implements CommandLineRunner { //CommandLineRunner Permite implementar un metodo auxiliar para ejecutar alguna opcion

	@Autowired
	private CategoriaRepository categoriaRepository; // Dependencia 
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(BackEndMcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
		//Instanciamiento de las categorias
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio"); 
		
		//Instaciomiento de los Productos 
		Produto p1 = new Produto(null, "Computador",2000.00);
		Produto p2 = new Produto(null, "Impresora",800.00);		
		Produto p3 = new Produto(null, "Mouse",80.00);				
		
		//Se crea la relacion de los productos basado en "instância do modelo conceitual:" Pag 03  del documento 02-implementacao-de-modelo-conceitual
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		//Se hace llamado al repository para que pueda almacenar todos los productos
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
	}
}
