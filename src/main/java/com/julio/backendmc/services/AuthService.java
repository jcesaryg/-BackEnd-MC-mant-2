package com.julio.backendmc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.julio.backendmc.domain.Cliente;
import com.julio.backendmc.repositories.ClienteRepository;
import com.julio.backendmc.services.exceptions.ObjectNotFoundException;

@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private EmailService emailService;

	private Random rand = new Random();

	public void sendNewPassword(String email) {

		Cliente cliente = clienteRepository.findByEmail(email);
		if (cliente == null) {
			throw new ObjectNotFoundException("No se encontro al Email");
		}

		String newPass = newPassword();

		cliente.setSenha(pe.encode(newPass));

		clienteRepository.save(cliente);
		emailService.sendNewPasswordEmail(cliente, newPass);
	}

	private String newPassword() {
		// generacion de 10 caracteres entre digitos o letras
		char[] vet = new char[10];
		for (int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}

	// metodo que genera caracteres
	// https://unicode-table.com/es/
	private char randomChar() {
		int opt = rand.nextInt(3);
		if (opt == 0) {
			// genera digitos
			return (char) (rand.nextInt(10) + 48);

		} else if (opt == 1) {
			// genera letras minusculas
			return (char) (rand.nextInt(26) + 65);

		} else {
			// genera letras mayusculas
			return (char) (rand.nextInt(26) + 97);
		}
	}
}