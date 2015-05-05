package com.ps.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.ps.common.Book;
import com.ps.db.DbConnector;
import com.ps.gui.gfx.DropShadow;
import com.ps.gui.gfx.GradientButton;
import com.ps.gui.gfx.JSearchTextField;

import de.jgrid.JGrid;

public class PanelToolBar extends JToolBar implements Action{
	
	private static final long serialVersionUID = 1L;

	// Componentes
	private JButton backward;
	private JButton forward;
	private JButton init;
	private JButton purchases;
	private JButton options;
	private JSearchTextField field;


	public PanelToolBar(JPanel cards, DbConnector db, List<Book> bookList, JGrid grid) {
		//setLayout(new BorderLayout(10, 10));
		
		setName("Barra de herramientas");
		setOpaque(true);
		setFloatable(false);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new BorderLayout(10, 10));
		
        JPanel b1 = new JPanel();
		// Boton hacia atras y hacia delante
		backward = new GradientButton("");
		forward = new GradientButton("");
		try {
			backward.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/b.png"))));
			forward.setIcon(new ImageIcon(ImageIO.read(getClass().getResource("/f.png"))));
		} catch (IOException ex) {}
		backward.setForeground(Color.WHITE);
		forward.setForeground(Color.WHITE);
		backward.addActionListener(backwardButton(cards));
		forward.addActionListener(forwardButton(cards));
		b1.add(backward);
		b1.add(forward);
		add(b1, BorderLayout.WEST);		

        JPanel b2 = new JPanel();
		// Boton de inicio
		init = new GradientButton("Inicio");
		init.setForeground(Color.WHITE);
		init.addActionListener(initButton(cards, db, bookList, grid));
		b2.add(init);
		// Boton de mis compras
		purchases = new GradientButton("Mis Compras");
		purchases.setForeground(Color.WHITE);
		purchases.addActionListener(purchasesButton(cards, db, bookList, grid));
		b2.add(purchases);
		// Boton de opciones
		options = new GradientButton("Opciones");
		options.setForeground(Color.WHITE);
		options.addActionListener(optionsButton(cards,db));
		b2.add(options);
		add(b2, BorderLayout.CENTER);
		
		// Barra de busqueda
        JPanel search = new JPanel();
        field = new JSearchTextField(15);
        field.setIcon(new ImageIcon("img/search_icon.png"));
        field.getDocument().addDocumentListener(searchField(db, grid, bookList));
		search.add(field);
	    add(search, BorderLayout.EAST);
	    
		setBorder(BorderFactory.createEmptyBorder(1,1,1,1));

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object getValue(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void putValue(String key, Object value) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void paintComponent(Graphics g){
	    Graphics2D g2 = (Graphics2D)g.create();
	    g2.setPaint(new GradientPaint(0, 0, new Color(90, 90, 90), 0, getHeight(), new Color(76, 76, 76)));
	    g2.fillRect(0, 0, getWidth(), getHeight());
	    g2.setColor(Color.BLACK);
	    g2.setColor(new Color(40, 40, 40));
		g2.drawLine(0,  getHeight() - 1, getWidth(), getHeight() - 1);
	    BufferedImage bi = new BufferedImage(getWidth(), 1, BufferedImage.TYPE_INT_RGB );
	    DropShadow ds = new DropShadow(bi);
	    ds.paint(g2, -1, -2);
	    g2.setColor(new Color(40, 40, 40));
	    g2.drawLine(0, 0, getWidth(), 0);
	    g2.dispose();
	}
	
	/**
	 * 
	 * @param cards
	 * @return
	 */
	private ActionListener backwardButton(final JPanel cards) {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("<");
		        CardLayout cl = (CardLayout)(cards.getLayout());
		        cl.previous(cards);
			}
        };
		return al;
	}
	
	/**
	 * 
	 * @param cards
	 * @return
	 */
	private ActionListener forwardButton(final JPanel cards) {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(">");
		        CardLayout cl = (CardLayout)(cards.getLayout());
		        cl.next(cards);
			}
        };
		return al;
	}
	
	/**
	 * 
	 * @param cards
	 * @param db
	 * @param bookList
	 * @param grid
	 * @return
	 */
	private ActionListener initButton(final JPanel cards, final DbConnector db,
			final List<Book> bookList, final JGrid grid) {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Inicio");
                bookList.clear();
				bookList.addAll(db.getBooks());
				grid.repaint();
		        CardLayout cl = (CardLayout)(cards.getLayout());
		        cl.show(cards, "GRID");
			}
        };
		return al;
	}
	
	/**
	 * 
	 * @param cards
	 * @param db
	 * @param bookList
	 * @param grid
	 * @return
	 */
	private ActionListener purchasesButton(final JPanel cards, final DbConnector db,
			final List<Book> bookList, final JGrid grid) {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Mis libros");
                bookList.clear();
                bookList.addAll(db.getBooksBuy("650010@unizar.es")); // CAMBIAR
                //bookList.add(new Book("a","a","/book0.jpg","a",25,"a"));
                grid.repaint();
                CardLayout cl = (CardLayout)(cards.getLayout());
                cl.show(cards, "GRID");       
			}
        };
		return al;
	}
	
	/**
	 * 
	 * @param cards
	 * @param db
	 * @return
	 */
	private ActionListener optionsButton(final JPanel cards, final DbConnector db) {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Opciones");
				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.show(cards, "OPTIONS");
			}
        };
		return al;
	}
	
	/**
	 * 
	 * @param db
	 * @param grid
	 * @param bookList
	 * @return
	 */
	private DocumentListener searchField(final DbConnector db, final JGrid grid,
			final List<Book> bookList) {
		DocumentListener dl = new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				listen();
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				listen();
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				listen();
			}
			
			public void listen() {
				List<Book> books = null;
				switch(field.getSelected()) {
				case 0:
					books = db.getBookString(field.getText());
					break;
				case 1:
					books = db.getBookTitulo(field.getText());
					System.out.println(books.toString());
					break;
				case 2: 
					books = db.getBookAutor(field.getText());
					break;
				case 3:
					books = db.getBookEditorial(field.getText());
					break;
				case 4:
					books = db.getBooks(); // POR TERMINAR
					break;
				default:
					books = db.getBooks();
				}
                bookList.clear();
                bookList.addAll(books);
                grid.repaint();
			}
			
		};
		return dl;
	}
}
