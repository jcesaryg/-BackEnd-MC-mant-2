package com.julio.backendmc.domain;

import java.io.Serializable;

import javax.persistence.Entity;

import com.julio.backendmc.domain.enums.EstadoPagamento;

@Entity
public class PagamentoComCartao extends Pagamento implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer numeroDeParcelas;

	//Constructor  
	public PagamentoComCartao() {
	}

	//Constructor con atributos
	public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
		super(id, estado, pedido);
		
		this.numeroDeParcelas = numeroDeParcelas;
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}	
}
