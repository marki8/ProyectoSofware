package com.ps.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;

import com.ps.common.Book;
import com.ps.db.DbConnector;
import com.ps.gui.jgrid.EasyBooksUI;
import com.ps.gui.jgrid.OpenLibraryGridRenderer;
import com.ps.gui.jgrid.SelectionModel;

import de.jgrid.JGrid;

public class MainGUI extends JFrame {

	/**
         * 
         */
	private static final long serialVersionUID = 1L;

	private DbConnector db = null;
	private List<Book> bookList;

	public MainGUI() {
		setTitle("Easy Books");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		try {
			db = new DbConnector("db_file");
		} catch (Exception e) {
			e.printStackTrace();
		}

		bookList = db.getBooks();
		final JGrid grid = new JGrid(new SelectionModel(bookList));
		grid.getCellRendererManager().setDefaultRenderer(new OpenLibraryGridRenderer());
		//grid.setDefaultCellRenderer(new OpenLibraryGridRenderer());
		grid.setUI(new EasyBooksUI());

		final JPanel cards = new JPanel(new CardLayout());
		cards.add(new JScrollPane(grid), "GRID");
		// cards.add(new PanelBuyBook(), "TEST");
		
		// Columna derecha
		JPanel panel1 = new JPanel(); // Box.createVerticalBox();
		panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
		panel1.add(new PanelButtons(cards));
		final PanelAddBook pab = new PanelAddBook(grid, bookList, db);
		panel1.add(pab);
		
		grid.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				int selectedIndex = grid.getCellAt(arg0.getPoint());
				if (selectedIndex >= 0) {
					try {
						final Book book = bookList.get(selectedIndex);
						if (arg0.getButton() == MouseEvent.BUTTON1) { // Click izquierdo
							if (arg0.getClickCount() == 2) {
								System.out
										.println("Seleccion " + selectedIndex);
								cards.add(new PanelBuyBook(book,db), "TEST");
								CardLayout cl = (CardLayout) (cards.getLayout());
								cl.show(cards, "TEST");

							} else {
								pab.setBook(book.getEditorial(),book.getTitle(), book.getAutor(),
										book.getPath());
							}

						} else if (arg0.getButton() == MouseEvent.BUTTON3) { // Click derecho
							JPopupMenu popup = new JPopupMenu();
							JMenuItem m = new JMenuItem("Editar");
							popup.add(m);
							ActionListener al = new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									pab.setBook(book.getEditorial(),book.getTitle(),
											book.getAutor(), book.getPath());
								}
							};
							m.addActionListener(al);
							m = new JMenuItem("Borrar");
							popup.add(m);
							ActionListener a = new ActionListener() {
								public void actionPerformed(ActionEvent e) {
									pab.deleteBook(book.getTitle(),
											book.getAutor(), book.getPath(), grid, bookList, db);
								}
							};
							m.addActionListener(a);
							popup.show(MainGUI.this, arg0.getX(), arg0.getY());
						}

					} catch (Exception e) {}
				}
				else {
					grid.getSelectionModel().clearSelection();;
				}
			}
		});

		// Barra de herramientas
		JToolBar toolBar = new JToolBar("Still draggable");
		toolBar.add(new JButton("<"));
		JButton forward = new JButton(">");
		toolBar.add(forward);

		getContentPane().add(toolBar, BorderLayout.PAGE_START);
		getContentPane().add(cards, BorderLayout.CENTER);
		getContentPane().add(panel1, BorderLayout.EAST);
		// setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(1066, 600);
	}

	public static void main(String[] args) {
		new MainGUI().setVisible(true);
	}
}