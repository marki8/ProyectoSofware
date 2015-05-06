package com.ps.util;

import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JPanel {

	private static final long serialVersionUID = 1L;
	private String user, pass;

	public Login() {
		this.user = "";
		this.pass = "";
		
		this.setLayout(null);
		this.setBackground(new Color(103, 191, 206));
		LoadLoginData();
	}

	private void LoadLoginData() {

		/*
		 * Texto de usuario
		 */
		JLabel usuario = new JLabel();
		usuario.setFont(usuario.getFont().deriveFont(20f));
		usuario.setForeground(Color.WHITE);
		usuario.setText("Usuario: ");
		usuario.setBackground(Color.BLACK);
		usuario.setBounds(100, 100, 150, 30);

		/*
		 * Panel para introducir el usuario
		 */
		final JTextField usuarioText = new JTextField(32);
		usuarioText.setEditable(true);
		usuarioText.setBounds(225, 100, 150, 30);

		/*
		 * Texto de la contrasena
		 */
		JLabel contrasena = new JLabel();
		contrasena.setFont(contrasena.getFont().deriveFont(20f));
		contrasena.setForeground(Color.WHITE);
		contrasena.setText("contrasena: ");
		contrasena.setBackground(Color.BLACK);
		contrasena.setBounds(100, 150, 150, 30);

		/*
		 * Field para introducir la contrasena
		 */
		final JPasswordField contrasenaText = new JPasswordField(32);
		contrasenaText.setEditable(true);
		contrasenaText.setBounds(225, 150, 150, 30);

		/*
		 * Boton aceptar
		 */
		JButton botonAceptar = new JButton("Aceptar");
		botonAceptar.setFont(botonAceptar.getFont().deriveFont(10f));
		botonAceptar.setBounds(125, 225, 100, 25);
		botonAceptar.addActionListener(new ActionListener() {

			// Funcion que se activa cuando se pulsa el boton
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				user = usuarioText.getText();
				pass = contrasenaText.getText();
				System.out.println("Usuario: " + user + " contrasena: " + pass);
				/**
				 * Hacer login
				 */
			}
		});
		//
		//
		//
		JButton botonRegistrarse = new JButton("Registrarse");
		botonRegistrarse.setFont(botonAceptar.getFont().deriveFont(10f));
		botonRegistrarse.setBounds(250, 225, 100, 25);
		botonRegistrarse.addActionListener(new ActionListener() {

			// Funcion que se activa cuando se pulsa el boton
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		//
		// /*
		// * A�adimos los componentes
		// */
		this.add(contrasena);
		this.add(usuario);
		//
		//
		this.add(contrasenaText);
		this.add(usuarioText);
		//
		this.add(botonAceptar);
		this.add(botonRegistrarse);
	}

}
