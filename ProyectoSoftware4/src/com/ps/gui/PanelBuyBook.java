package com.ps.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ps.common.Book;
import com.ps.db.DbConnector;

public class PanelBuyBook extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PanelBuyBook(final Book book,final DbConnector db) {

		//this.add(new JButton(book.getTitel()));
		//this.add(new JButton(book.getAutor()));
		JLabel j= new JLabel();
		j.setIcon(new ImageIcon(getClass().getResource(book.getPath()))); 
		j.setBounds(10,10,100,20); //Posición del jlabel dentro del panel
		this.add(j);
		JLabel labelTitle = new JLabel("Titulo: "+book.getTitle()+"\n");
        this.add(labelTitle);
        JLabel labelAutor = new JLabel("Autor: "+book.getAutor());
        this.add(labelAutor);
        JButton buttonBuy = new JButton("Comprar");
        buttonBuy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String user = "user";
				String password = "pass";
				int id = 25;
	            db.addBookBuy(user, password, id);				
			}
        });
        this.add(buttonBuy);
}
}