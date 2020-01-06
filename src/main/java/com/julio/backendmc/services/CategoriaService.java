package com.julio.backendmc.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.julio.backendmc.domain.Categoria;
import com.julio.backendmc.repositories.CategoriaRepository;

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
        Optional<Categoria> obj = repo.findById(id); //retorna el onjeto por busqueda de Id
        return obj.orElse(null); // retorna el obj y si no lo retorna el regresara con null
    }
}