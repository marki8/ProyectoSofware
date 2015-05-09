package com.ps.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * Clase que implementa una puntuacion de estrellas, es necesario definir una posicion
 * @author joseangel
 *
 */
public class PuntuacionEstrellas extends JComponent implements MouseMotionListener,MouseListener{
	
	private static final long serialVersionUID = 1L;
	private BufferedImage EstrellaVacia,Estrella;
	private int xpos,ypos,puntuacion;
	private boolean Seleccionado;
	
	public PuntuacionEstrellas(int xpos,int ypos){
		try {
			this.setBounds(xpos, ypos, 5*70, 64);
			this.EstrellaVacia=ImageIO.read(new File("img/assets/estrella Vacia.png"));
			this.Estrella=ImageIO.read(new File("img/assets/estrella.png"));
			this.puntuacion=0;
			this.xpos=xpos;
			this.ypos=ypos;
			this.Seleccionado=false;
			this.addMouseListener(this);
			this.addMouseMotionListener(this);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int GetPuntuacion(){
		return this.puntuacion;
	}
	/*
	 * Obtiene la puntuacion dependiendo del punto dado
	 */
	private void GetPuntuacion(Point p){
			//si el punto se encuentra dentro de las estrellas
		if(ypos<p.getY() && p.getY()<ypos+64){
			if(xpos<p.getX() && p.getX()<xpos+(35*5)) this.puntuacion=((p.x-xpos)/35)+1;
			
		}
		else this.puntuacion=0;
		repaint();
	}
	
	private void HacerDibujo(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(0.5, 0.5);
        for(int i=0;i<this.puntuacion;i++)
        	g2d.drawImage(this.Estrella,xpos+(i*70),ypos, null);
        for(int i=this.puntuacion;i<5;i++)
        	g2d.drawImage(this.EstrellaVacia,xpos+(i*70),ypos, null);
    }
	
	
	 public void paintComponent(Graphics g) {
	        super.paintComponent(g);
	        HacerDibujo(g);
	  }

	@Override
	public void mouseClicked(MouseEvent arg0) {
		this.Seleccionado=!this.Seleccionado;
		System.out.println("Puntucacion selecionada: "+ this.Seleccionado);
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(!Seleccionado){
			Point p= arg0.getPoint();
			GetPuntuacion(p);
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		if(!Seleccionado){
			Point p= arg0.getPoint();
			GetPuntuacion(p);
		}
		
	}

}
