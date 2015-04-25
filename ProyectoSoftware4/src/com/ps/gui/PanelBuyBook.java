package com.ps.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
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
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import com.ps.common.Book;
import com.ps.db.DbConnector;
import com.ps.gui.jgrid.ImageUtilities;

import de.jgrid.JGrid;

public class PanelBuyBook extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PanelBuyBook(final Book book, final DbConnector db, final JGrid grid) {
		
		Dimension minimumSize = new Dimension(200, 50);
		
		// Panel izquierdo
		JPanel leftPanel = new JPanel();
		leftPanel.setMinimumSize(minimumSize);
		leftPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
		JLabel j = new JLabel();
		BufferedImage img = null;
		try {
			img = ImageIO.read(getClass().getResource(book.getPath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Escala proporcional al tamaño de la imagen
		Scale scale = scale(img.getWidth(), img.getHeight());
		BufferedImage scaled = ImageUtilities.getOptimalScalingImage(img, scale.w, scale.h);
		
		j.setIcon(new ImageIcon(scaled));
		leftPanel.add(j, BorderLayout.WEST);
		
		JButton b1 = new JButton("10,99€ Comprar libro");
		b1.setFont(new Font("Arial", Font.BOLD, 14));
		leftPanel.add(b1);
		b1 = new JButton("Obtener muestra");
		b1.setFont(new Font("Arial", Font.BOLD, 14));
		leftPanel.add(b1);
		
        JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
        leftPanel.add(separator);
        leftPanel.add(Box.createHorizontalStrut(5));
		
		JLabel req = new JLabel("REQUISITOS");
		req.setFont(new Font("Helvetica", Font.BOLD, 14));
		leftPanel.add(req);
		req = new JLabel("<html>Para ver este libro,<br> debes tener un dispositivo iOS <br>con iBooks 1.3.1"
				+ "(o posterior) y<br> iOS 4.3.3 (o posterior).</html>");
		req.setFont(new Font("Helvetica", Font.PLAIN, 12));
		leftPanel.add(req);
        leftPanel.add(Box.createHorizontalStrut(20), BorderLayout.WEST);
		req = new JLabel("MÁS DE " + book.getAutor().toUpperCase());
		req.setFont(new Font("Helvetica", Font.BOLD, 14));
		leftPanel.add(req);
		
		// Panel Derecho
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		rightPanel.setMinimumSize(minimumSize);
		
		JPanel title_autor = new JPanel();
		title_autor.setLayout(new BoxLayout(title_autor, BoxLayout.Y_AXIS));
		//title_autor.setAlignmentY(LEFT_ALIGNMENT);

		JLabel title = new JLabel(book.getTitle());
		title.setFont(new Font("Arial", Font.BOLD, 20));
		title.setAlignmentX(Component.LEFT_ALIGNMENT);
		title_autor.add(title);

		JLabel autor = new JLabel(book.getAutor());
		autor.setFont(new Font("Arial", Font.PLAIN, 16));
		autor.setAlignmentX(Component.LEFT_ALIGNMENT);
		title_autor.add(autor);
		rightPanel.add(title_autor);
	
		rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// Pestañas de descripcion y valoraciones
		JTabbedPane tabbedPane = new JTabbedPane();
		JPanel desc = new JPanel();
		desc.setLayout(new BoxLayout(desc, BoxLayout.Y_AXIS));
		JLabel d = new JLabel("Descripción del libro"); 
		d.setFont(new Font("Arial", Font.BOLD, 16));
		desc.add(d, LEFT_ALIGNMENT);
		JPanel val = new JPanel();
		tabbedPane.addTab("Detalles", null, desc,"Descripcion");
		tabbedPane.addTab("Valoraciones", null, val, "Valoraciones");
		rightPanel.add(tabbedPane);

		// Split Pane
		final JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
                leftPanel, rightPanel );
		pane.setOneTouchExpandable(true);
		pane.setDividerLocation(150);
		pane.setPreferredSize(grid.getPreferredSize());
		this.add(pane);
		
        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                System.out.println(grid.getPreferredSize());
        		pane.setPreferredSize(new Dimension(grid.getWidth(), 600));
            }
        });
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
	
		//this.add(new JButton(book.getTitel()));
		//this.add(new JButton(book.getAutor()));
//		JLabel j= new JLabel();
//		j.setIcon(new ImageIcon(getClass().getResource(book.getPath()))); 
//		j.setBounds(10,10,100,20); //Posici�n del jlabel dentro del panel
//		this.add(j);
//		JLabel labelTitle = new JLabel("Titulo: "+book.getTitle()+"\n");
//        this.add(labelTitle);
//        JLabel labelAutor = new JLabel("Autor: "+book.getAutor());
//        this.add(labelAutor);
//        
//        
//        JButton buttonBuy = new JButton("Comprar");
//        
//        buttonBuy.addActionListener(new ActionListener() {
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				String user = "user";
//				String password = "pass";
//				int id = 25;
//	            db.addBookBuy(user, password, id);				
//			}
//        });
//        this.add(buttonBuy);
//        
//	}
}