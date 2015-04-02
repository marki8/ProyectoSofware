package com.ps.gui.jgrid;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
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
		grid.setVerticalMargin(25); // 12
		grid.setBorder(BorderFactory.createEmptyBorder(58, 0, 0, 0)); // 6, 0,
																		// 0, 0
		grid.setSelectionBackground(Color.YELLOW);
		grid.setCellBackground(Color.BLUE);
		grid.setBackground(UIManager.getColor("Panel.background"));
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		int height = 0;
		int width = 0;

		while (height < c.getHeight()) {
			width = 0;
			while (width < c.getWidth()) {
				g.drawImage(backgroundImage, width, height, null);
				width = width + backgroundImage.getWidth();
			}
			height = height + backgroundImage.getHeight();
		}
		super.paint(g, c);
	}

	@Override
	protected void paintCellBorder(Graphics g, JComponent c, int index,
			Rectangle bounds, int leadIndex) {
		// TODO Auto-generated method stub
		// super.paintCellBorder(g, c, index, new Rectangle(bounds.x + 50,
		// bounds.y, 150, 250), leadIndex);
		//super.paintCellBorder(g, c, index, bounds, leadIndex);
		boolean isSelected = grid.getSelectionModel().isSelectedIndex(index);
		if (isSelected) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);

			Rectangle spotlightRect = new Rectangle(bounds.getLocation(),bounds.getSize());
			spotlightRect.y = spotlightRect.y - 58;

			int lightHeight = spotlightRect.height - (spotlightRect.height / 5); // 10
			int topWidth = (spotlightRect.width / 5); // 10
			int lightWidth = (spotlightRect.width / 10); // 20

			Polygon shape = new Polygon();
			shape.addPoint(spotlightRect.x + (spotlightRect.width / 2) - (topWidth / 2), spotlightRect.y);
			shape.addPoint(spotlightRect.x + lightWidth, spotlightRect.y + lightHeight);
			shape.addPoint(spotlightRect.x + spotlightRect.width - lightWidth, spotlightRect.y + lightHeight);
			shape.addPoint(spotlightRect.x + (spotlightRect.width / 2) + (topWidth / 2), spotlightRect.y);

			g2.setStroke(new BasicStroke(1.5f));
			g2.setPaint(new GradientPaint(spotlightRect.x, spotlightRect.y,
					new Color(255, 255, 255, 130), spotlightRect.x,
					spotlightRect.y - 5 + lightHeight, new Color(255, 255, 255, 0)));
			g2.fill(shape);
			g2.draw(shape);

			RadialGradientPaint radialGradient = new RadialGradientPaint(
					new Point(spotlightRect.x + (spotlightRect.width / 2),
							spotlightRect.y), lightHeight / 2, new float[] {
							0.0f, 1.0f }, new Color[] {
							new Color(255, 255, 255),
							new Color(255, 255, 255, 0) });
			g2.setPaint(radialGradient);
			g2.fill(shape);

			g2.dispose();
		}

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