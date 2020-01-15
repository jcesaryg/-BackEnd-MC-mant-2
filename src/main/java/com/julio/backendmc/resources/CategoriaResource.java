package com.julio.backendmc.resources;


import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.julio.backendmc.domain.Categoria;
import com.julio.backendmc.services.CategoriaService;


@RestController //Controlador Rest
@RequestMapping(value="/categorias")//RequestMapping Evalua y regresa el Url que sera evaluado http://localhost:8080/categoria
public class CategoriaResource {
    
    @Autowired //Instacion Automatica el objeto
    private CategoriaService service;//Accesara al Servicio
    
    @RequestMapping(value = "/{id}",method=RequestMethod.GET) //agrega al request method el id para su busqueda http://localhost:8080/categoria/1
    public ResponseEntity<Categoria> find(@PathVariable Integer id)
    {
        Categoria obj = service.find(id);//Se va al servicio y se busca la categoria por el Id
        return ResponseEntity.ok().body(obj); //retorna ok = operacion fue con suceso, body = cuerpo del objeto obj que fue como categoria 
    }
    
    //recibir una categoria en Json y agregar ese categoria
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@RequestBody Categoria obj)
    {
    	obj = service.insert(obj); //obj recibira un servicio y agregar ver CategoriaService.java
    	
    	// va coger la url localhost:8080/categoria buildAndExpand 
    	URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
    			.path("/{id}").buildAndExpand(obj.getId()).toUri();// coge la url del nuevo recurso que fue agragado
    	return ResponseEntity.created(uri).build();//genera codigo 201 y crea un URI	
    }
    
    //Metodo Actualizar una  categoria
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)//se ingresa el Id que sera actualizado junto con el metodo que se uzara
    public ResponseEntity<Void> update(@RequestBody Categoria obj,@PathVariable Integer id)
    {
    	obj.setId(id);
    	obj = service.update(obj);//llama al servicio de actualizar CategoriaService.java
    	return ResponseEntity.noContent().build();
    }

    @RequestMapping(value = "/{id}",method=RequestMethod.DELETE) //agrega al request method el id para su busqueda http://localhost:8080/categoria/1
    public ResponseEntity<Categoria> delete(@PathVariable Integer id)
    {
    	service.delete(id); //servicio que eliminara CategoriaService.java
    	return ResponseEntity.noContent().build();
    }
}