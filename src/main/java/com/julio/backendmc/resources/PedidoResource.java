package com.julio.backendmc.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.julio.backendmc.domain.Pedido;
import com.julio.backendmc.services.PedidoService;

@RestController // Controlador Rest
@RequestMapping(value = "/pedidos") // RequestMapping Evalua y regresa el Url que sera evaluado
									// http://localhost:8080/pedidos
public class PedidoResource {

	@Autowired // Instacion Automatica el objeto
	private PedidoService service;// Accesara al Servicio

	@RequestMapping(value = "/{id}", method = RequestMethod.GET) // agrega al request method el id para su busqueda
																	// http://localhost:8080/categoria/1
	public ResponseEntity<Pedido> find(@PathVariable Integer id) {
		Pedido obj = service.find(id);// Se va al servicio y se busca la categoria por el Id
		return ResponseEntity.ok().body(obj); // retorna ok = operacion fue con suceso, body = cuerpo del objeto obj que
												// fue como categoria
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<Pedido>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "instante") String orderBy,
			@RequestParam(value = "direction", defaultValue = "DESC") String direction) {
		Page<Pedido> list = service.findPage(page, linesPerPage, orderBy, direction);
		return ResponseEntity.ok().body(list);
	}
}