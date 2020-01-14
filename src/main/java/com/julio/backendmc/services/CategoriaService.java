package com.julio.backendmc.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.julio.backendmc.domain.Categoria;
import com.julio.backendmc.repositories.CategoriaRepository;
import com.julio.backendmc.services.exceptions.ObjectNotFoundException;


@Service
public class CategoriaService {

    //Anotacion Autowired importada de spring
    @Autowired
    private CategoriaRepository repo;
            
    //Operacion capaz de buscar una categoria por Codigo
    //llamara una operacion de objeto de accesoa  datos  de tipo cateogira repository
    
    public Categoria buscar(Integer id) 
    {
        //Optional<Categoria> encapsula el objeto instanciado
        Optional<Categoria> obj = repo.findById(id); //retorna el objeto por busqueda de Id
        
        return obj.orElseThrow(()->new ObjectNotFoundException("Objeto no Encontrado Id: "+id+",Tipo: "+Categoria.class.getPackageName()));
        		
    }
    
    //agregar una categoria uzando un repositorio
    public Categoria insert(Categoria obj) 
    {
    	obj.setId(null); // si setId trae algun objeto no se hara un agregado sino una actualizacion
    	return repo.save(obj); //regresara un repositario de guardar
    }
}
















