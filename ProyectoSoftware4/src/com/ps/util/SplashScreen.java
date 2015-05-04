package com.ps.util;

import java.awt.Color;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Label;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JWindow;

public class SplashScreen extends JWindow {
	
	private BufferedImage logo;
	private ImageIcon iconoCarga;
	private MediaTracker tracker;
	private int duration;
	private JFrame frame;
	private JPanel panel;
	
	public SplashScreen(int duration){
		this.duration=duration;
		this.setLayout(null);
		this.setBackground(new Color(78, 197, 255));
		
		this.setSize(1000, 800);
		this.setVisible(true);
		
		this.frame=new JFrame();
		//this.frame.setSize(800, 1000);
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setVisible(true);
		this.panel=new JPanel();
		this.panel.setSize(800, 1000);
		this.frame.add(this.panel);
		this.dispose();
		InitSplash();
		
	}
	
	public void InitSplash(){
		LoadImage();	
		try {Thread.sleep(duration);} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		LoadLoginData();
		System.out.println("CArgo opciotndgad");
	}
	
	private void LoadImage(){
		try {
			this.logo=ImageIO.read(new File("assets/logotipo.png"));
			JLabel logolabel= new JLabel(new ImageIcon(this.logo));
			logolabel.setSize(50,50);
			this.panel.add(logolabel);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private void LoadIcon(){
		this.iconoCarga=new ImageIcon("assets/loading.gif");
		this.iconoCarga.setImageObserver(this);
		JLabel panelaux=new JLabel(this.iconoCarga);
		
		
		panelaux.setHorizontalTextPosition(JLabel.LEFT);
		panelaux.setVerticalTextPosition(JLabel.BOTTOM);
	    
	    
		this.add(panelaux);
	}
	
	private void LoadLoginData(){
		
		/*
		 * Texto de usuario
		 */
		JLabel usuario= new JLabel();
		usuario.setFont(usuario.getFont().deriveFont(30f));
		usuario.setText("Usuario: ");
		usuario.setBackground(Color.BLACK);
		usuario.setBounds((1000/2)+300,(800/2)+this.logo.getHeight(), 150, 30);
		
		/*
		 * Panel para introducir el usuario
		 */
		JTextField usuarioText = new JTextField(32);
		usuarioText.setEditable(true);
		usuarioText.setBounds((1000/2)+500,(800/2)+this.logo.getHeight(), 150, 30);
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
		
		/*
		 * Texto de la contraseña
		 */
		JLabel contraseña= new JLabel();
		contraseña.setFont(contraseña.getFont().deriveFont(30f));
		contraseña.setText("Contraseña: ");
		contraseña.setBackground(Color.BLACK);
		contraseña.setBounds((1000/2)+300,(800/2)+this.logo.getHeight()+50, 200, 30);
		
		/*
		 * Field para introducir la contraseña
		 */
		JTextField contraseñaText = new JTextField(32);
		contraseñaText.setEditable(true);
		contraseñaText.setBounds((1000/2)+500,(800/2)+this.logo.getHeight()+50, 150, 30);
		contraseñaText.addKeyListener(new KeyListener() {			
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
		
		
		
		/*
		 * Boton aceptar
		 */
		
		JButton botonAceptar=  new JButton("Aceptar");
		botonAceptar.setFont(botonAceptar.getFont().deriveFont(20f));
		botonAceptar.setBounds((1000/2)+300, (800/2)+this.logo.getHeight()+125, 150, 50);
		botonAceptar.addActionListener(new ActionListener() {
			
			//Funcion que se activa cuando se pulsa el boton 
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		JButton botonRegistrarse=  new JButton("Registrarse");
		botonRegistrarse.setFont(botonAceptar.getFont().deriveFont(20f));
		botonRegistrarse.setBounds((1000/2)+500, (800/2)+this.logo.getHeight()+125, 150, 50);
		botonRegistrarse.addActionListener(new ActionListener() {
			
			//Funcion que se activa cuando se pulsa el boton 
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		/*
		 * Añadimos los componentes
		 */
		this.panel.add(contraseña);
		this.panel.add(usuario);
		

		this.panel.add(contraseñaText);
		this.panel.add(usuarioText);
		
		this.panel.add(botonAceptar);
		this.panel.add(botonRegistrarse);
	}
	
	private void HacerDibujo(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(0.5, 0.5);
        g2d.drawImage(this.logo,(1000/2)+(int)(this.logo.getWidth()*0.15),
        		(800/2)-200, null);
    }
	
	
	private Font getFont(String name) {
	    Font font = null;
	    try {
			font=Font.createFont(Font.TRUETYPE_FONT, new File(name));
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return font;
	  }

}
