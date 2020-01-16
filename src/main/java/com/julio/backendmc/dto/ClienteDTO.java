package com.julio.backendmc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.julio.backendmc.domain.Cliente;

public class ClienteDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;

	@NotEmpty(message = "Llene el nombre de forma obligatoria")
	@Length(min = 5, max = 120, message = "El tamaño debe ser entre 5 o 80 caracteres")
	private String nome;
	
	@NotEmpty(message = "Llene el email de forma obligatoria")
	@Email(message = "Email Invalido")
	private String email;
	
	//Constructor Vacio
	public ClienteDTO(){
	}
	
	
	//constructor responsable por instanciar detalle para mi
	public ClienteDTO(Cliente obj) 
	{
		id = obj.getId();
		nome = obj.getNome();
		email = obj.getEmail();
	}
	
	//Getters and Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}
