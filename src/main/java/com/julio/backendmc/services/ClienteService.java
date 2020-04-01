package com.julio.backendmc.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.julio.backendmc.domain.Cidade;
import com.julio.backendmc.domain.Cliente;
import com.julio.backendmc.domain.Endereco;
import com.julio.backendmc.domain.enums.Perfil;
import com.julio.backendmc.domain.enums.TipoCliente;
import com.julio.backendmc.dto.ClienteDTO;
import com.julio.backendmc.dto.ClienteNewDTO;
import com.julio.backendmc.repositories.ClienteRepository;
import com.julio.backendmc.repositories.EnderecoRepository;
import com.julio.backendmc.security.UserSS;
import com.julio.backendmc.services.exceptions.AuthorizationException;
import com.julio.backendmc.services.exceptions.DataIntegrityException;
import com.julio.backendmc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private BCryptPasswordEncoder pe;

	// Anotacion Autowired importada de spring
	@Autowired
	private ClienteRepository repo;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private S3Service s3Service;

	@Autowired
	private ImageService imageService;

	@Value("${img.prefix.client.profile}")
	private String prefix;

	@Value("${img.profile.size}")
	private Integer size;

	// Operacion capaz de buscar una categoria por Codigo
	// llamara una operacion de objeto de accesoa datos de tipo cateogira repository

	public Cliente find(Integer id) {

		UserSS user = UserService.authenticated();// coge el usuario logueado
		if (user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso denegado"); // lanzar una excepcion
		}

		// Optional<Cliente> encapsula el objeto instanciado
		Optional<Cliente> obj = repo.findById(id); // retorna el onjeto por busqueda de Id

		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto no Encontrado Id: " + id + ",Tipo: " + Cliente.class.getPackageName()));
	}

	// agregar una Cliente uzando un repositorio
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}

	// Metodo Para la actualizacion de una categoria
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId()); // se llamara find por que buscara si existen los registros o sino pasara
											// algun error
		updateData(newObj, obj);
		return repo.save(newObj);// Muy similar o igual al de insert con la diferencia que el update no lleva
									// obj.setId(null);
	}

	// Metodo Para Eliminar una Cliente
	public void delete(Integer id) {
		find(id); // Buscara el Id
		try {
			repo.deleteById(id); // Eliminara el Id Utilizando el Id
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("No es posible eliminar por que tiene pedidos relacionadas.");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}

	// Metodo buscar Cliente por pagina
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);// busca el
																											// PageRequest
																											// uzando
		return repo.findAll(pageRequest);// regresa el pagerequest despues de la busqueda
	}

	// de un Dto apartir de una ClienteDTO voy a construir un objDto
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(),
				TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),
				objDto.getBairro(), objDto.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if (objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		return cli;
	}

	// de un Dto apartir de una ClienteDTO voy a construir un objDto
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	// metodo para hacer upload de una foto del perfil de cliente
	public URI uploadProfilePicture(MultipartFile multipartFile) {

		UserSS user = UserService.authenticated();

		if (user == null) {
			throw new AuthorizationException("Acceso Negado");
		}

		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);

		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);

		String fileName = prefix + user.getId() + ".jpg";

		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
	}

}