package com.julio.backendmc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.julio.backendmc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());// envia
		sm.setFrom(sender);// recibe
		sm.setSubject("Pedido confirmado! Código: " + obj.getId());// asunto
		sm.setSentDate(new Date(System.currentTimeMillis()));// fecha del envio del mensaje
		sm.setText(obj.toString()); // convertir a string
		return sm;
	}
}
