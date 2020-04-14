package com.julio.backendmc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.julio.backendmc.domain.Estado;

//Buscara datos de categoria JpaRepository<nombre de Dominio, Tipo de la variable en este caso ID>
//Objeto de la capa de acceso a datos (REPOSITORY)
@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

	@Transactional(readOnly = true)
	public List<Estado> findAllByOrderByNome();
}