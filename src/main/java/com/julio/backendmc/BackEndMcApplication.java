package com.julio.backendmc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.julio.backendmc.domain.Categoria;
import com.julio.backendmc.domain.Cidade;
import com.julio.backendmc.domain.Cliente;
import com.julio.backendmc.domain.Endereco;
import com.julio.backendmc.domain.Estado;
import com.julio.backendmc.domain.ItemPedido;
import com.julio.backendmc.domain.Pagamento;
import com.julio.backendmc.domain.PagamentoComBoleto;
import com.julio.backendmc.domain.PagamentoComCartao;
import com.julio.backendmc.domain.Pedido;
import com.julio.backendmc.domain.Produto;
import com.julio.backendmc.domain.enums.EstadoPagamento;
import com.julio.backendmc.domain.enums.TipoCliente;
import com.julio.backendmc.repositories.CategoriaRepository;
import com.julio.backendmc.repositories.CidadeRepository;
import com.julio.backendmc.repositories.ClienteRepository;
import com.julio.backendmc.repositories.EnderecoRepository;
import com.julio.backendmc.repositories.EstadoRepository;
import com.julio.backendmc.repositories.ItemPedidoRepository;
import com.julio.backendmc.repositories.PagamentoRepository;
import com.julio.backendmc.repositories.PedidoRepository;
import com.julio.backendmc.repositories.ProdutoRepository;

@SpringBootApplication
public class BackEndMcApplication implements CommandLineRunner { //CommandLineRunner Permite implementar un metodo auxiliar para ejecutar alguna opcion

	@Autowired
	private CategoriaRepository categoriaRepository; // Dependencia 
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	//Repositorios para categoria y estado
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository; 
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(BackEndMcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
		//Instanciamiento de las categorias
		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");
		Categoria cat3 = new Categoria(null, "Casa mesa y baño");
		Categoria cat4 = new Categoria(null, "Electronicos");
		Categoria cat5 = new Categoria(null, "Jardineria");
		Categoria cat6 = new Categoria(null, "Decoracion");
		Categoria cat7 = new Categoria(null, "Perfumeria");
		
		//Instaciomiento de los Productos 
		Produto p1 = new Produto(null, "Computador",2000.00);
		Produto p2 = new Produto(null, "Impresora",800.00);		
		Produto p3 = new Produto(null, "Mouse",80.00);				
		
		//Se crea la relacion de los productos basado en "instância do modelo conceitual:" Pag 03  del documento 02-implementacao-de-modelo-conceitual
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		//Se hace llamado al repository para que pueda almacenar todos los productos
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2,cat3,cat4,cat5,cat6,cat7));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));

		//------- INSTANCIACION DE ESTADOS Y CIUDADES -------
		//Instanciar Estados
		Estado est1 = new Estado(null,"Mina gerais");
		Estado est2 = new Estado(null,"Sao Paulo");

		//Instanciar Ciudades
		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "Sao Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));//va adicionar a las ciudades relacionadas
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		//------- INSTANCIACION DE LOS NUEVOS OBJETOS -------
		
		//Instacia de Cliente
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "123456789", TipoCliente.PESSOAFISICA);
		
		//Instancia de Teledote 
		cli1.getTelefones().addAll(Arrays.asList("984123456","994000944"));
		
		//Instancia de Endereco
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apt 303", "Jardin", "3820834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Salas 800", "Centro", "38777012", cli1, c2); 	
		
		cli1.getEnderecos().addAll(Arrays.asList(e1,e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1,e2));
		
		//------- NUEVA INSTANCIACION DE ATRIBUTOS------- 
		//Formato para enmascar fechas
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		//instanciacion de pedidos
		Pedido ped1 = new Pedido(null, sdf.parse("11/01/2020 19:21"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("11/01/2020 19:25"), cli1, e2);
		
		//Instanciacion de pagamento
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDIENTE, ped2, sdf.parse("20/10/2020 00:00"),null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1,ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1,ped2));
		pagamentoRepository.saveAll(Arrays.asList(pagto1,pagto2));		

		
		//------- NUEVA INSTANCIACION DE ATRIBUTOS------- 
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));

		
		
		
	
	}
}















