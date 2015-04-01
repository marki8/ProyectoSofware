package com.ps.gui;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.ps.common.Book;

public class PanelBuyBook extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PanelBuyBook(Book book) {
		
		this.add(new JButton(book.getTitel()));
		this.add(new JButton(book.getAutor()));
		
	}

}
