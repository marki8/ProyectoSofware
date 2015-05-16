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
//		 * Texto de la constrasena
//		 */
		JLabel constrasena= new JLabel();
		constrasena.setFont(constrasena.getFont().deriveFont(20f));
		constrasena.setForeground(Color.WHITE);
		constrasena.setText("constrasena: ");
		constrasena.setBackground(Color.BLACK);
		constrasena.setBounds(300,350, 150, 30);
		
		/*
		 * Field para introducir la constrasena
		 */
		final JPasswordField constrasenaText = new JPasswordField(32);
		constrasenaText.setEditable(true);
		constrasenaText.setBounds(425,350, 150, 30);
		constrasenaText.addKeyListener(new KeyListener() {			
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
				pass=constrasenaText.getText();
				System.out.println("Usuario: "+user+" constrasena: "+pass);
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
		this.add(constrasena);
		this.add(usuario);
//		
//
		this.add(constrasenaText);
		this.add(usuarioText);
//		
		this.add(botonAceptar);
		this.add(botonRegistrarse);
	}

}
