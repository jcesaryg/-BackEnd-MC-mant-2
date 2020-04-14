package com.julio.backendmc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.julio.backendmc.domain.Cidade;

//Buscara datos de categoria JpaRepository<nombre de Dominio, Tipo de la variable en este caso ID>
//Objeto de la capa de acceso a datos (REPOSITORY)
@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

	@Transactional(readOnly = true)
	@Query("SELECT obj FROM Cidade obj WHERE obj.estado.id = :estadoId ORDER BY obj.nome")
	public List<Cidade> findCidades(@Param("estadoId") Integer estadoid);
}