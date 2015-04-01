package com.ps.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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
                        backgroundImage = ImageIO.read(getClass().getResource("/bookshelf.png"));
                } catch (IOException e) {
                        e.printStackTrace();
                }
                grid.setFixedCellDimension(250); // 120
                grid.setHorizonztalMargin(0); // 18
                grid.setVerticalMargin(25);    // 12
                grid.setBorder(BorderFactory.createEmptyBorder(58, 0, 0, 0)); // 6, 0, 0, 0
                grid.setSelectionBackground(Color.YELLOW);
                grid.setCellBackground(Color.BLUE);
                grid.setBackground(UIManager.getColor ("Panel.background"));
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
        	//super.paintCellBorder(g, c, index, new Rectangle(bounds.x + 50, bounds.y, 150, 250), leadIndex);
        	//super.paintCellBorder(g, c, index, bounds, leadIndex);

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

                Component rendererComponent = grid.getCellRenderer()
                                .getGridCellRendererComponent(grid, value, index, isSelected,
                                                cellHasFocus);
                rendererPane.add(rendererComponent);
                rendererPane.paintComponent(g2, rendererComponent, grid, bounds.x, bounds.y,
                                bounds.width, bounds.height, true);

                g2.dispose();
        }
}