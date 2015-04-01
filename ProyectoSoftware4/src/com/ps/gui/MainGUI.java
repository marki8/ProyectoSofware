package com.ps.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

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
                @SuppressWarnings("rawtypes")
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
                
                final JPanel cards = new JPanel(new CardLayout());
                cards.add(new JScrollPane(grid), "GRID");
                //cards.add(new PanelBuyBook(), "TEST");                
                grid.addMouseListener(new MouseAdapter() {
                	
                	final int xOffset = 50;
					@Override
					public void mouseClicked(MouseEvent arg0) {
						if (arg0.getClickCount() == 2) {
		                    int selectedIndex = grid.getSelectedIndex();
		                    Rectangle r = grid.getCellBounds(selectedIndex);
		                    try {
								if (arg0.getX() > r.x + xOffset && arg0.getX() < r.x + r.width - xOffset) {
									System.out.println("Seleccion "  + selectedIndex);
									cards.add(new PanelBuyBook(bookList.get(selectedIndex)), "TEST");
							        CardLayout cl = (CardLayout)(cards.getLayout());
									cl.show(cards, "TEST");
								}
		                    } catch (Exception e) {}
						}
					}
                });
                

                // Columna derecha
                JPanel panel1 = new JPanel(); //Box.createVerticalBox();
                panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));  
                            
                panel1.add(new PanelButtons(cards));
                panel1.add(new PanelAddBook(grid, bookList, db));

                // Barra de herramientas
                JToolBar toolBar = new JToolBar("Still draggable");
                toolBar.add(new JButton("<"));
                JButton forward = new JButton(">");
                toolBar.add(forward);            
                
                getContentPane().add(toolBar, BorderLayout.PAGE_START);
                getContentPane().add(cards, BorderLayout.CENTER);
                getContentPane().add(panel1, BorderLayout.EAST);
                //setExtendedState(JFrame.MAXIMIZED_BOTH);
                setSize(1066, 600);
        }
        
        public static void main(String[] args) {
                new MainGUI().setVisible(true);
        }
}