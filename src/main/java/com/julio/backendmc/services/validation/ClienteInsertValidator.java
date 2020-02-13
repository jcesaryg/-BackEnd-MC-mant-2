package com.julio.backendmc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.julio.backendmc.domain.Cliente;
import com.julio.backendmc.domain.enums.TipoCliente;
import com.julio.backendmc.dto.ClienteNewDTO;
import com.julio.backendmc.repositories.ClienteRepository;
import com.julio.backendmc.resources.exception.FieldMessage;
import com.julio.backendmc.services.validation.utils.BR;

//Anotacion y el tipo de cliente 
public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository repo;

	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {

		List<FieldMessage> list = new ArrayList<>();

		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}

		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}

		// busca por Email
		Cliente aux = repo.findByEmail(objDto.getEmail());
		// si auxiliar es distinto a null el email ya existe
		if (aux != null) {
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
