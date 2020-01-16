package com.julio.backendmc.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.julio.backendmc.domain.Cliente;
import com.julio.backendmc.dto.ClienteDTO;
import com.julio.backendmc.repositories.ClienteRepository;
import com.julio.backendmc.services.exceptions.DataIntegrityException;
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
    
    
    //Metodo Para la actualizacion de una categoria
    public Cliente update(Cliente obj)
    {
     	Cliente newObj = find(obj.getId()); // se llamara find por que buscara si existen los registros o sino pasara algun error
     	updateData(newObj, obj);
     	return repo.save(newObj);//Muy similar o igual al de insert con la diferencia que el update no lleva obj.setId(null); 
    }
    
    //Metodo Para Eliminar una Cliente
    public void delete(Integer id)
    {
    	find(id); //Buscara el Id 
    	try {
    		repo.deleteById(id); //Eliminara el Id Utilizando el Id
    	}
    	catch (DataIntegrityViolationException e) {
    		throw new DataIntegrityException("No es posible eliminar por que tiene entidades relacionadas.");
		}
    }
    
    public List<Cliente> findAll()
    {
    	return  repo.findAll();
    }
    
    // Metodo buscar Cliente por pagina
    public Page<Cliente> findPage(Integer page,Integer linesPerPage, String orderBy,String direction)
    {
    	PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);//busca el PageRequest uzando 
    	return repo.findAll(pageRequest);//regresa el pagerequest despues de la busqueda
    }
    
    // de un Dto apartir de una ClienteDTO voy a construir un objDto
    public Cliente fromDTO(ClienteDTO objDto) 
    {
    	return new Cliente(objDto.getId(),objDto.getNome(),objDto.getEmail(),null,null);
    }
    
    private void updateData (Cliente newObj, Cliente obj)
    {
    	newObj.setNome(obj.getNome());
    	newObj.setEmail(obj.getEmail());
    }
}