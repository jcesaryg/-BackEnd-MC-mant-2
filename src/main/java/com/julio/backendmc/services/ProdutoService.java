package com.julio.backendmc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.julio.backendmc.domain.Categoria;
import com.julio.backendmc.domain.Produto;
import com.julio.backendmc.repositories.CategoriaRepository;
import com.julio.backendmc.repositories.ProdutoRepository;
import com.julio.backendmc.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {

	// Anotacion Autowired importada de spring
	@Autowired
	private ProdutoRepository repo;
	@Autowired
	private CategoriaRepository categoriaRepository;

	// Operacion capaz de buscar una categoria por Codigo
	// llamara una operacion de objeto de accesoa datos de tipo cateogira repository

	public Produto find(Integer id) {
		// Optional<Produto> encapsula el objeto instanciado
		Optional<Produto> obj = repo.findById(id); // retorna el onjeto por busqueda de Id

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto no Encontrado Id: " + id + ",Tipo: " + Produto.class.getPackageName()));
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy,
			String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
}
