package com.julio.backendmc.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.julio.backendmc.domain.ItemPedido;
import com.julio.backendmc.domain.PagamentoComBoleto;
import com.julio.backendmc.domain.Pedido;
import com.julio.backendmc.domain.enums.EstadoPagamento;
import com.julio.backendmc.repositories.ItemPedidoRepository;
import com.julio.backendmc.repositories.PagamentoRepository;
import com.julio.backendmc.repositories.PedidoRepository;
import com.julio.backendmc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	// Anotacion Autowired importada de spring
	@Autowired
	private PedidoRepository repo;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private EmailService emailService;

	// Operacion capaz de buscar una categoria por Codigo
	// llamara una operacion de objeto de accesoa datos de tipo cateogira repository

	public Pedido find(Integer id) {
		// Optional<Pedido> encapsula el objeto instanciado
		Optional<Pedido> obj = repo.findById(id); // retorna el onjeto por busqueda de Id

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto no Encontrado Id: " + id + ",Tipo: " + Pedido.class.getPackageName()));

	}

	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));// se buscara del id en la base de datos
		obj.getPagamento().setEstado(EstadoPagamento.PENDIENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.saveAll(obj.getItens());

		emailService.sendOrderConfirmationEmail(obj);

		return obj;
	}
}