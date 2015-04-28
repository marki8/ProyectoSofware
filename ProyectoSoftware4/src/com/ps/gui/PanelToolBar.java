package com.ps.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class PanelToolBar extends JToolBar implements Action{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PanelToolBar() {
		//setLayout(new BorderLayout(10, 10));
		
		setName("Barra de herramientas");
		setOpaque(true);
		setFloatable(false);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new BorderLayout(10, 10));
		
        JPanel b1 = new JPanel();
		// Boton hacia atras y hacia delante
		JButton backward = new GradientButton("");
		JButton forward = new GradientButton("");
		try {
			backward.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/b.png"))));
			forward.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/f.png"))));
		} catch (IOException ex) {}
		backward.setForeground(Color.WHITE);
		forward.setForeground(Color.WHITE);
		backward.addActionListener(backwardButton());
		forward.addActionListener(forwardButton());
		b1.add(backward);
		b1.add(forward);
		add(b1, BorderLayout.WEST);
		

        JPanel b2 = new JPanel();
		// Boton de inicio
		JButton init = new GradientButton("Inicio");
		init.setForeground(Color.WHITE);
		init.addActionListener(initButton());
		b2.add(init);
		// Boton de mis compras
		JButton purchases = new GradientButton("Mis Compras");
		purchases.setForeground(Color.WHITE);
		purchases.addActionListener(purchasesButton());
		b2.add(purchases);
		// Boton de opciones
		JButton options = new GradientButton("Opciones");
		options.setForeground(Color.WHITE);
		options.addActionListener(optionsButton());
		b2.add(options);
		add(b2, BorderLayout.CENTER);
		
		// Barra de busqueda
        JPanel search = new JPanel();
		JTextField field = new JTextField(15);
		field.setText("Buscar");
		search.add(field);
	    add(search, BorderLayout.EAST);
	    
		setBorder(BorderFactory.createEmptyBorder(1,1,1,1));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getValue(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putValue(String key, Object value) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void paintComponent(Graphics g){
	    Graphics2D g2 = (Graphics2D)g.create();

	    g2.setPaint(new GradientPaint(0, 0, new Color(90, 90, 90), 0, getHeight(), new Color(76, 76, 76)));
	    g2.fillRect(0, 0, getWidth(), getHeight());
	    g2.setColor(Color.BLACK);
		g2.drawRect(-1, -1, getWidth() + 1, getHeight());
	    g2.dispose();
	}
	
	private ActionListener backwardButton() {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("<");
			}
        };
		return al;
	}
	
	private ActionListener forwardButton() {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(">");
			}
        };
		return al;
	}
	
	private ActionListener initButton() {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Inicio");
			}
        };
		return al;
	}
	
	private ActionListener purchasesButton() {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Mis libros");
			}
        };
		return al;
	}
	
	private ActionListener optionsButton() {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Opciones");
			}
        };
		return al;
	}
}