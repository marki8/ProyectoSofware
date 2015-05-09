package com.ps.gui;

import java.util.List;

import javax.swing.JScrollPane;

import com.ps.common.Book;
import com.ps.db.DbConnector;
import com.ps.gui.jgrid.EasyBooksUI;
import com.ps.gui.jgrid.OpenLibraryGridRenderer;
import com.ps.gui.jgrid.SelectionModel;

import de.jgrid.JGrid;

public class PanelPurchases extends JScrollPane {
	
	private static final long serialVersionUID = 1L;

	public PanelPurchases(DbConnector db, String user) {
		List<Book> bookList = db.getBooksBuy(user);
		JGrid grid = new JGrid(new SelectionModel(bookList));
		grid.getCellRendererManager().setDefaultRenderer(
				new OpenLibraryGridRenderer());
		grid.setUI(new EasyBooksUI("Mis compras"));
		setBorder(null);
		setViewportView(grid);
	}

}
