package com.ps.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import com.ps.common.Book;
import com.ps.db.DbConnector;

import de.jgrid.JGrid;


public class MainGUI extends JFrame {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        public MainGUI() {
                setTitle("Easy Books");
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                
                DbConnector db = null;
                try {
                    db = new DbConnector("db_file");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                
                final List<Book> bookList = db.getBooks();           
                final JGrid grid = new JGrid(new ListModel() {

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
                JPanel panel1 = new JPanel(); //Box.createVerticalBox();
                panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));  
                
                // TEST
                JPanel t = new JPanel();
                GridLayout layout = new GridLayout(4, 1);
                layout.setVgap(5);
                t.setLayout(layout);
                final JButton b1 = new JButton("Inicio");
                JButton b2 = new JButton("Mis libros");
                JButton b3 = new JButton("Opciones");
				b1.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.GRAY));
				b2.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.GRAY));
				b3.setBorder(BorderFactory.createMatteBorder(1, 5, 1, 1, Color.GRAY));
                t.add(b1);
                t.add(b2);
                t.add(b3);
                t.add(Box.createHorizontalStrut(30));
                panel1.add(t);
                
                PanelCursor pc = new PanelCursor(grid, bookList, db);
                //pc.setVisible(false);
                panel1.add(pc);
                
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