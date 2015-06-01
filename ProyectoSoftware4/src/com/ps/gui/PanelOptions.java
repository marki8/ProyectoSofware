package com.ps.gui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import com.ps.common.Book;
import com.ps.db.DbConnector;

import de.jgrid.JGrid;

public class PanelOptions extends Panel {
	
	private static final long serialVersionUID = 1L;
	
//	private final JTextField textFieldEmail;
//	private final JPasswordField textFieldPass;
	
	private final JTextField textFieldTitle;
	private final JTextField textFieldAutor;
	private final JTextField textFieldPublisher;
	private final JComboBox comboBoxGenero;
	private final JComboBox comboBoxScore;
	private JSlider sliderPreciomin;
	private JSlider sliderPreciomax;
	private final JTextField textFieldPrecioMin;
	private final JTextField textFieldPrecioMax;


	private JButton buttonSearch;
	private JTable tableSearch;
	private List<String> columns;
	private List<Book> books;
	
	private String user;

	public PanelOptions(final DbConnector db, String user, JPanel cards, JGrid grid) {
		
		this.user = user;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		setMinimumSize(getPreferredSize());


		// Titulo
		JLabel title = new JLabel("Búsqueda Avanzada", SwingUtilities.LEFT);
		title.setFont(new Font("Helvetica", Font.BOLD, 20));
		add(title);
		add(Box.createRigidArea(new Dimension(0, 20)));

		// Cambiar email y pass
//		JLabel req = new JLabel("Cambiar de email y contrase�a");
//		req.setFont(new Font("Helvetica", Font.BOLD, 14));
//		add(req);
//		add(Box.createRigidArea(new Dimension(0, 10)));

		
		// Opcion 1 - Cambiar correo electronico
//		JPanel option1 = new JPanel();
//		option1.setLayout(new BoxLayout(option1, BoxLayout.X_AXIS));
//		option1.setAlignmentX(LEFT_ALIGNMENT);
//		option1.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
//		JLabel labelEmail = new JLabel("         Email: ", SwingUtilities.LEFT);
//	    textFieldEmail = new JTextField(15);
//	    textFieldEmail.setMaximumSize(textFieldEmail.getPreferredSize());
//	    textFieldEmail.setText(user);
//	    option1.add(labelEmail);
//	    option1.add(textFieldEmail);
//	    
//	    JButton bEmail = new JButton("Cambiar");
//		bEmail.addActionListener(emailButton(db));
//		bEmail.setFont(new Font("Arial", Font.BOLD, 12));
//		option1.add(bEmail);
//	    add(option1);
//
//		add(Box.createRigidArea(new Dimension(0, 10)));
//
//	    
//		// Opcion 2 - Cambiar de contraseña
//		JPanel option2 = new JPanel();
//		option2.setLayout(new BoxLayout(option2, BoxLayout.X_AXIS));
//		option2.setAlignmentX(LEFT_ALIGNMENT);
//		option2.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
//
//	    JLabel labelPass = new JLabel("Contrasena: ");
//	    textFieldPass = new JPasswordField(15);
//	    textFieldPass.setMaximumSize(textFieldPass.getPreferredSize());
//	    textFieldPass.setText(db.getPass(user));
//	    option2.add(labelPass, Component.RIGHT_ALIGNMENT);
//	    option2.add(textFieldPass);
//
//	    JButton bPass = new JButton("Cambiar");
//		bPass.addActionListener(passButton(db));
//		bPass.setFont(new Font("Arial", Font.BOLD, 12));
//		option2.add(bPass);
//		add(option2);
		
		JPanel search = new JPanel();
		search.setLayout(new BoxLayout(search, BoxLayout.Y_AXIS));
		//search.setAlignmentX(LEFT_ALIGNMENT);
		search.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		//search.setMinimumSize(new Dimension(200, 400));
		
		JPanel searchLeft = new JPanel();
		searchLeft.setLayout(new BoxLayout(searchLeft, BoxLayout.Y_AXIS));
		searchLeft.setAlignmentX(LEFT_ALIGNMENT);
		searchLeft.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
		//searchLeft.setMinimumSize(new Dimension(200, 400));

		
		// Titulo
		JLabel labelTitle = new JLabel("Titulo                  ", SwingUtilities.LEFT);
		labelTitle.setFont(new Font("Arial", Font.BOLD, 14));
		labelTitle.setAlignmentX(LEFT_ALIGNMENT);
	    textFieldTitle = new JTextField(30);
	    textFieldTitle.setMaximumSize(textFieldTitle.getPreferredSize());
	    textFieldTitle.setAlignmentX(LEFT_ALIGNMENT);
	    searchLeft.add(labelTitle);
	    searchLeft.add(textFieldTitle);
	    searchLeft.add(Box.createRigidArea(new Dimension(0, 10)));
		
		// Autor
		JLabel labelAutor = new JLabel("Autor", SwingUtilities.LEFT);
		labelAutor.setFont(new Font("Arial", Font.BOLD, 14));
		labelAutor.setAlignmentX(LEFT_ALIGNMENT);
	    textFieldAutor = new JTextField(30);
	    textFieldAutor.setMaximumSize(textFieldTitle.getPreferredSize());
	    textFieldAutor.setAlignmentX(LEFT_ALIGNMENT);
	    searchLeft.add(labelAutor);
	    searchLeft.add(textFieldAutor);
	    searchLeft.add(Box.createRigidArea(new Dimension(0, 10)));
		
		// Editorial
		JLabel labelPublisher = new JLabel("Editorial", SwingUtilities.LEFT);
		labelPublisher.setFont(new Font("Arial", Font.BOLD, 14));
		labelPublisher.setAlignmentX(LEFT_ALIGNMENT);
	    textFieldPublisher = new JTextField(30);
	    textFieldPublisher.setMaximumSize(textFieldTitle.getPreferredSize());
	    textFieldPublisher.setAlignmentX(LEFT_ALIGNMENT);
	    searchLeft.add(labelPublisher);
	    searchLeft.add(textFieldPublisher);
	    searchLeft.add(Box.createRigidArea(new Dimension(0, 10)));
		
		// Genero
		JLabel labelGenero = new JLabel("Género", SwingUtilities.LEFT);
		labelGenero.setFont(new Font("Arial", Font.BOLD, 14));
		labelGenero.setAlignmentX(LEFT_ALIGNMENT);
		List<String> list = db.getGeneros();
		list.add(0, "Ninguno");
		String[] Strings = list.toArray(new String[list.size()]);
		comboBoxGenero = new JComboBox(Strings);
		comboBoxGenero.setMaximumSize(new Dimension(200, comboBoxGenero.getPreferredSize().height));
		comboBoxGenero.setAlignmentX(LEFT_ALIGNMENT);
		searchLeft.add(labelGenero);
		searchLeft.add(comboBoxGenero);
		searchLeft.add(Box.createRigidArea(new Dimension(0, 10)));
		
		JPanel searchRight = new JPanel();
		searchRight.setLayout(new BoxLayout(searchRight, BoxLayout.Y_AXIS));
		searchRight.setAlignmentX(LEFT_ALIGNMENT);
		searchRight.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		//searchRight.setMinimumSize(new Dimension(200, 400));
		
		// Precio minimo
		JLabel precioMin = new JLabel("Precio Mínimo", SwingUtilities.LEFT);
		precioMin.setFont(new Font("Arial", Font.BOLD, 14));
		precioMin.setAlignmentX(LEFT_ALIGNMENT);
		JPanel precioMinLabel = new JPanel();
		precioMinLabel.setLayout(new BoxLayout(precioMinLabel, BoxLayout.X_AXIS));
		precioMinLabel.setAlignmentX(LEFT_ALIGNMENT);
		sliderPreciomin = new JSlider(JSlider.HORIZONTAL, 0, 1000, 0);
		sliderPreciomin.setAlignmentX(LEFT_ALIGNMENT);
		textFieldPrecioMin = new JTextField(4);
		textFieldPrecioMin.setMaximumSize(new Dimension(2, textFieldPrecioMin.getPreferredSize().height));
		textFieldPrecioMin.setText("0");
		sliderPreciomin.addChangeListener(sliderChange(textFieldPrecioMin, sliderPreciomin));
		searchRight.add(precioMin);
		precioMinLabel.add(sliderPreciomin);
		precioMinLabel.add(Box.createRigidArea(new Dimension(10, 0)));
		precioMinLabel.add(textFieldPrecioMin);
		searchRight.add(precioMinLabel);
		searchRight.add(Box.createRigidArea(new Dimension(0, 10)));
		
		// Precio maximo
		JLabel precioMax = new JLabel("Precio Máximo", SwingUtilities.LEFT);
		precioMax.setFont(new Font("Arial", Font.BOLD, 14));
		precioMax.setAlignmentX(LEFT_ALIGNMENT);
		JPanel precioMaxLabel = new JPanel();
		precioMaxLabel.setLayout(new BoxLayout(precioMaxLabel, BoxLayout.X_AXIS));
		precioMaxLabel.setAlignmentX(LEFT_ALIGNMENT);
		sliderPreciomax = new JSlider(JSlider.HORIZONTAL, 0, 1000, 1000);
		sliderPreciomax.setAlignmentX(LEFT_ALIGNMENT);
		textFieldPrecioMax = new JTextField(4);
		textFieldPrecioMax.setText("1000");
		textFieldPrecioMax.setMaximumSize(new Dimension(2, textFieldPrecioMax.getPreferredSize().height));
		sliderPreciomax.addChangeListener(sliderChange(textFieldPrecioMax, sliderPreciomax));
		searchRight.add(precioMax);
		precioMaxLabel.add(sliderPreciomax);
		precioMaxLabel.add(Box.createRigidArea(new Dimension(10, 0)));
		precioMaxLabel.add(textFieldPrecioMax);
		searchRight.add(precioMaxLabel);
	    searchRight.add(Box.createRigidArea(new Dimension(0, 10)));
		
		// Puntuacion
		JLabel labelScore = new JLabel("Puntuación", SwingUtilities.LEFT);
		labelScore.setFont(new Font("Arial", Font.BOLD, 14));
		labelScore.setAlignmentX(LEFT_ALIGNMENT);
		String[] scores = { "No", "Mayor que 0", "Mayor que 1", "Mayor que 2", "Mayor que 3", "Mayor que 4"};
		comboBoxScore = new JComboBox(scores);
		comboBoxScore.setMaximumSize(new Dimension(200,comboBoxScore.getPreferredSize().height));
		comboBoxScore.setAlignmentX(LEFT_ALIGNMENT);
		searchRight.add(labelScore);
		searchRight.add(comboBoxScore);
	    searchRight.add(Box.createRigidArea(new Dimension(0, 10)));
		
		JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				searchLeft, searchRight);
		pane.setOneTouchExpandable(true);
		pane.setEnabled(false);
		pane.setDividerSize(0);
		pane.setAlignmentX(LEFT_ALIGNMENT);
		search.add(pane);
		
