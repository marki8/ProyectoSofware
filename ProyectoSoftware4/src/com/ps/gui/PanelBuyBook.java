package com.ps.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

import com.ps.common.Book;
import com.ps.db.DbConnector;
import com.ps.gui.gfx.DropShadow;
import com.ps.gui.jgrid.ImageUtilities;
import com.ps.mail.SendMailTLS;
import com.ps.util.PuntuacionEstrellas;

import de.jgrid.JGrid;

/**
 * Clase correspondiente al panel que muestra los datos de los libros y permite
 * comprarlos
 * 
 * @author
 * 
 */
public class PanelBuyBook extends JPanel {

	private static final long serialVersionUID = 1L;
	private String user;
	private String description = "Para ver este libro, debes tener un dispositivo "
			+ "iOS con iBooks 1.3.1 (o posterior) y iOS 4.3.3 (o posterior).";
	private int pages;
	private double size;
	private Date date;

	/**
	 * Constructor
	 * 
	 * @param book
	 * @param db
	 * @param grid
	 */
	public PanelBuyBook(final JPanel cards, final Book book,
			final DbConnector db, final JGrid grid,String user) {

		this.user=user;
		// Leemos la imagen del libro
		BufferedImage img = null;
		try {
			img = ImageIO.read(getClass().getResource(book.getPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Escala proporcional al tamaÃ±o de la imagen
		Dimension scale = scale(img.getWidth(), img.getHeight());
		BufferedImage scaled = ImageUtilities.getOptimalScalingImage(img,
				scale.width, scale.height);
		// Coordenadas del divisor de los paneles
		int divider = (book.getAutor().length() + 7) * 10;
		if (divider < scaled.getWidth())
			divider = scaled.getWidth();
		// Split Pane para colocar los dos paneles izquiedo y derecho
		final JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				panelIzquierdo(cards, book, db, scaled), panelDerecho(book, db,
						grid));

		pane.setOneTouchExpandable(true);
		pane.setDividerLocation(divider);
		pane.setEnabled(false);
		pane.setDividerSize(0);
		pane.setPreferredSize(grid.getPreferredSize());
		add(pane);
		addComponentListener(windowResized(grid, pane));
	}

	/**
	 * Panel Izquierdo
	 * 
	 * @param book
	 * @param db
	 * @param scaled
	 * @return
	 */
	private JPanel panelIzquierdo(final JPanel cards, final Book book,
			final DbConnector db, BufferedImage scaled) {
		JPanel leftPanel = new JPanel();
		leftPanel.setMinimumSize(new Dimension(200, 400));
		leftPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		// Borde alrededor de la imagen
		Graphics2D g = scaled.createGraphics();
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, scaled.getWidth() - 1, scaled.getHeight() - 1);
		g.dispose();

		JLabel j = new JLabel();
		j.setIcon(new ImageIcon(scaled));
		leftPanel.add(j, BorderLayout.WEST);

		// Boton de comprar libro
		JButton b1 = new JButton(book.getPrecio() + " € Comprar libro");
		b1.addActionListener(buyButton(cards, book, db));
		b1.setFont(new Font("Arial", Font.BOLD, 14));
		leftPanel.add(b1);

		// Boton de obtener muestra
		JButton b2 = new JButton("Obtener muestra");
		b2.setFont(new Font("Arial", Font.BOLD, 14));
		b2.setPreferredSize(b1.getPreferredSize());
		leftPanel.add(b2);

		JPanel leftPanelsub = new JPanel();
		BoxLayout bl = new BoxLayout(leftPanelsub, BoxLayout.Y_AXIS);
		leftPanelsub.setLayout(bl);
		leftPanelsub.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 5));

		// Separacion
		JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
		leftPanelsub.add(separator);
		leftPanelsub.add(Box.createRigidArea(new Dimension(0, 20)));

