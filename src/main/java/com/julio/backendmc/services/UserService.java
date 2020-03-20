package com.julio.backendmc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.julio.backendmc.security.UserSS;

public class UserService {

	public static UserSS authenticated() {
		try {
			// llama a la funcion de un spring segurity de un usuario logueado
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {
			return null;
		}
	}
}