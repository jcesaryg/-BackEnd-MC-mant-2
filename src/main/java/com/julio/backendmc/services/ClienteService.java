package com.julio.backendmc.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.julio.backendmc.domain.Cliente;
import com.julio.backendmc.repositories.ClienteRepository;
import com.julio.backendmc.services.exceptions.ObjectNotFoundException;


@Service
public class ClienteService {

    //Anotacion Autowired importada de spring
    @Autowired
    private ClienteRepository repo;
            
    //Operacion capaz de buscar una categoria por Codigo
    //llamara una operacion de objeto de accesoa  datos  de tipo cateogira repository
    
    public Cliente find(Integer id) 
    {
        //Optional<Cliente> encapsula el objeto instanciado
        Optional<Cliente> obj = repo.findById(id); //retorna el onjeto por busqueda de Id
        
        return obj.orElseThrow(()->new ObjectNotFoundException("Objeto no Encontrado Id: "+id+",Tipo: "+Cliente.class.getPackageName()));
    }
}
















