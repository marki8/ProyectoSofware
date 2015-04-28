package com.ps.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import com.ps.common.Book;
import com.ps.db.DbConnector;
import com.ps.gui.jgrid.ImageUtilities;
import com.ps.mail.SendMailTLS;

import de.jgrid.JGrid;

public class PanelBuyBook extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PanelBuyBook(final Book book, final DbConnector db, final JGrid grid) {
		
		// Split Pane
		final JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
                panelIzquierdo(book, db), panelDerecho(book, db, grid) );
		pane.setOneTouchExpandable(true);
		pane.setDividerLocation((book.getAutor().length() + 7)*10);
		pane.setEnabled(false);
		pane.setDividerSize(0);
		pane.setPreferredSize(grid.getPreferredSize());
		this.add(pane);
		
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                System.out.println(grid.getWidth());
        		pane.setPreferredSize(new Dimension(grid.getWidth(), 600));
            }
        });
	}
	
	/**
	 * Panel izquierdo
	 * @param book
	 * @param db
	 * @return
	 */
	private JPanel panelIzquierdo(final Book book, final DbConnector db) {
		JPanel leftPanel = new JPanel();
		leftPanel.setMinimumSize(new Dimension(200, 50));
		leftPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		// Leemos la imagen
		BufferedImage img = null;
		try {
			img = ImageIO.read(getClass().getResource(book.getPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Escala proporcional al tamaño de la imagen
		Scale scale = scale(img.getWidth(), img.getHeight());
		BufferedImage scaled = ImageUtilities.getOptimalScalingImage(img, scale.w, scale.h);
		JLabel j = new JLabel();
		j.setIcon(new ImageIcon(scaled));
		leftPanel.add(j, BorderLayout.WEST);
		
		// Boton de comprar libro
		JButton b1 = new JButton(book.getPrecio() + " € Comprar libro");
		b1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String user = "650010@unizar.es";
				String titulo = book.getTitle();
				String autor= book.getAutor();
				int puntuacion=0;
				db.addBookBuy(user, titulo,autor,puntuacion);
				SendMailTLS mail=new SendMailTLS("user",titulo);
				mail.send();
			}
		});
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
		req = new JLabel("<html>Para ver este libro,<br> debes tener un dispositivo iOS <br>con iBooks 1.3.1"
				+ "(o posterior) y<br> iOS 4.3.3 (o posterior).</html>");
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
	 * @param book
	 * @param db
	 * @return
	 */
	private JPanel panelDerecho(final Book book, DbConnector db, final JGrid grid) {
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setMinimumSize(new Dimension(200, 50));
		
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
		final JPanel desc = new JPanel();
		desc.setLayout(new BoxLayout(desc, BoxLayout.Y_AXIS));
		JLabel d1 = new JLabel("Descripción del libro"); 
		d1.setFont(new Font("Arial", Font.BOLD, 16));
		desc.add(d1, LEFT_ALIGNMENT);
		desc.add(Box.createRigidArea(new Dimension(0, 20)));
		int width = (int) grid.getPreferredSize().getWidth() - (book.getAutor().length() + 7)*11;
		String description = String.format("<html><div WIDTH=%d>%s</div><html>", width, book.getDescripcion());
		JLabel d2 = new JLabel(description);
		d2.setFont(new Font("Arial", Font.PLAIN, 12));
		desc.add(d2, LEFT_ALIGNMENT);
		
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
        		desc.removeAll();
        		
        		JLabel d1 = new JLabel("Descripción del libro"); 
        		d1.setFont(new Font("Arial", Font.BOLD, 16));
        		desc.add(d1, LEFT_ALIGNMENT);
        		
        		desc.add(Box.createRigidArea(new Dimension(0, 20)));

            	int width = (int) grid.getPreferredSize().getWidth() - (book.getAutor().length() + 7)*11;
        		String description = String.format("<html><div WIDTH=%d>%s</div><html>", width, book.getDescripcion());
        		JLabel d2 = new JLabel(description);
        		d2.setFont(new Font("Arial", Font.PLAIN, 12));
        		desc.add(d2, LEFT_ALIGNMENT);
            }
        });

		// Panel de valoraciones
		JPanel val = new JPanel();
		
		// Pestañas de detalles y valoraciones
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Detalles", null, desc,"Descripcion");
		tabbedPane.addTab("Valoraciones", null, val, "Valoraciones");
		tabbedPane.setAlignmentX(LEFT_ALIGNMENT);
		rightPanel.add(tabbedPane);
		
		return rightPanel;
	}
	
	private Scale scale(int bookWidth, int bookHeight) {
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
		
		return new Scale(width, height);

	}
	
	public class Scale {
		public int w;
		public int h;
		
		public Scale(int width, int height) {
			this.w = width;
			this.h = height;
		}

	}
}