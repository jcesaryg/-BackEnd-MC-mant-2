package com.julio.backendmc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.julio.backendmc.domain.Cidade;
import com.julio.backendmc.repositories.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository repo;
	
	public List<Cidade> findByEstado(Integer estadoId)
	{
		return repo.findCidades(estadoId);
	}
}
