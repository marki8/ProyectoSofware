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
	private final JTextField textFieldEditorial;
	private final JTextField textFieldTitle;
	private final JTextField textFieldAutor;
	private final JTextField textFieldPath;

	
	public PanelAddBook(final JGrid grid, final List<Book> bookList, final DbConnector db) {
		
		GridLayout layout = new GridLayout(20, 1);
		layout.setHgap(5); layout.setVgap(5);
		this.setLayout(layout);
		this.setBorder(new TitledBorder(new EtchedBorder(), "Anadir Libro"));
		
		JLabel labelEditorial = new JLabel("Editorial: ");
		textFieldEditorial = new JTextField("", 15);
        this.add(labelEditorial);
        this.add(textFieldEditorial);
        
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
        
        JButton buttonAdd = new JButton("Anadir");
        buttonAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String editorial =textFieldEditorial.getText();
				String title = textFieldTitle.getText();
				String autor = textFieldAutor.getText();
				String path = "/" + textFieldPath.getText();
				int index = grid.getSelectedIndex();
				Book newBook = new Book(title, autor, path, editorial);
				if(index==-1) {
	                bookList.add(newBook); 
	                db.addBook(title, autor, path, null);
				}				
				else if (!bookList.get(index).equals(newBook) && index >= 0) {
	                bookList.set(index, newBook);
	                //db.editBook(title, autor, path);
				} 
				
                grid.repaint();
			}
        });
        this.add(buttonAdd);
        
        JButton buttonDelete = new JButton("Borrar");
        buttonDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String title = textFieldTitle.getText();
				String autor = textFieldAutor.getText();
				String path = "/" + textFieldPath.getText();
				int index = grid.getSelectedIndex();
	            db.deleteBook(title, autor, path);
	            bookList.remove(index);
                grid.repaint();
			}
        });
        this.add(buttonDelete);
	}
	
	public void setBook(String title, String autor, String path) {
		textFieldTitle.setText(title);
		textFieldAutor.setText(autor);
		textFieldPath.setText(path.substring(1));
	}
	
	public void deleteBook(String title, String autor, String path,final JGrid grid, final List<Book> bookList, final DbConnector db) {
		db.deleteBook(title, autor, path);
		int index = grid.getSelectedIndex();
        bookList.remove(index);
        grid.repaint();
	}

}
