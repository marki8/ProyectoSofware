package com.ps.gui.jgrid;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import com.ps.common.Book;
import com.ps.gui.DropShadow;

import de.jgrid.JGrid;
import de.jgrid.renderer.GridCellRenderer;

public class OpenLibraryGridRenderer extends JComponent implements GridCellRenderer {

	/**
         * 
         */
	private static final long serialVersionUID = 1L;
	private Book book;
	private boolean isSelected;

	@Override
	public Component getGridCellRendererComponent(JGrid grid, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		this.book = null;
		this.isSelected = isSelected;
		if (value instanceof Book) {
			this.book = (Book) value;
		}

		return this;
	}

	@Override
	public String getToolTipText() {
		if (book != null) {
			return book.getTitle();
		}
		return super.getToolTipText();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (book != null && book.getCover() != null) {
			int width = book.getCover().getWidth();
			int height = book.getCover().getHeight();

			float widthFactor = (float) getWidth()
					/ (float) book.getCover().getWidth();
			float heightFactor = (float) (getHeight() - 40)
					/ (float) book.getCover().getHeight();

			if (widthFactor < heightFactor) {
				width = (int) ((float) book.getCover().getWidth() * widthFactor);
				height = (int) ((float) book.getCover().getHeight() * widthFactor);
			} else {
				width = (int) ((float) book.getCover().getWidth() * heightFactor);
				height = (int) ((float) book.getCover().getHeight() * heightFactor);
			}

			int startX = (getWidth() - width) / 2;
			// int startY = getHeight() - height;

			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			BufferedImage coverImage = ImageUtilities.getOptimalScalingImage(
					book.getCover(), width, height);
			//g2.setStroke(new BasicStroke(2.5f));

//			if (isSelected) {
//				g2.setColor(new Color(0, 0, 0, 160));
//			} else {
//				g2.setColor(new Color(0, 0, 0, 100));
//			}

//			g2.fillOval(startX - 8, coverImage.getHeight() - 8, coverImage.getWidth() + 16, 8);
//			Polygon shape = new Polygon();

			// Sombra inferior
//			shape.addPoint(startX - 8, coverImage.getHeight() - 2);
//			shape.addPoint(startX - 8 + coverImage.getWidth() + 16, coverImage.getHeight() - 2);
//
//			// Sombra izquierda
//			shape.addPoint(startX + coverImage.getWidth() + coverImage.getWidth() / 20,
//					coverImage.getHeight() / 20);
//			shape.addPoint(startX + coverImage.getWidth(), 3);
//
//			// Sombra derecha
//			shape.addPoint(startX, 2);
//			shape.addPoint(startX - coverImage.getWidth() / 20, coverImage.getHeight() / 20);

			g2.setFont(new Font("Helvetica", Font.BOLD, 12));
			g2.drawString(book.getTitle(), startX, getHeight() - 24);
			g2.setFont(new Font("HelveticaLight", Font.PLAIN, 10));
			g2.drawString(book.getAutor(), startX, getHeight() - 10);
//			g2.fill(shape);

//			g2.setColor(new Color(0, 0, 0, 160)); // 80
//			g2.setStroke(new BasicStroke(1.6f)); // 0.8f
			DropShadow ds = new DropShadow(coverImage);
			ds.paintShadow(g, startX, 0);
			g2.drawImage(coverImage, startX, 0, null);
			g2.drawRect(startX, 0, coverImage.getWidth(),coverImage.getHeight());

			g2.dispose();
		}
	}
}
