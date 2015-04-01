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
	
	public PanelAddBook(final JGrid grid, final List<Book> bookList, final DbConnector db) {	
		GridLayout layout = new GridLayout(20, 1);
		layout.setHgap(5); layout.setVgap(5);
		this.setLayout(layout);
		this.setBorder(new TitledBorder(new EtchedBorder(), "Añadir Libro"));
		
		
	    JLabel labelTitle = new JLabel("Titulo: ");
	    final JTextField textFieldTitle = new JTextField("", 15);
        this.add(labelTitle);
        this.add(textFieldTitle);
        
	    JLabel labelAutor = new JLabel("Autor: ");
	    final JTextField textFieldAutor = new JTextField("", 15);
        this.add(labelAutor);
        this.add(textFieldAutor);
        
	    JLabel labelPath = new JLabel("Ruta: ");
	    final JTextField textFieldPath = new JTextField("", 15);
        this.add(labelPath);
        this.add(textFieldPath);
        
        JButton buttonAdd = new JButton("Añadir");
        buttonAdd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String title = textFieldTitle.getText();
				String autor = textFieldAutor.getText();
				String path = "/" + textFieldPath.getText();
                bookList.add(new Book(title, autor, path)); 
                db.addBook(title, autor, path);
                grid.repaint();
			}
        });
        this.add(buttonAdd);
	}

}
