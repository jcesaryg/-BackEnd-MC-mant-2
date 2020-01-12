package com.julio.backendmc.services;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.julio.backendmc.domain.Pedido;
import com.julio.backendmc.repositories.PedidoRepository;
import com.julio.backendmc.services.exceptions.ObjectNotFoundException;


@Service
public class PedidoService {

    //Anotacion Autowired importada de spring
    @Autowired
    private PedidoRepository repo;
            
    //Operacion capaz de buscar una categoria por Codigo
    //llamara una operacion de objeto de accesoa  datos  de tipo cateogira repository
    
    public Pedido buscar(Integer id) 
    {
        //Optional<Pedido> encapsula el objeto instanciado
        Optional<Pedido> obj = repo.findById(id); //retorna el onjeto por busqueda de Id
        
        return obj.orElseThrow(()->new ObjectNotFoundException("Objeto no Encontrado Id: "+id+",Tipo: "+Pedido.class.getPackageName()));
        		
    }
}
















