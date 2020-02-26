package com.julio.backendmc.services;

import org.springframework.mail.SimpleMailMessage;

import com.julio.backendmc.domain.Pedido;

public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);
}
