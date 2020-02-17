package com.julio.backendmc.domain.enums;

public enum EstadoPagamento {

	PENDIENTE(1, "Pendiente"), QUITADO(2, "Quitado"), CANCELADO(3, "Cancelado");

	// variables internas para almacenar los valores
	private int cod;
	private String descricao;

	// Constructor de tipo enumerado es private
	private EstadoPagamento(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}

	// hacer metodo get para codifo y descripcion
	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}

	// operacion que recibe un codigo y retorna el objeto de tipo cliente ya
	// instanciado static por que esa operacion tiene que ser posible de ejecutarse
	// sin instanciar objetos
	public static EstadoPagamento toEnum(Integer cod) {
		// si el codigo es null no hay ninguno debuelve nulo
		if (cod == null) {
			return null;
		}

		// busqueda entre todos los objetos x de los valores posibles de tipo cliente
		for (EstadoPagamento x : EstadoPagamento.values()) {
			// Si fue igual a x. getcod regresa x
			if (cod.equals(x.getCod())) {
				return x;
			}
		}
		// si no regresa es un codigo invalido y debuelve el codigo errado
		throw new IllegalArgumentException("Id invalido" + cod);

	}

}
