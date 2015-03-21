package com.ps.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import com.ps.common.Book;

import de.jgrid.JGrid;


public class MainGUI extends JFrame {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        public MainGUI() {
                setTitle("Easy Books");
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                
                final List<Book> bookList = new ArrayList<Book>();
                bookList.add(new Book("La espada del destino", "Andrzej Sapkowski", "0"));
                bookList.add(new Book("Destiny of the sword", "Jeremy Twigg", "1"));
                bookList.add(new Book("Nathe the great and the Sticky Case", "Ugo Sanchez", "2"));
                bookList.add(new Book("The Iron Hell", "Jack London", "3"));
                bookList.add(new Book("The arrow of gold", "Joseph Conrad", "4"));
                bookList.add(new Book("Bold Pursuit", "Zabrina Faiere", "5"));              
                
                JGrid grid = new JGrid(new ListModel() {

                        @Override
                        public void removeListDataListener(ListDataListener l) {
                        }

                        @Override
                        public int getSize() {
                                return bookList.size();
                        }

                        @Override
                        public Object getElementAt(int index) {
                                return bookList.get(index);
                        }

                        @Override
                        public void addListDataListener(ListDataListener l) {
                        }
                });
                grid.setDefaultCellRenderer(new OpenLibraryGridRenderer());
                grid.setUI(new EasyBooksUI());   
                
                // Columna derecha
                JPanel panel1 = new JPanel();
                panel1.add(new JButton("Test"));
                
                // Barra de herramientas
                JToolBar toolBar = new JToolBar("Still draggable");
                toolBar.add(new JButton("<"));
                toolBar.add(new JButton(">"));    
                
                getContentPane().add(toolBar, BorderLayout.PAGE_START);
                getContentPane().add(new JScrollPane(grid), BorderLayout.CENTER);
                getContentPane().add(panel1, BorderLayout.EAST);
                setExtendedState(JFrame.MAXIMIZED_BOTH);
                //setSize(1066, 600);
        }
        
        public static void main(String[] args) {
                new MainGUI().setVisible(true);
        }
}