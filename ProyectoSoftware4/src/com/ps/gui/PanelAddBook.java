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
	private final JTextField textFieldPrecio;
	private final JTextField textFieldDescripcion;
	private final JTextField textFieldPath;
	private final JTextField textFieldGenero;
	private JButton buttonAdd;

	
	public PanelAddBook(final JGrid grid, final List<Book> bookList, final DbConnector db) {
		
		GridLayout layout = new GridLayout(20, 1);
		layout.setHgap(5); layout.setVgap(5);
		this.setLayout(layout);
		this.setBorder(new TitledBorder(new EtchedBorder(), "Anadir Libro"));
        
	    JLabel labelTitle = new JLabel("Titulo: ");
	    textFieldTitle = new JTextField("", 15);
        this.add(labelTitle);
        this.add(textFieldTitle);
        
	    JLabel labelAutor = new JLabel("Autor: ");
	    textFieldAutor = new JTextField("", 15);
        this.add(labelAutor);
        this.add(textFieldAutor);
        
		JLabel labelEditorial = new JLabel("Editorial: ");
		textFieldEditorial = new JTextField("", 15);
        this.add(labelEditorial);
        this.add(textFieldEditorial);
        
        JLabel labelPrecio = new JLabel("Precio: ");
		textFieldPrecio = new JTextField("", 15);
        this.add(labelPrecio);
        this.add(textFieldPrecio);
        
        JLabel labelDescripcion = new JLabel("Descripcion: ");
		textFieldDescripcion = new JTextField("", 15);
        this.add(labelDescripcion);
        this.add(textFieldDescripcion);
        
	    JLabel labelPath = new JLabel("Ruta: ");
	    textFieldPath = new JTextField("", 15);
        this.add(labelPath);
        this.add(textFieldPath);
        
	    JLabel labelGenero = new JLabel("Genero: ");
	    textFieldGenero = new JTextField("", 15);
        this.add(labelGenero);
        this.add(textFieldGenero);
       
        
        buttonAdd = new JButton("Anadir");
        buttonAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				String title = textFieldTitle.getText();
				String autor = textFieldAutor.getText();
				String path = "/" + textFieldPath.getText();
				String editorial = textFieldEditorial.getText();
				double precio = Double.parseDouble(textFieldPrecio.getText());
				String descripcion = textFieldDescripcion.getText();
				String genero = textFieldGenero.getText();
				
				int index = grid.getSelectedIndex();
				Book newBook = new Book(title, autor, path, editorial, precio, descripcion, genero);
				if (index == -1) {
	                bookList.add(newBook); 
	                db.addBook(title, autor, path, editorial, precio, descripcion, genero);
				}				
				else if (!bookList.get(index).equals(newBook) && index >= 0) {
					Book oldBook = bookList.get(index);
	                bookList.set(index, newBook);
	                db.updateBook(title, autor, path, editorial, precio, descripcion, genero,
	                		oldBook.getTitle(), oldBook.getAutor());
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
	
	public void setBook(String editorial,String title, String autor, double precio,
			String descripcion, String path, String genero) {
		textFieldEditorial.setText(editorial);
		textFieldTitle.setText(title);
		textFieldAutor.setText(autor);
		textFieldPrecio.setText(Double.toString(precio));
		textFieldDescripcion.setText(descripcion);
		textFieldPath.setText(path.substring(1));
		textFieldGenero.setText(genero);
	}
	
	public void deleteBook(String title, String autor, String path, final JGrid grid, final List<Book> bookList, final DbConnector db) {
		db.deleteBook(title, autor, path);
		int index = grid.getSelectedIndex();
        bookList.remove(index);
        grid.repaint();
	}
	
	public void setAddModifyText(String s) {
		buttonAdd.setText(s);
	}

	public void setEditable(boolean b) {
		textFieldTitle.setEditable(b);
		textFieldAutor.setEditable(b);
		textFieldTitle.setFocusable(b);
		textFieldAutor.setFocusable(b);
		
		if (!b) {
			textFieldTitle.setForeground(new Color(100, 100, 100));
			textFieldAutor.setForeground(new Color(100, 100, 100));
			textFieldTitle.setBackground(new Color(220, 220, 220));
			textFieldAutor.setBackground(new Color(220, 220, 220));
		} 
		else {
			textFieldTitle.setForeground(Color.BLACK);
			textFieldAutor.setForeground(Color.BLACK);
			textFieldTitle.setBackground(Color.WHITE);
			textFieldAutor.setBackground(Color.WHITE);
		}
	}
}
