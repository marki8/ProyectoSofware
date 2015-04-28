package com.ps.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailTLS {
	
	private String mail;
	private String titulo;
	private String autor;
	
	public SendMailTLS(String mail,String titulo,String autor) {
		this.mail=mail;
		this.titulo=titulo;
		this.autor=autor;
	}

	public void send() {

		final String username = "easybooksps@gmail.com";
		final String password = "ps20142015";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {
			Date date=new Date();
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("easybooksps@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("javiermiguel93@gmail.com"));
			message.setSubject("Compra del libro: "+titulo);
			message.setText("Apreciado cliente: "+mail
					+ "\n\nUsted ha comprado el libro "+titulo+"de "+autor+"a las "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds()
					+" horas del "+date.getDate()+"/"+date.getMonth()+"/"+date.getYear()) ;
//libro,código del libro, hora del pedido, usuario
			Transport.send(message);
			
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}