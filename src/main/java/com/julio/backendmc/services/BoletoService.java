package com.julio.backendmc.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.julio.backendmc.domain.PagamentoComBoleto;

@Service
public class BoletoService {

	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {
		// instancia de calendario
		Calendar cal = Calendar.getInstance();
		// definir la fecjha del calendario
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime());
	}
}
