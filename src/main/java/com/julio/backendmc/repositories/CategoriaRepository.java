package com.julio.backendmc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.julio.backendmc.domain.Categoria;

//Buscara datos de categoria JpaRepository<nombre de Dominio, Tipo de la variable en este caso ID>
//Objeto de la capa de acceso a datos (REPOSITORY)
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {

}