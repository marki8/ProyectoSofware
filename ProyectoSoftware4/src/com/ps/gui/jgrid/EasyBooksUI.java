package com.ps.gui.jgrid;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.UIManager;

import com.ps.gui.gfx.DropShadow;

import de.jgrid.GridUI;

public class EasyBooksUI extends GridUI {

	BufferedImage backgroundImage;
	private Color startColor = new Color(200, 200, 200);
	private Color endColor = new Color(241, 241, 241);
	private Color shadowColor = new Color(120, 120, 120);
	private String title;

	public EasyBooksUI(String title) {
		this.title = title;
	}

	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		try {
			backgroundImage = ImageIO.read(getClass().getResource("/bookshelf.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		grid.setFixedCellDimension(250); // 120
		grid.setHorizonztalMargin(0); // 18
		grid.setVerticalMargin(12); // 12
		grid.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0)); // 6, 0,
																		// 0, 0
		grid.setSelectionBackground(Color.YELLOW);
		grid.setCellBackground(Color.BLUE);
		grid.setBackground(UIManager.getColor("Panel.background"));
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		// Imagen de fondo
//		int height = 0;
//		int width = 0;
//
//		while (height < c.getHeight()) {
//			width = 0;
//			while (width < c.getWidth()) {
//				g.drawImage(backgroundImage, width, height, null);
//				width = width + backgroundImage.getWidth();
//			}
//			height = height + backgroundImage.getHeight();
//		}
		
		// Gradiente de fondo
		Graphics2D g2 = (Graphics2D)g.create();
	    g2.setPaint(new GradientPaint(0, 0, startColor, 0, 200, endColor));
	    g2.fillRect(0, 0, c.getWidth(), c.getHeight());
	    
	    // Sombra de 1 pixel debajo de la barra de herramientas
	    BufferedImage bi = new BufferedImage(c.getWidth(), 1, BufferedImage.TYPE_INT_RGB);
	    DropShadow ds = new DropShadow(bi);
	    ds.paint(g2, -2, -3);
	    g2.setColor(shadowColor);
	    g2.drawLine(0, 0, c.getWidth(), 0);
	    
	    // Texto de encabezado de grid
	    ((Graphics2D) g2).setRenderingHint(
	            RenderingHints.KEY_TEXT_ANTIALIASING,
	            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setFont(new Font("Helvetica", Font.BOLD, 20));
		g2.setColor(Color.BLACK);
		g2.drawString(title, 30, 22);
	    g2.dispose();
	    
		super.paint(g, c);
	}

	@Override
	protected void paintCellBorder(Graphics g, JComponent c, int index,
			Rectangle bounds, int leadIndex) {
//		super.paintCellBorder(g, c, index, bounds, leadIndex);
//		boolean isSelected = grid.getSelectionModel().isSelectedIndex(index);
//		if (isSelected) {
//			g2.dispose();
//		}

	}

	protected void paintCell(Graphics g, JComponent c, int index,
			Rectangle bounds, int leadIndex) {
		boolean cellHasFocus = grid.hasFocus() && (index == leadIndex);
		boolean isSelected = grid.getSelectionModel().isSelectedIndex(index);

		Graphics2D g2 = (Graphics2D) g.create();
		if (debug) {
			g2.setColor(Color.blue);
			g2.fillRect(0, 0, bounds.width, bounds.height);
		}
		Object value = grid.getModel().getElementAt(index);

		Component rendererComponent = grid.getCellRenderer(index)
				.getGridCellRendererComponent(grid, value, index, isSelected,
						cellHasFocus);
		rendererPane.add(rendererComponent);
		rendererPane.paintComponent(g2, rendererComponent, grid, bounds.x,
				bounds.y, bounds.width, bounds.height, true);

		g2.dispose();
	}
}