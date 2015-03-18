package com.ps.gui;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import com.ps.common.SiguienteException;

import java.text.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Vector;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	public static final String mapname = "img/book.jpg";

    String s;      
	JPanel lista = new JPanel();
    PanelPosition panelposition;
    Map	imagen;
    PanelCursor	     panelcursor;
    //User usuario;
    Vector<String> vector_usuarios;
    String username;
    JList usuarios;		// Lista de Usuarios que se incluye en el JScrollPane
    JScrollPane lista1; // Lista de usuarios con scroll
    
    ArrayList<Map> imgs;
    
    public Main(String username, String pass)
    {
    	this.s = username;
        imagen = new Map(mapname);
        panelposition = new PanelPosition();
		panelcursor = new PanelCursor();
		imgs = new ArrayList<Map>();
    }
    
    public void start() throws SiguienteException
    {    	
	    this.setTitle("Catalogo de libros");

		// Si se obtuvo una cadena...
		if ((s != null) && (s.length() > 0)) {
			this.setTitle("EasyBook - "+s);
			username=s;
		    distribute();
		}
		else
			this.dispose();
     }

	private void distribute() throws SiguienteException 
	{
	    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    
	    //Creamos un panel para la derecha, en el centro esta el mapa
	    BorderLayout layout = new BorderLayout();
	    
	    JPanel right = new JPanel(); 
	    final JPanel data = new JPanel();
	    right.add(data,BorderLayout.CENTER);
	    
	    //En el panel de la derecha, donde estan los datos, vamos a ponerlos uno debajo de
	    //otro (BoxLayout)
	    BoxLayout dataLayout = new BoxLayout(data,BoxLayout.Y_AXIS);
	    data.setLayout(dataLayout);
	    
	    JPanel viewportPanel = new JPanel();
	    
	    imgs.add(imagen); imgs.add(imagen);
		viewportPanel.add(imagen,BorderLayout.PAGE_END);
	    //viewportPanel.add(imagen,BorderLayout.CENTER);
	    this.getContentPane().setLayout(layout);
	    this.getContentPane().add(new JScrollPane(viewportPanel),BorderLayout.CENTER);
	    this.getContentPane().add(right,BorderLayout.EAST);
	    
	    data.add(panelposition);
	    TitledBorder titleCoordenadas = BorderFactory.createTitledBorder("Coordenadas locales");
	    panelposition.setBorder(titleCoordenadas); 
	    
	    // Obtiene los usuarios m�s cercanos y los inserta en un JScrollPane
	    vector_usuarios = new Vector<String>();
	    TitledBorder titleLista = BorderFactory.createTitledBorder("Coordenadas en Base de Datos");
	    lista.setBorder(titleLista);

	    usuarios = new JList(vector_usuarios);
	    lista1 = new JScrollPane(usuarios);
	    data.add(lista1);
	    
	    // Boton actualizar
	    JButton botonUpdate = new JButton("Actualizar");
	    data.add(botonUpdate, BorderLayout.LINE_START);
	    
	    // Boton seleccionar
	    JButton botonSelect = new JButton("Seleccionar");
	    data.add(botonSelect, BorderLayout.LINE_END);

	    data.add(panelcursor);
	    TitledBorder titleCursor = BorderFactory.createTitledBorder("Coordenadas del cursor");
	    panelcursor.setBorder(titleCursor);
	    
		// Listeners
		// Mapa
	    imagen.addMouseListener(new MouseAdapter() {
	    	public void mousePressed(MouseEvent e)
	    	{	
	    		//updateCoordinatesFromMap(e.getX(),e.getY());
	    	}
	    });
	   
	    imagen.addMouseMotionListener(new MouseMotionAdapter() {
	    	public void mouseMoved(MouseEvent e)
	    	{	
	    		//updateStatusPanel(e.getX(),e.getY());
	    	}
	    });
	    
	    panelposition.addChangeListener(new ChangeListener() {
	    	public void stateChanged(ChangeEvent e) 
	    	{  
	    	    //updateImage(); 
	    	    //updateDatabaseFromCoordinates(); 
	    	}
	    });
		
		this.pack();
	    this.setVisible(true);
	}
}
