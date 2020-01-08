package com.julio.backendmc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity //Mapeamiento basico
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;	
	
	@Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private Double preco;
	
	
	//Asociacion de Producto con categoria
	@JsonBackReference //del otro lado de la asociacion ya fue buscado los objetos entonces ya no busco mas, emito la lista de categorias de cada producto
	@ManyToMany //Relacionamiento muchos para muchos
	@JoinTable (name = "PRODUTO__CATEGORIA", //Nombre de la tabla
		joinColumns = @JoinColumn(name="produto_id"), //nombre de campo correspondiente al codigo del producto FK
		inverseJoinColumns = @JoinColumn(name="categoria_id")//Nombre de la otra llave extrangera (FK) que va referenciar la categoria
			) //voy a definir quien va ser la tabla que hare muchos para muchos
	private List<Categoria> categorias = new ArrayList<>(); //Mapeamiento de la lista de categorias informando quien va ser la tabla del banco de datos que hara de medio de campo entre las dos tablas producto y categoria
	
	//Constructor vacio
	public Produto() {
	}

	//Constructor con parametros
	public Produto(Integer id, String nome, Double preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
	}

	
	//Getter and Setter de Productos
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

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	//Hash code and equals
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
	
	
}
