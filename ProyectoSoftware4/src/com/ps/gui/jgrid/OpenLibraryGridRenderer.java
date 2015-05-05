package com.ps.gui.jgrid;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import com.ps.common.Book;
import com.ps.gui.gfx.DropShadow;

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
			
			// Sombra del libro
			DropShadow ds = new DropShadow(coverImage);

			if (isSelected) {
				ds.paintShadow(g, startX, 0);
				g2.setColor(new Color(0, 0, 0, 255));
				//ds.setShadowColor(new Color(0, 255, 255));
			} else {
				g2.setColor(new Color(0, 0, 0, 240));
			}
			ds.paintShadow(g, startX, 0);

			// Titulo del libro
			g2.setFont(new Font("Helvetica", Font.BOLD, 12));
			g2.drawString(checkTextSize(book.getTitle(), 12, coverImage.getWidth()), startX, getHeight() - 24);
			//highlightText(checkTextSize(book.getTitle(), 12, coverImage.getWidth()), "es", g2, startX, getHeight() -24);
			// Autor del libro
			g2.setFont(new Font("HelveticaLight", Font.PLAIN, 10));
			g2.drawString(checkTextSize(book.getAutor(), 10, coverImage.getWidth()), startX, getHeight() - 10);

			//g2.setColor(new Color(0, 0, 0, 160)); // 80
			//g2.setStroke(new BasicStroke(1.6f)); // 0.8f
			
			// Imagen del libro
			g2.drawImage(coverImage, startX, 0, null);
			g2.drawRect(startX, 0, coverImage.getWidth(),coverImage.getHeight());

			g2.dispose();
		}
	}
	
	private String checkTextSize(String text, int size, int widht) {
		boolean modify = false;
		for (int i = text.length() - 1; i >= 0; i--) {
			if (text.length()*size > widht + 11*size) {
				text = text.substring(0, i); 
				modify = true;
			}
			else {
				break;
			}
		}

		if (modify) return text + "...";
		return text;
	}
	
	private void highlightText(String text, String h, Graphics2D g, int xini, int yini) {
		for (int i = 0; i < text.length(); i++) {
			char ch = text.charAt(i);
			if (h.indexOf(ch) >= 0) {
				g.setFont(new Font("Helvetica", Font.BOLD, 12));
				g.drawString(String.valueOf(text.charAt(i)), xini, yini);
				xini += 8;
			}
			else {
				g.setFont(new Font("Helvetica", Font.PLAIN, 12));
				g.drawString(String.valueOf(text.charAt(i)), xini, yini);
				xini += 8;
			}
		}
	}
}
