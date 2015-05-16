package com.ps.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import com.ps.common.Book;
import com.ps.db.DbConnector;
import com.ps.gui.PanelAddBook;
import com.ps.gui.PanelBuyBook;
import com.ps.gui.PanelToolBar;
import com.ps.gui.jgrid.EasyBooksUI;
import com.ps.gui.jgrid.OpenLibraryGridRenderer;
import com.ps.gui.jgrid.SelectionModel;

import de.jgrid.JGrid;

/**
 * 
 * @author 
 *
 */
public class MainGUI extends JFrame {


	private static final long serialVersionUID = 1L;

	private DbConnector db = null;
	private List<Book> bookList;
	private String user;

	public MainGUI(int opcion,String user) {
		this.user = user;
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
		try {
		    UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
		    //UIManager.put("TextField.disabledBackground", Color.GRAY);
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
		JGrid grid = new JGrid(new SelectionModel(bookList));
		grid.getCellRendererManager().setDefaultRenderer(
				new OpenLibraryGridRenderer(user, db));
		grid.setUI(new EasyBooksUI("Catalogo"));

		JPanel cards = new JPanel(new CardLayout());
		cards.setBorder(null);
		JScrollPane jsp = new JScrollPane(grid);
		jsp.setBorder(null);
		cards.add(jsp, "GRID");
		PanelToolBar.STACK.add("GRID");

		// Columna derecha (Incrustada en otro panel para que no se redimensione)
		JPanel panel1 = new JPanel();
		PanelAddBook pab = new PanelAddBook(grid, bookList, db);
		panel1.setVisible(false);
		panel1.add(pab);
		if (opcion == 1) panel1.setVisible(true);

		// Listener de eventos del raton para el grid de libros
		grid.addMouseListener(MouseListener(grid, cards, pab));
		
		// Panel de opciones
		PanelOptions po = new PanelOptions(db, user, cards, grid);		
		cards.add(po, "OPTIONS");
		
		getContentPane().add(new PanelToolBar(cards, db,bookList,grid,user), BorderLayout.PAGE_START);
		getContentPane().add(cards, BorderLayout.CENTER);
		getContentPane().add(panel1, BorderLayout.EAST);
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		setSize(1066, 600);
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MainGUI gui = new MainGUI(1, "admin");
		gui.setVisible(true);
		gui.pack();
	}

	/**
	 * 
	 * @param grid
	 * @param cards
	 * @param pab
	 * @return
	 */
	private MouseAdapter MouseListener(final JGrid grid, final JPanel cards,
			final PanelAddBook pab) {
		MouseAdapter ma = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int selectedIndex = grid.getCellAt(arg0.getPoint());
				if (selectedIndex >= 0) {
					final Book book = bookList.get(selectedIndex);
					if (arg0.getButton() == MouseEvent.BUTTON1) { // Clic
						// izquierdo
						if (arg0.getClickCount() == 2) { // Doble clic izquierdo
							PanelToolBar.checkNavigation("BOOK" + book.getAutor() + book.getTitle());
							PanelToolBar.STACK.add("BOOK" + book.getAutor() + book.getTitle());
							PanelToolBar.INDEX++;
							System.out.println("Seleccion " + book.getAutor() + book.getTitle());
							cards.add(new PanelBuyBook(cards,book, db, grid,user),
									PanelToolBar.STACK.get(PanelToolBar.STACK.size()-1));
							CardLayout cl = (CardLayout) (cards.getLayout());
							//panel1.setVisible(false);
							cl.show(cards, PanelToolBar.STACK.get(PanelToolBar.STACK.size()-1));
						} else {
							pab.setAddModifyText("Modificar");
							pab.setBook(book.getEditorial(), book.getTitle(),
									book.getAutor(), book.getPrecio(),
									book.getDescripcion(), book.getPath(), book.getGenero());
							pab.setEditable(false);
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
					pab.setAddModifyText("Anadir");
					pab.setEditable(true);
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
						book.getDescripcion(), book.getPath(), book.getGenero());
				System.out.println("Libro selecionado");
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
