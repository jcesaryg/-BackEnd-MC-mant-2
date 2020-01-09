package com.julio.backendmc.domain.enums;

public enum TipoCliente {
	
	//guardar con nombre de persona fisica o juridica en la base de datos no es lo recomendable por que ocupa mucho espacio por lo que
	//se recomienda utilizar codigos. para poder almacenarlos.
	//--*--//
	
	//Para la asignacion de datos se coloca el valor del codigo numero de persona fisica y el de persona juridica 
	PESSOAFISICA(1,"Persona Fisica"),
	PESSOAJURIDICA(2,"Persona Juridica");

	//variables internas para almacenar los valores
	private int cod;
	private String descricao;
	
	//Constructor de tipo enumerado es private
	private TipoCliente(int cod, String descripcao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	//hacer metodo get para codifo y descripcion
	public int getCod(){
		return cod;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	//operacion que recibe un codigo y retorna el objeto de tipo cliente ya instanciado
	//static por que esa operacion tiene que ser posible de ejecutarse sin instanciar objetos
	public static TipoCliente toEnum(Integer cod) {
		//si el codigo es null no hay ninguno debuelve nulo
		if(cod == null){
			return null;
		}
		
		//busqueda entre todos los objetos x de los valores posibles de tipo cliente 
		for (TipoCliente x: TipoCliente.values()) {
			//Si fue igual a x. getcod  regresa x
			if(cod.equals(x.getCod())) {
				return x;
			}
		}
		//si no regresa es un codigo invalido y debuelve el codigo errado
		throw new IllegalArgumentException("Id invalido" + cod);
		
	}
	
}
