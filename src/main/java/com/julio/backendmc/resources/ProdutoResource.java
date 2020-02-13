package com.julio.backendmc.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.julio.backendmc.domain.Produto;
import com.julio.backendmc.dto.ProdutoDTO;
import com.julio.backendmc.resources.utils.URL;
import com.julio.backendmc.services.ProdutoService;

@RestController // Controlador Rest
@RequestMapping(value = "/produtos") // RequestMapping Evalua y regresa el Url que sera evaluado
										// http://localhost:8080/pedidos
public class ProdutoResource {

	@Autowired // Instacion Automatica el objeto
	private ProdutoService service;// Accesara al Servicio

	@RequestMapping(value = "/{id}", method = RequestMethod.GET) // agrega al request method el id para su busqueda
																	// http://localhost:8080/categoria/1
	public ResponseEntity<Produto> find(@PathVariable Integer id) {
		Produto obj = service.find(id);// Se va al servicio y se busca la categoria por el Id
		return ResponseEntity.ok().body(obj); // retorna ok = operacion fue con suceso, body = cuerpo del objeto obj que
												// fue como categoria
	}

	@RequestMapping(method = RequestMethod.GET) // retornara todas las categorias en un page

	public ResponseEntity<Page<ProdutoDTO>> findPage(@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "categorias", defaultValue = "") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		String nomeDecoded = URL.decodeParam(nome);
		List<Integer> ids = URL.decodeIntList(categorias);
		Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);
		Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}
}