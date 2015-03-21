package com.ps.gui;

import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JComponent;

import com.ps.common.Book;

import de.jgrid.GridCellRenderer;
import de.jgrid.JGrid;

public class OpenLibraryGridRenderer extends JComponent implements GridCellRenderer {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;
        private Book book;
        
        @Override
        public Component getGridCellRendererComponent(JGrid grid, Object value,
                        int index, boolean isSelected, boolean cellHasFocus) {
                this.book = null;
                
                if(value instanceof Book) {
                        this.book = (Book) value;
                }
                
                return this;
        }
        
        @Override
        public String getToolTipText() {
                if(book != null) {
                        return book.getTitel();
                }
                return super.getToolTipText();
        }

        @Override
        protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (book != null && book.getCover() != null) {
                        int width = book.getCover().getWidth();
                        int height = book.getCover().getHeight();
                        
                        float widthFactor = (float)getWidth() / (float)book.getCover().getWidth();
                        float heightFactor = (float)(getHeight() - 40) / (float)book.getCover().getHeight();
                        
                        if (widthFactor < heightFactor) {
                                width = (int) ((float)book.getCover().getWidth() * widthFactor);
                                height = (int) ((float)book.getCover().getHeight() * widthFactor);
                        } else {
                                width = (int) ((float)book.getCover().getWidth() * heightFactor);
                                height = (int) ((float)book.getCover().getHeight() * heightFactor);
                        }
                   
                        int startX = (getWidth() - width) / 2;
                        //int startY = getHeight() - height;
                                
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
                        
                        g2.drawImage(ImageUtilities.getOptimalScalingImage(book.getCover(), width, height), startX, 0,null);
                        g2.setFont(new Font("Helvetica", Font.PLAIN, 12)); 
                        g2.drawString(book.getTitel(), startX, getHeight() - 24);
                        g2.setFont(new Font("HelveticaLight", Font.PLAIN, 10)); 
                        g2.drawString(book.getAutor(), startX, getHeight() - 10);
                        g2.dispose();
                }
        }
}
