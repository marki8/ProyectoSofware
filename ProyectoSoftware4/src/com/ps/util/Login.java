package com.ps.util;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.ps.db.DbConnector;
import com.ps.gui.MainGUI;
import com.ps.gui.PanelBuyBook;

public class Login extends JPanel{
	
	private String user,pass;
	private DbConnector db;
	private JFrame login;
	public static final int Ancho=840;
	public static final int Alto=700;
	
	public Login(final DbConnector db, JFrame login){
		this.user="";
		this.pass="";
		this.db=db;
		this.login=login;
		
		this.setLayout(null);
		this.setBackground(new Color(103,191,206));
		LoadLoginData(login);
	}
	
	
	
private void LoadLoginData(final JFrame login){
		
		/*
		 * Texto de usuario
		 */
		JLabel usuario= new JLabel();
		usuario.setFont(usuario.getFont().deriveFont(20f));
		usuario.setForeground(Color.WHITE);
		usuario.setText("Usuario: ");
		usuario.setBackground(Color.BLACK);
		usuario.setBounds(300,300, 150, 30);
//		
//		/*
//		 * Panel para introducir el usuario
//		 */
		final JTextField usuarioText = new JTextField(32);
		usuarioText.setEditable(true);
		usuarioText.setBounds(425,300, 150, 30);
		usuarioText.addKeyListener(new KeyListener() {			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				repaint();
			}			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub	
			}
		});
//		
//		/*
//		 * Texto de la contrasena
//		 */
		JLabel contrasena= new JLabel();
		contrasena.setFont(contrasena.getFont().deriveFont(20f));
		contrasena.setForeground(Color.WHITE);
		contrasena.setText("Contraseña: ");
		contrasena.setBackground(Color.BLACK);
		contrasena.setBounds(300,350, 150, 30);
		
		/*
		 * Field para introducir la contrasena
		 */
		final JPasswordField contrasenaText = new JPasswordField(32);
		contrasenaText.setEditable(true);
		contrasenaText.setBounds(425,350, 150, 30);
		contrasenaText.addKeyListener(new KeyListener() {			
			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				repaint();
			}			
			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub	
			}
		});
//		
//		
//		
//		/*
//		 * Boton aceptar
//		 */
//		
		JButton botonAceptar=  new JButton("Aceptar");
		botonAceptar.setFont(botonAceptar.getFont().deriveFont(10f));
		botonAceptar.setBounds(325,425, 100, 25);
		botonAceptar.addActionListener(new ActionListener() {
			
			//Funcion que se activa cuando se pulsa el boton 
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				user=usuarioText.getText();
				pass=contrasenaText.getText();
				//System.out.println("Usuario: "+user+" contrasena: "+pass);
				if (db.userExist(user, pass)) {
					if (user.equals("admin")) {
						login.dispose();
						new MainGUI(1,user,pass).setVisible(true);
					} else {
						login.dispose();
						new MainGUI(0,user,pass).setVisible(true);
					}
				} else {
					JOptionPane j = new JOptionPane();
					j.showMessageDialog(
							login,
							"El usuario o la contraseña que ha introducido son erroneos\nPor favor vuelva a intentarlo",
							"Error", JOptionPane.INFORMATION_MESSAGE);
					//restart de la pantalla de login
					login.dispose();
					JFrame  Login= new JFrame(BorderLayout.CENTER);
					Login.setSize(Ancho, Alto);
					Login.setLocationRelativeTo(null);
					Login.add(new Login(db,Login));
					Login.setVisible(true);
				}
			}
		});
//		
//		
//		
		JButton botonRegistrarse=  new JButton("Registrarse");
		botonRegistrarse.setFont(botonAceptar.getFont().deriveFont(10f));
		botonRegistrarse.setBounds(450,425, 100, 25);
		botonRegistrarse.addActionListener(new ActionListener() {
			
			//Funcion que se activa cuando se pulsa el boton 
			@Override
			public void actionPerformed(ActionEvent arg0) {
				user=usuarioText.getText();
				pass=contrasenaText.getText();
				db.addUser(user, pass);
				login.dispose();
				new MainGUI(0,user,pass).setVisible(true);
			}
		});
//		
//		/*
//		 * Aï¿½adimos los componentes
//		 */
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
