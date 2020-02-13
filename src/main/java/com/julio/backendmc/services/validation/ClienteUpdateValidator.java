package com.julio.backendmc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.julio.backendmc.domain.Cliente;
import com.julio.backendmc.dto.ClienteDTO;
import com.julio.backendmc.repositories.ClienteRepository;
import com.julio.backendmc.resources.exception.FieldMessage;

//Anotacion y el tipo de cliente 
public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private ClienteRepository repo;

	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {

		// permite recuperar el id del cliente http://localhost:8080/clientes/2
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));

		List<FieldMessage> list = new ArrayList<>();

		// busca por Email
		Cliente aux = repo.findByEmail(objDto.getEmail());
		// si auxiliar es distinto a null el email ya existe
		if (aux != null && !aux.getId().equals(uriId)) {
			list.add(new FieldMessage("email", "el Email ya existe"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
