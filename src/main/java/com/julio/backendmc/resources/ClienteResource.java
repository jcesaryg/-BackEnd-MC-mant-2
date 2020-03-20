package com.julio.backendmc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.julio.backendmc.domain.Cliente;
import com.julio.backendmc.dto.ClienteDTO;
import com.julio.backendmc.dto.ClienteNewDTO;
import com.julio.backendmc.services.ClienteService;

@RestController // Controlador Rest
@RequestMapping(value = "/clientes") // RequestMapping Evalua y regresa el Url que sera evaluado
										// http://localhost:8080/categoria
public class ClienteResource {

	@Autowired // Instacion Automatica el objeto
	private ClienteService service;// Accesara al Servicio

	@RequestMapping(value = "/{id}", method = RequestMethod.GET) // agrega al request method el id para su busqueda
																	// http://localhost:8080/categoria/1
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente obj = service.find(id);// Se va al servicio y se busca la categoria por el Id
		return ResponseEntity.ok().body(obj); // retorna ok = operacion fue con suceso, body = cuerpo del objeto obj que
												// fue como categoria
	}

	// Metodo Post de cliente Resource que llama a ClienteNewDTO.java
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto) {
		Cliente obj = service.fromDTO(objDto);
		obj = service.insert(obj); // obj recibira un servicio y agregar ver ClienteService.java

		// va coger la url localhost:8080/categoria buildAndExpand
		// coge la url del nuevo recurso que fue agragado
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();// genera codigo 201 y crea un URI
	}

	// Metodo Actualizar una categoria @Valid sirve para poder validar el metodo
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT) // se ingresa el Id que sera actualizado junto con el
																	// metodo que se uzara
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id) {
		Cliente obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);// llama al servicio de actualizar ClienteService.java
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE) // agrega al request method el id para su busqueda
																	// http://localhost:8080/categoria/1
	public ResponseEntity<Cliente> delete(@PathVariable Integer id) {
		service.delete(id); // servicio que eliminara ClienteService.java
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.GET) // retornara todas las categorias
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> list = service.findAll();// listara todas las categorias
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto); // retorna ok = operacion fue con suceso, body = cuerpo del objeto obj
													// que fue como categoria
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	// localhost:8080/categorias/page?linesPerPage=3&page=1&direction=DESC
	@RequestMapping(value = "/page", method = RequestMethod.GET) // retornara todas las categorias en un page
	public ResponseEntity<Page<ClienteDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);// listara todas las categorias
		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));
		return ResponseEntity.ok().body(listDto);
	}
}