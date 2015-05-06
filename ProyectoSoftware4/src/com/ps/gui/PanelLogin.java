package com.ps.gui;

import java.awt.BorderLayout;
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
	public static final int Ancho = 420;
	public static final int Alto = 350;

	public PanelLogin(final DbConnector db, JFrame login) {
		this.user = "";
		this.pass = "";
		this.db = db;
		this.login = login;

		this.setLayout(null);
		this.setBackground(new Color(103, 191, 206));
		LoadLoginData(login);
	}

	private void LoadLoginData(final JFrame login) {

		/*
		 * Texto de usuario
		 */
		JLabel usuario = new JLabel();
		usuario.setFont(usuario.getFont().deriveFont(20f));
		usuario.setForeground(new Color(30, 30, 30));
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
		contrasena.setForeground(new Color(30, 30, 30));
		contrasena.setText("Contrasena: ");
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
		botonAceptar
				.addActionListener(acceptButton(usuarioText, contrasenaText));
		//
		//
		//
		JButton botonRegistrarse = new JButton("Registrarse");
		botonRegistrarse.setFont(botonAceptar.getFont().deriveFont(10f));
		botonRegistrarse.setBounds(250, 225, 100, 25);
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
				System.out.println("Usuario: " + user + " contrasena: " + pass);
				if (db.userExist(user, pass)) {
					if (user.equals("admin")) {
						login.dispose();
						new MainGUI(1, user, pass).setVisible(true);
					} else {
						login.dispose();
						new MainGUI(0, user, pass).setVisible(true);
					}
				} else {
					JOptionPane j = new JOptionPane();
					j.showMessageDialog(login,
							"El usuario o la contrase�a que ha introducido son erroneos\n"
									+ "Por favor vuelva a intentarlo", "Error",
							JOptionPane.INFORMATION_MESSAGE);
					// restart de la pantalla de login
					login.dispose();
					JFrame Login = new JFrame(BorderLayout.CENTER);
					Login.setSize(Ancho, Alto);
					Login.setLocationRelativeTo(null);
					Login.add(new PanelLogin(db, Login));
					Login.setVisible(true);
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
				new MainGUI(0, user, pass).setVisible(true);
			}
		};
		return al;
	}

}
