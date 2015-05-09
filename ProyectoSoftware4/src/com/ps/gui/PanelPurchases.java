package com.ps.gui;

import java.awt.CardLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.ps.common.Book;
import com.ps.db.DbConnector;
import com.ps.gui.jgrid.EasyBooksUI;
import com.ps.gui.jgrid.OpenLibraryGridRenderer;
import com.ps.gui.jgrid.SelectionModel;

import de.jgrid.JGrid;

public class PanelPurchases extends JScrollPane {
	
	private static final long serialVersionUID = 1L;
	
	private DbConnector db;
	private String user;
	private List<Book> bookList;

	public PanelPurchases(DbConnector db, String user, JPanel cards) {
		this.db = db;
		this.user = user;
		bookList = db.getBooksBuy(user);
		JGrid grid = new JGrid(new SelectionModel(bookList));
		grid.getCellRendererManager().setDefaultRenderer(
				new OpenLibraryGridRenderer(user, db));
		grid.setUI(new EasyBooksUI("Mis compras"));
		setBorder(null);
		setViewportView(grid);
		grid.addMouseListener(MouseListener(grid, cards));

	}
	
	private MouseAdapter MouseListener(final JGrid grid, final JPanel cards) {
		MouseAdapter ma = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int selectedIndex = grid.getCellAt(arg0.getPoint());
				if (selectedIndex >= 0) {
					final Book book = bookList.get(selectedIndex);
					if (arg0.getButton() == MouseEvent.BUTTON1) { // Clic izquierdo
						if (arg0.getClickCount() == 2) { // Doble clic izquierdo
							PanelToolBar.checkNavigation("BOOK" + selectedIndex);
							PanelToolBar.STACK.add("BOOK" + selectedIndex);
							PanelToolBar.INDEX++;
							System.out.println("Seleccion " + selectedIndex);
							cards.add(new PanelBuyBook(cards,book, db, grid, user),
									PanelToolBar.STACK.get(PanelToolBar.STACK.size()-1));
							CardLayout cl = (CardLayout) (cards.getLayout());
							cl.show(cards, PanelToolBar.STACK.get(PanelToolBar.STACK.size()-1));
						}
					}
				} else { // Si no se hace clic en ningun libro, se deselecciona todo
					grid.getSelectionModel().clearSelection();
				}
			}	
		};
		return ma;
	}
}