package com.ps.util;

import java.awt.Color;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JPanel{
	
	private String user,pass;
	
	public Login(){
		this.user="";
		this.pass="";
		
		this.setLayout(null);
		this.setBackground(new Color(103,191,206));
		LoadLoginData();
	}
	
	
	
private void LoadLoginData(){
		
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
//		 * Texto de la contrase�a
//		 */
		JLabel contrase�a= new JLabel();
		contrase�a.setFont(contrase�a.getFont().deriveFont(20f));
		contrase�a.setForeground(Color.WHITE);
		contrase�a.setText("Contrase�a: ");
		contrase�a.setBackground(Color.BLACK);
		contrase�a.setBounds(300,350, 150, 30);
		
		/*
		 * Field para introducir la contrase�a
		 */
		final JPasswordField contrase�aText = new JPasswordField(32);
		contrase�aText.setEditable(true);
		contrase�aText.setBounds(425,350, 150, 30);
		contrase�aText.addKeyListener(new KeyListener() {			
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
				pass=contrase�aText.getText();
				System.out.println("Usuario: "+user+" Contrase�a: "+pass);
				/**
				 * Hacer login
				 */
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
				// TODO Auto-generated method stub
				
			}
		});
//		
//		/*
//		 * A�adimos los componentes
//		 */
		this.add(contrase�a);
		this.add(usuario);
//		
//
		this.add(contrase�aText);
		this.add(usuarioText);
//		
		this.add(botonAceptar);
		this.add(botonRegistrarse);
	}

}
