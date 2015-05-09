package com.ps.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.ps.db.DbConnector;

public class PanelLogin extends Panel {

	private static final long serialVersionUID = 1L;
	private String user, pass;
	private DbConnector db;
	private JFrame login;
	
	// Componentes
	private JLabel usuario;
	private JLabel contrasena;
	private JTextField usuarioText;
	private JPasswordField contrasenaText;
	private JButton botonAceptar;
	private JButton botonRegistrarse;

	public PanelLogin(final DbConnector db, JFrame login) {
		this.user = "";
		this.pass = "";
		this.db = db;
		this.login = login;

		this.setLayout(null);

		//this.setBackground(new Color(103, 191, 206));
		LoadLoginData(login);
	}

	private void LoadLoginData(final JFrame login) {

		/*
		 * Texto de usuario
		 */
		usuario = new JLabel();
		usuario.setFont(usuario.getFont().deriveFont(14f));
		usuario.setForeground(new Color(30, 30, 30));
		usuario.setText("Usuario: ");
		usuario.setBackground(Color.BLACK);
		usuario.setBounds(20, 20, 150, 30);

		/*
		 * Panel para introducir el usuario
		 */
		usuarioText = new JTextField(32);
		usuarioText.setEditable(true);
		usuarioText.setBounds(140, 20, 220, 30);

		/*
		 * Texto de la contrasena
		 */
		contrasena = new JLabel();
		contrasena.setFont(contrasena.getFont().deriveFont(14f));
		contrasena.setForeground(new Color(30, 30, 30));
		contrasena.setText("Contrasena: ");
		contrasena.setBackground(Color.BLACK);
		contrasena.setBounds(20, 60, 150, 30);

		/*
		 * Field para introducir la contrasena
		 */
		contrasenaText = new JPasswordField(32);
		contrasenaText.setEditable(true);
		contrasenaText.setBounds(140, 60, 220, 30);

		/*
		 * Boton aceptar
		 */
		botonAceptar = new JButton("Aceptar");
		botonAceptar.setFont(botonAceptar.getFont().deriveFont(12f));
		botonAceptar.setBounds(60, 110, 100, 25);
		botonAceptar
				.addActionListener(acceptButton(usuarioText, contrasenaText));
		//
		//
		//
		botonRegistrarse = new JButton("Registrarse");
		botonRegistrarse.setFont(botonAceptar.getFont().deriveFont(12f));
		botonRegistrarse.setBounds(250, 110, 100, 25);
		botonRegistrarse.addActionListener(registerButton(usuarioText,
				contrasenaText));
		//
		// /*
		// * A�adimos los componentes
		// */
		this.add(contrasena);
		this.add(usuario);
		this.add(contrasenaText);
		this.add(usuarioText);
		this.add(botonAceptar);
		this.add(botonRegistrarse);
	}

	/**
	 * 
	 * @param usuarioText
	 * @param contrasenaText
	 * @return
	 */
	private ActionListener acceptButton(final JTextField usuarioText,
			final JTextField contrasenaText) {
		ActionListener al = new ActionListener() {

			// Funcion que se activa cuando se pulsa el boton
			@Override
			public void actionPerformed(ActionEvent arg0) {
				/**
				 * Hacer login
				 */
				user = usuarioText.getText();
				pass = contrasenaText.getText();
				JFrame gui;
				System.out.println("Usuario: " + user + " contrasena: " + pass);
				if (db.userExist(user, pass)) {
					if (user.equals("admin")) {
						gui = new MainGUI(1, user);
					} else {
						gui = new MainGUI(0, user);
					}
					gui.setVisible(true);
					gui.pack();
					login.dispose();
				} else {
					JOptionPane.showMessageDialog(login,
							"El usuario o la contrasena que ha introducido son erroneos\n"
									+ "Por favor vuelva a intentarlo", "Error",
							JOptionPane.INFORMATION_MESSAGE);
					contrasenaText.setText("");
				}
			}
		};
		return al;
	}

	/**
	 * 
	 * @param usuarioText
	 * @param contrasenaText
	 * @return
	 */
	private ActionListener registerButton(final JTextField usuarioText,
			final JTextField contrasenaText) {
		ActionListener al = new ActionListener() {

			// Funcion que se activa cuando se pulsa el boton
			@Override
			public void actionPerformed(ActionEvent arg0) {
				user = usuarioText.getText();
				pass = contrasenaText.getText();
				db.addUser(user, pass);
				login.dispose();
				
				JFrame gui = new MainGUI(0, user);
				gui.setVisible(true);
				gui.pack();
			}
		};
		return al;
	}

}