		// Requisitos
		JLabel req = new JLabel("REQUISITOS");
		req.setFont(new Font("Helvetica", Font.BOLD, 14));
		leftPanelsub.add(req);
		leftPanelsub.add(Box.createRigidArea(new Dimension(0, 5)));
		String description = String.format(
				"<html><div WIDTH=%d>%s</div><html>",
				(book.getAutor().length() + 7) * 9, this.description);
		req = new JLabel(description);
		req.setFont(new Font("Helvetica", Font.PLAIN, 12));
		leftPanelsub.add(req);
		leftPanelsub.add(Box.createRigidArea(new Dimension(0, 20)));

		// Mas del autor
		req = new JLabel("MÁS DE " + book.getAutor().toUpperCase());
		req.setFont(new Font("Helvetica", Font.BOLD, 14));
		leftPanelsub.add(req);

		leftPanel.add(leftPanelsub);
		return leftPanel;
	}

	/**
	 * Panel derecho
	 * 
	 * @param book
	 * @param db
	 * @return
	 */
	private JPanel panelDerecho(final Book book, DbConnector db,
			final JGrid grid) {
		JPanel rightPanel = new JPanel();
		rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setMinimumSize(new Dimension(200, 400));

		// Titulo del libro
		JLabel title = new JLabel(book.getTitle(), SwingUtilities.LEFT);
		title.setFont(new Font("Arial", Font.BOLD, 20));
		title.setAlignmentX(LEFT_ALIGNMENT);
		rightPanel.add(title);

		// Autor
		JLabel autor = new JLabel(book.getAutor(), SwingUtilities.LEFT);
		autor.setFont(new Font("Arial", Font.PLAIN, 16));
		autor.setAlignmentX(LEFT_ALIGNMENT);
		rightPanel.add(autor);

		// Separacion
		rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// Panel de detalles
		Random rand = new Random();
		pages = rand.nextInt((1000 - 50) + 1) + 50;
		size = 1.0 + (200.0 - 1.0) * rand.nextDouble();
		date = new Date(631152000000L + (Math.abs(rand.nextLong()) % (70L * 36
				* 24 * 60 * 60 * 4000)));
		final JPanel desc = descriptionPanel(book, grid);
		addComponentListener(panelResized(desc, book, grid));

		// Panel de valoraciones
		JPanel val = new JPanel();
		//val.add(new PuntuacionEstrellas(25,50));

		// PestaÃ±as de detalles y valoraciones
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Detalles", null, desc, "Descripcion");
		tabbedPane.addTab("Valoraciones", null, val, "Valoraciones");
		tabbedPane.setAlignmentX(LEFT_ALIGNMENT);
		rightPanel.add(tabbedPane);

		return rightPanel;
	}

	/**
	 * 
	 * @param bookWidth
	 * @param bookHeight
	 * @return
	 */
	private Dimension scale(int bookWidth, int bookHeight) {
		int width = bookWidth;
		int height = bookHeight;

		float widthFactor = (float) 256 / (float) bookWidth;
		float heightFactor = (float) 256 / (float) bookHeight;

		if (widthFactor < heightFactor) {
			width = (int) ((float) bookWidth * widthFactor);
			height = (int) ((float) bookHeight * widthFactor);
		} else {
			width = (int) ((float) bookWidth * heightFactor);
			height = (int) ((float) bookHeight * heightFactor);
		}

		return new Dimension(width, height);

	}

	private ActionListener buyButton(final JPanel cards, final Book book,
			final DbConnector db) {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String titulo = book.getTitle();
				String autor = book.getAutor();
				int puntuacion = 0;
				db.addBookBuy(user, titulo, autor, puntuacion);
				JOptionPane j = new JOptionPane();
				j.showMessageDialog(
						PanelBuyBook.this,
						"Usted ha realizado la compra con Exito.\nRecibira un correo con la informacion de la compra",
						"Compra Realizada", JOptionPane.INFORMATION_MESSAGE);
				SendMailTLS mail = new SendMailTLS("user", titulo, autor);
				CardLayout cl = (CardLayout) (cards.getLayout());
				cl.show(cards, "GRID");
				mail.send(user);
			}
		};
		return al;
	}

	/**
	 * 
	 * @param grid
	 * @param pane
	 * @return
	 */
	private ComponentAdapter windowResized(final JGrid grid,
			final JSplitPane pane) {
		ComponentAdapter ca = new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				pane.setPreferredSize(new Dimension(grid.getWidth(), 600));
			}
		};
		return ca;
	}

	/**
	 * 
	 * @param desc
	 * @param book
	 * @param grid
	 * @return
	 */
	private ComponentAdapter panelResized(final JPanel desc, final Book book,
			final JGrid grid) {
		ComponentAdapter ca = new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				desc.removeAll();
				desc.add(descriptionPanel(book, grid));
			}
		};
		return ca;
	}

	/**
	 * 
	 * @param book
	 * @param grid
	 * @return
	 */
	private JPanel descriptionPanel(Book book, JGrid grid) {
		final JPanel desc = new JPanel();
		desc.setLayout(new BoxLayout(desc, BoxLayout.Y_AXIS));
		// Descripcion del libro
		desc.add(Box.createRigidArea(new Dimension(0, 20)));
		JLabel d1 = new JLabel("Descripción del libro");
		d1.setFont(new Font("Arial", Font.BOLD, 16));
		desc.add(d1, LEFT_ALIGNMENT);
		// SeparaciÃ³n
		desc.add(Box.createRigidArea(new Dimension(0, 10)));
		// Sinopsis
		int width = (int) grid.getPreferredSize().getWidth()
				- (book.getAutor().length() + 7) * 11;
		String description = String.format(
				"<html><div WIDTH=%d>%s</div><html>", width,
				book.getDescripcion());
		JLabel d2 = new JLabel(description);
		d2.setFont(new Font("Arial", Font.PLAIN, 12));
		desc.add(d2, LEFT_ALIGNMENT);
		// Informacion
		JLabel d3 = new JLabel("Información");
		desc.add(Box.createRigidArea(new Dimension(0, 30)));
		d3.setFont(new Font("Arial", Font.BOLD, 14));
		desc.add(d3, LEFT_ALIGNMENT);

		desc.add(Box.createRigidArea(new Dimension(0, 10)));

		JTable table = new JTable();
		SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
		table.setModel(new javax.swing.table.DefaultTableModel(new Object[][] {
				{ "Idioma", "Español", "Publicado", formatter.format(date) },
				{ "Genero", "Historica", "Paginas", pages },
				{ "Editorial", book.getEditorial(), "TamaÃ±o",
						String.format("%.1f", size) + " MB" }, }, new String[] {
				"Title 1", "Title 2", "Title 3", "Title 4" }));

		JScrollPane scrollPane = new JScrollPane(table);

		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setViewportBorder(null);
		scrollPane.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);
		table.setOpaque(false);
		table.setBackground(new Color(255, 255, 255, 0));
		table.setTableHeader(null);
		table.setEnabled(false);
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(62);
		table.getColumnModel().getColumn(0).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(1).setPreferredWidth(160);
		table.getColumnModel().getColumn(2).setPreferredWidth(100);
		table.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
		table.getColumnModel().getColumn(3).setPreferredWidth(160);
		desc.add(scrollPane, LEFT_ALIGNMENT);
		return desc;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();

		g2.setPaint(new GradientPaint(0, 0, new Color(200, 200, 200), 0, 200,
				new Color(241, 241, 241)));
		g2.fillRect(0, 0, getWidth(), getHeight());

		// Sombra debajo de la barra de herramientas
		BufferedImage bi = new BufferedImage(getWidth(), 1,
				BufferedImage.TYPE_INT_RGB);
		DropShadow ds = new DropShadow(bi);
		ds.paint(g2, -2, -3);
		g2.setColor(new Color(120, 120, 120));
		g2.drawLine(0, 0, getWidth(), 0);

		g2.dispose();
	}
}
