package com.ps.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import de.jgrid.JGrid;

import com.ps.common.Book;
import com.ps.db.DbConnector;

public class PanelAddBook extends JPanel  {

	private static final long serialVersionUID = 1L;
	private final JTextField textFieldTitle;
	private final JTextField textFieldAutor;
	private final JTextField textFieldPath;

	
	public PanelAddBook(final JGrid grid, final List<Book> bookList, final DbConnector db) {
		
		GridLayout layout = new GridLayout(20, 1);
		layout.setHgap(5); layout.setVgap(5);
		this.setLayout(layout);
		this.setBorder(new TitledBorder(new EtchedBorder(), "A�adir Libro"));
		
		
	    JLabel labelTitle = new JLabel("Titulo: ");
	    textFieldTitle = new JTextField("", 15);
        this.add(labelTitle);
        this.add(textFieldTitle);
        
	    JLabel labelAutor = new JLabel("Autor: ");
	    textFieldAutor = new JTextField("", 15);
        this.add(labelAutor);
        this.add(textFieldAutor);
        
	    JLabel labelPath = new JLabel("Ruta: ");
	    textFieldPath = new JTextField("", 15);
        this.add(labelPath);
        this.add(textFieldPath);
        
        JButton buttonAdd = new JButton("A�adir");
        buttonAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String title = textFieldTitle.getText();
				String autor = textFieldAutor.getText();
				String path = "/" + textFieldPath.getText();
				int index = grid.getSelectedIndex();
				Book newBook = new Book(title, autor, path);
				if (!bookList.get(index).equals(newBook) && index >= 0) {
	                bookList.set(index, newBook);
	                //db.editBook(title, autor, path);
				} 
				else {
	                bookList.add(newBook); 
	                db.addBook(title, autor, path);
				}
                grid.repaint();
			}
        });
        this.add(buttonAdd);
	}
	
	public void setBook(String title, String autor, String path) {
		textFieldTitle.setText(title);
		textFieldAutor.setText(autor);
		textFieldPath.setText(path.substring(1));
	}

}
