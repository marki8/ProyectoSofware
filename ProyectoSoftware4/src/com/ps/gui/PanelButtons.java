package com.ps.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.ps.common.Book;
import com.ps.db.DbConnector;
import com.ps.gui.jgrid.EasyBooksUI;
import com.ps.gui.jgrid.OpenLibraryGridRenderer;
import com.ps.gui.jgrid.SelectionModel;

import de.jgrid.JGrid;

public class PanelButtons extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	final String Init = "Inicio";
	final String MyBooks = "Mis Libros";
	final String Options = "Opciones";
	
	public PanelButtons(final JPanel cards,final DbConnector db,final List<Book> bookList,final JGrid grid) {
		
        GridLayout layout = new GridLayout(4, 1);
        layout.setVgap(5);
        this.setLayout(layout);
        
        final JButton b1 = new JButton(Init);
        final JButton b2 = new JButton(MyBooks);
        final JButton b3 = new JButton(Options);
        
		b1.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.GRAY));
		b2.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.GRAY));
		b3.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.GRAY));
		
        this.add(b1);
        this.add(b2);
        this.add(b3);
        //this.add(Box.createHorizontalStrut(30));
        
        b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
                bookList.clear();
				bookList.addAll(db.getBooks());
				grid.repaint();
		        CardLayout cl = (CardLayout)(cards.getLayout());
		        cl.next(cards);
			}
        });
        
        b2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
                bookList.clear();
                //bookList.addAll(db.getBooksBuy("ejemplo"));
                bookList.add(new Book("a","a","/book0.jpg","a",25,"a"));
                grid.repaint();
                CardLayout cl = (CardLayout)(cards.getLayout());
		        cl.next(cards);
			}
        });
        
        b3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
        });
		
	}

}
