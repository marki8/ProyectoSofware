package com.ps.gui.jgrid;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RadialGradientPaint;
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

	@Override
	public void installUI(JComponent c) {
		super.installUI(c);
		try {
			backgroundImage = ImageIO.read(getClass().getResource(
					"/bookshelf.png"));
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
		Graphics2D g2 = (Graphics2D)g.create();
	    g2.setPaint(new GradientPaint(0, 0, new Color(200, 200, 200), 0, 200, new Color(241, 241, 241)));
	    g2.fillRect(0, 0, c.getWidth(), c.getHeight());
	    
	    // Sombra debajo de la barra de herramientas
	    BufferedImage bi = new BufferedImage(c.getWidth(), 1, BufferedImage.TYPE_INT_RGB );
	    DropShadow ds = new DropShadow(bi);
	    ds.paint(g2, -2, -3);
	    g2.setColor(new Color(120, 120, 120));
	    g2.drawLine(0, 0, c.getWidth(), 0);
	    
	    g2.dispose();
//		g.setFont(new Font("Helvetica", Font.BOLD, 20));
//		g.setColor(Color.BLACK);
//		g.drawString("Catalogo", 10, 10);

		super.paint(g, c);
	}

	@Override
	protected void paintCellBorder(Graphics g, JComponent c, int index,
			Rectangle bounds, int leadIndex) {
		// TODO Auto-generated method stub
		// super.paintCellBorder(g, c, index, new Rectangle(bounds.x + 50,
		// bounds.y, 150, 250), leadIndex);
		//super.paintCellBorder(g, c, index, bounds, leadIndex);
//		boolean isSelected = grid.getSelectionModel().isSelectedIndex(index);
//		if (isSelected) {
//			Graphics2D g2 = (Graphics2D) g.create();
//			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
//
//			Rectangle spotlightRect = new Rectangle(bounds.getLocation(),bounds.getSize());
//			spotlightRect.y = spotlightRect.y - 58;
//
//			int lightHeight = spotlightRect.height - (spotlightRect.height / 5); // 10
//			int topWidth = (spotlightRect.width / 5); // 10
//			int lightWidth = (spotlightRect.width / 10); // 20
//
//			Polygon shape = new Polygon();
//			shape.addPoint(spotlightRect.x + (spotlightRect.width / 2) - (topWidth / 2), spotlightRect.y);
//			shape.addPoint(spotlightRect.x + lightWidth, spotlightRect.y + lightHeight);
//			shape.addPoint(spotlightRect.x + spotlightRect.width - lightWidth, spotlightRect.y + lightHeight);
//			shape.addPoint(spotlightRect.x + (spotlightRect.width / 2) + (topWidth / 2), spotlightRect.y);
//
//			g2.setStroke(new BasicStroke(1.5f));
//			g2.setPaint(new GradientPaint(spotlightRect.x, spotlightRect.y,
//					new Color(255, 255, 255, 130), spotlightRect.x,
//					spotlightRect.y - 5 + lightHeight, new Color(255, 255, 255, 0)));
//			g2.fill(shape);
//			g2.draw(shape);
//
//			RadialGradientPaint radialGradient = new RadialGradientPaint(
//					new Point(spotlightRect.x + (spotlightRect.width / 2),
//							spotlightRect.y), lightHeight / 2, new float[] {
//							0.0f, 1.0f }, new Color[] {
//							new Color(255, 255, 255),
//							new Color(255, 255, 255, 0) });
//			g2.setPaint(radialGradient);
//			g2.fill(shape);
//
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