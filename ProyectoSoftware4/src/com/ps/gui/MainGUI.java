package com.ps.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;

import com.ps.common.Book;
import com.ps.db.DbConnector;
import com.ps.gui.PanelAddBook;
import com.ps.gui.PanelButtons;
import com.ps.gui.PanelBuyBook;
import com.ps.gui.PanelToolBar;
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

		// Evento para cerrar la ventana y la base de datos
		WindowAdapter exitListener = new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				try {
					db.shutdown();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
		};
		addWindowListener(exitListener);

		// Configuramos el Look & Feel para que cada S0 use su propia GUI
//		try {
//			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//		} catch (ClassNotFoundException e1) {
//			e1.printStackTrace();
//		} catch (InstantiationException e1) {
//			e1.printStackTrace();
//		} catch (IllegalAccessException e1) {
//			e1.printStackTrace();
//		} catch (UnsupportedLookAndFeelException e1) {
//			e1.printStackTrace();
//		}
		try {
			UIManager.put("Panel.background", Color.WHITE);
			UIManager.put("nimbusBase", Color.WHITE);
			UIManager.put("nimbusBlueGrey", Color.WHITE);
			UIManager.put("control", Color.WHITE); 
		    UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");

		} catch (Exception e) {
		    e.printStackTrace();
		}

		// Iniciamos la base de datos
		try {
			db = new DbConnector("db_file");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Grid de libros
		bookList = db.getBooks();
		final JGrid grid = new JGrid(new SelectionModel(bookList));
		grid.getCellRendererManager().setDefaultRenderer(
				new OpenLibraryGridRenderer());
		// grid.setDefaultCellRenderer(new OpenLibraryGridRenderer());
		grid.setUI(new EasyBooksUI());

		final JPanel cards = new JPanel(new CardLayout());
		cards.add(new JScrollPane(grid), "GRID");
		// cards.add(new PanelBuyBook(), "TEST");

		// Columna derecha
		JPanel panel1 = new JPanel(); // Box.createVerticalBox();
//		panel1.setLayout(new BoxLayout(panel1, BoxLayout.PAGE_AXIS));
//		panel1.add(new PanelButtons(cards, db,bookList,grid));
		final PanelAddBook pab = new PanelAddBook(grid, bookList, db);
		panel1.add(pab);

		// Listener de eventos del raton para el grid de libros
		grid.addMouseListener(MouseListener(grid, cards, pab, panel1));

		// Barra de herramientas
		JToolBar toolBar = new JToolBar("Barra de herramientas");
		toolBar.setOpaque(true);
		toolBar.setBackground(Color.BLACK);
		toolBar.add(new JButton("<"));
		JButton forward = new JButton(">");
		toolBar.add(forward);
		JButton init = new JButton("Inicio");
		toolBar.add(init);
		JButton purchases = new JButton("Mis Compras");
		toolBar.add(purchases);
		JButton options = new JButton("Opciones");
		toolBar.add(options);

		//getContentPane().setBackground( Color.WHITE );
		getContentPane().add(new PanelToolBar(cards, db,bookList,grid), BorderLayout.PAGE_START);
		getContentPane().add(cards, BorderLayout.CENTER);
		getContentPane().add(panel1, BorderLayout.EAST);
		// setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(1066, 600);
	}

	public static void main(String[] args) {
		new MainGUI().setVisible(true);
	}

	/**
	 * 
	 * @param grid
	 * @param cards
	 * @param pab
	 * @param panel1 
	 * @return
	 */
	private MouseAdapter MouseListener(final JGrid grid, final JPanel cards,
			final PanelAddBook pab, final JPanel panel1) {
		MouseAdapter ma = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int selectedIndex = grid.getCellAt(arg0.getPoint());
				if (selectedIndex >= 0) {
					final Book book = bookList.get(selectedIndex);
					if (arg0.getButton() == MouseEvent.BUTTON1) { // Clic
						// izquierdo
						if (arg0.getClickCount() == 2) { // Doble clic izquierdo
							System.out.println("Seleccion " + selectedIndex);
							cards.add(new PanelBuyBook(book, db, grid), "TEST");
							CardLayout cl = (CardLayout) (cards.getLayout());
							//panel1.setVisible(false);
							cl.show(cards, "TEST");

						} else {
							pab.setBook(book.getEditorial(), book.getTitle(),
									book.getAutor(), book.getPrecio(),
									book.getDescripcion(), book.getPath());
						}

					} else if (arg0.getButton() == MouseEvent.BUTTON3) { // Clic
						// derecho
						JPopupMenu popup = new JPopupMenu();
						// Editar
						JMenuItem m = new JMenuItem("Editar");
						popup.add(m);
						m.addActionListener(setBook(grid, book, pab));
						// Borrar
						m = new JMenuItem("Borrar");
						popup.add(m);
						m.addActionListener(deleteBook(grid, book, pab));
						popup.show(MainGUI.this, arg0.getX(), arg0.getY());
					}
				} else { // Si no se hace clic en ningun libro, se deselecciona
					// todo
					grid.getSelectionModel().clearSelection();
				}
			}
		};
		return ma;
	}

	/**
	 * 
	 * @param grid
	 * @param book
	 * @param pab
	 * @return
	 */
	private ActionListener setBook(final JGrid grid, final Book book,
			final PanelAddBook pab) {
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pab.setBook(book.getEditorial(), book.getTitle(),
						book.getAutor(), book.getPrecio(),
						book.getDescripcion(), book.getPath());
			}
		};
		return al;
	}

	/**
	 * 
	 * @param grid
	 * @param book
	 * @param pab
	 * @return
	 */
	private ActionListener deleteBook(final JGrid grid, final Book book,
			final PanelAddBook pab) {
		ActionListener al = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pab.deleteBook(book.getTitle(), book.getAutor(),
						book.getPath(), grid, bookList, db);
			}
		};
		return al;
	}
}
