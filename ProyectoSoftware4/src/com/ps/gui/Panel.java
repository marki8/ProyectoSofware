package com.ps.gui;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.ps.gui.gfx.DropShadow;

public class Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	private Color iniColor = new Color(200, 200, 200);
	private Color endColor = new Color(241, 241, 241);

	
	public Panel() {

    }

    @Override
    public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setPaint(new GradientPaint(0, 0, iniColor, 0, 200, endColor));
		g2.fillRect(0, 0, getWidth(), getHeight());

		// Sombra debajo de la barra de herramientas
		BufferedImage bi = new BufferedImage(getWidth(), 1, BufferedImage.TYPE_INT_RGB);
		DropShadow ds = new DropShadow(bi);
		ds.paint(g2, -2, -3);
		g2.setColor(new Color(120, 120, 120));
		g2.drawLine(0, 0, getWidth(), 0);

		g2.dispose();
    } 

}