	    // Boton de buscar
	    buttonSearch = new JButton("Buscar");
	    buttonSearch.addActionListener(searchButton(db));
	    search.add(buttonSearch, CENTER_ALIGNMENT);
	    search.add(Box.createRigidArea(new Dimension(0, 10)));
	    
	    // Tabla
	    columns = new ArrayList<String>();
	    List<String[]> values = new ArrayList<String[]>();

        columns.add("Titulo");
        columns.add("Autor");
        columns.add("Editorial");
        columns.add("Género");
        columns.add("Puntuación");
        columns.add("Precio");

        TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][] {}), columns.toArray());
        tableSearch = new JTable(tableModel);
        tableSearch.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableSearch.setEnabled(false);
        tableSearch.addMouseListener(clicTable(db, cards, grid));
        search.add(new JScrollPane(tableSearch));
		
		add(search);


	}
	
//	private ActionListener emailButton(final DbConnector db) {
//		ActionListener al = new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				//String emailViejo = user;
//				//String email = textFieldEmail.getText();;
//				//db.changeEmail(email, emailViejo);
//			}
//		};
//		return al;
//	}
	
//	private ActionListener passButton(final DbConnector db) {
//		ActionListener al = new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				//String passViejo = pass;
//				//String pass = textFieldPass.getText();
//				//db.changePass(user, pass);
//			}
//		};
//		return al;
//	}
	
	private ActionListener searchButton(final DbConnector db) {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				DefaultTableModel dm = (DefaultTableModel)tableSearch.getModel();
				if (dm.getRowCount() > 0) {
				    for (int i = dm.getRowCount() - 1; i > -1; i--) {
				    	dm.removeRow(i);
				    }
				}
				
			    List<String[]> values = new ArrayList<String[]>();
			    
			    String title = null, autor = null, editorial = null, genero = null;
			    double pmin = 0, pmax = 1000, score = -1;
			    
			    if (textFieldTitle.getText().length() != 0) title = textFieldTitle.getText();
			    if (textFieldAutor.getText().length() != 0) autor = textFieldAutor.getText();
			    if (textFieldPublisher.getText().length() != 0) editorial = textFieldPublisher.getText();
			    if (!comboBoxGenero.getSelectedItem().toString().equals("Ninguno")) genero = comboBoxGenero.getSelectedItem().toString();
			    if (!comboBoxScore.getSelectedItem().toString().equals("No")) score = Integer.valueOf(comboBoxScore.getSelectedItem().toString().substring(10));
			    pmin = Double.valueOf(textFieldPrecioMin.getText());
			    pmax = Double.valueOf(textFieldPrecioMax.getText());

			    books = db.getBooksAdvance(title, autor, editorial, genero, pmin, pmax);

		        for (int i = 0; i < books.size(); i++) {
		        	double bookscore = db.getMedia(books.get(i).getTitle(), books.get(i).getAutor());
		        	if (bookscore > score)
		        		values.add(new String[] {books.get(i).getTitle(), 
		        				books.get(i).getAutor(),
		        				books.get(i).getEditorial(),
		        				books.get(i).getGenero(),
		        				String.valueOf(bookscore),
		        				String.valueOf(books.get(i).getPrecio())});
		        }
		        TableModel tableModel = new DefaultTableModel(values.toArray(new Object[][] {}), columns.toArray());
		        tableSearch.setModel(tableModel);
			}
		};
		return al;
	}
	
	private MouseAdapter clicTable(final DbConnector db, final JPanel cards,
			final JGrid grid) {
		MouseAdapter ma = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				int row = tableSearch.rowAtPoint(evt.getPoint());
				int col = tableSearch.columnAtPoint(evt.getPoint());
				if (row >= 0 && col >= 0) {
					for (int i = 0; i < columns.size(); i++) {
						System.out.println(tableSearch.getValueAt(row, i));
					}
					Book book = books.get(row);
					PanelToolBar.checkNavigation("BOOK" + book.getAutor() + book.getTitle());
					PanelToolBar.STACK.add("BOOK" + book.getAutor() + book.getTitle());
					PanelToolBar.INDEX++;
					cards.add(new PanelBuyBook(cards,book, db, grid, user),
							PanelToolBar.STACK.get(PanelToolBar.STACK.size()-1));
					CardLayout cl = (CardLayout) (cards.getLayout());
					cl.show(cards, PanelToolBar.STACK.get(PanelToolBar.STACK.size()-1));
				}
			}
		};
		return ma;
	}
	
	private ChangeListener sliderChange(final JTextField tf, final JSlider js) {
		ChangeListener cl = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent arg0) {
                tf.setText(String.valueOf(js.getValue()));
            }
        };
		return cl;
	}
}
