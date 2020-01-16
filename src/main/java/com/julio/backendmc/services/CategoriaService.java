package com.julio.backendmc.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.julio.backendmc.domain.Categoria;
import com.julio.backendmc.dto.CategoriaDTO;
import com.julio.backendmc.repositories.CategoriaRepository;
import com.julio.backendmc.services.exceptions.DataIntegrityException;
import com.julio.backendmc.services.exceptions.ObjectNotFoundException;


@Service
public class CategoriaService {

    //Anotacion Autowired importada de spring
    @Autowired
    private CategoriaRepository repo;
            
    //Operacion capaz de buscar una categoria por Codigo
    //llamara una operacion de objeto de accesoa  datos  de tipo cateogira repository
    
    public Categoria find(Integer id) 
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
    
    //Metodo Para la actualizacion de una categoria
    public Categoria update(Categoria obj)
    {
    	find(obj.getId()); // se llamara find por que buscara si existen los registros o sino pasara algun error
    	return repo.save(obj);//Muy similar o igual al de insert con la diferencia que el update no lleva obj.setId(null); 
    }
    
    //Metodo Para Eliminar una Categoria
    public void delete(Integer id)
    {
    	find(id); //Buscara el Id 
    	try {
    		repo.deleteById(id); //Eliminara el Id Utilizando el Id
    	}
    	catch (DataIntegrityViolationException e) {
    		throw new DataIntegrityException("No es posible eliminar una categoria que tiene productos.");
		}
    }
    
    public List<Categoria> findAll()
    {
    	return  repo.findAll();
    }
    
    // Metodo buscar Categoria por pagina
    public Page<Categoria> findPage(Integer page,Integer linesPerPage, String orderBy,String direction)
    {
    	PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction),orderBy);//busca el PageRequest uzando 
    	return repo.findAll(pageRequest);//regresa el pagerequest despues de la busqueda
    }
    
    // de un Dto apartir de una CategoriaDTO voy a construir un objDto
    public Categoria fromDTO(CategoriaDTO objDto) 
    {
    	return new Categoria(objDto.getId(), objDto.getNome());
    }
}










