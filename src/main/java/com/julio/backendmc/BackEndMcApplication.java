package com.julio.backendmc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.julio.backendmc.domain.Categoria;
import com.julio.backendmc.domain.Cidade;
import com.julio.backendmc.domain.Estado;
import com.julio.backendmc.domain.Produto;
import com.julio.backendmc.repositories.CategoriaRepository;
import com.julio.backendmc.repositories.CidadeRepository;
import com.julio.backendmc.repositories.EstadoRepository;
import com.julio.backendmc.repositories.ProdutoRepository;

@SpringBootApplication
public class BackEndMcApplication implements CommandLineRunner { //CommandLineRunner Permite implementar un metodo auxiliar para ejecutar alguna opcion

	@Autowired
	private CategoriaRepository categoriaRepository; // Dependencia 
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	//Repositorios para categoria y estado
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
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

		//------- INSTANCIACION DE ESTADOS Y CIUDADES -------
		//Instanciar Estados
		Estado est1 = new Estado(null,"Mina gerais");
		Estado est2 = new Estado(null,"Sao Paulo");

		//Instanciar Ciudades
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "Sao Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));//va adicionar a las ciudades relacionadas
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
	}
}
