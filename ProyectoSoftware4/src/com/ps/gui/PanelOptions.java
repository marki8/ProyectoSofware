package com.ps.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.ps.db.DbConnector;
import com.ps.gui.gfx.DropShadow;

public class PanelOptions extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	private final JTextField textFieldEmail;
	private final JPasswordField textFieldPass;
	private String user;
	private String pass;

	public PanelOptions(final DbConnector db,String user,String pass) {
		
		this.user=user;
		this.pass=pass;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Titulo
		JLabel title = new JLabel("Opciones", SwingUtilities.LEFT);
		title.setFont(new Font("Helvetica", Font.BOLD, 20));
		add(title);
		add(Box.createRigidArea(new Dimension(0, 20)));

		// Cambiar email y pass
		JLabel req = new JLabel("Cambiar de email y contrase�a");
		req.setFont(new Font("Helvetica", Font.BOLD, 14));
		add(req);
		add(Box.createRigidArea(new Dimension(0, 10)));

		
		// Opcion 1 - Cambiar correo electronico
		JPanel option1 = new JPanel();
		option1.setLayout(new BoxLayout(option1, BoxLayout.X_AXIS));
		option1.setAlignmentX(LEFT_ALIGNMENT);
		option1.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		JLabel labelEmail = new JLabel("         Email: ", SwingUtilities.LEFT);
	    textFieldEmail = new JTextField(15);
	    textFieldEmail.setMaximumSize(textFieldEmail.getPreferredSize());
	    option1.add(labelEmail);
	    option1.add(textFieldEmail);
	    
	    JButton bEmail = new JButton("Cambiar");
		bEmail.addActionListener(emailButton(db));
		bEmail.setFont(new Font("Arial", Font.BOLD, 12));
		option1.add(bEmail);
	    add(option1);

		add(Box.createRigidArea(new Dimension(0, 10)));

	    
		// Opcion 2 - Cambiar de contraseña
		JPanel option2 = new JPanel();
		option2.setLayout(new BoxLayout(option2, BoxLayout.X_AXIS));
		option2.setAlignmentX(LEFT_ALIGNMENT);
		option2.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

	    JLabel labelPass = new JLabel("Contrasena: ");
	    textFieldPass = new JPasswordField(15);
	    textFieldPass.setMaximumSize(textFieldPass.getPreferredSize());
	    option2.add(labelPass, Component.RIGHT_ALIGNMENT);
	    option2.add(textFieldPass);

	    JButton bPass = new JButton("Cambiar");
		bPass.addActionListener(passButton(db));
		bPass.setFont(new Font("Arial", Font.BOLD, 12));
		option2.add(bPass);
		add(option2);

	}
	
	private ActionListener emailButton(final DbConnector db) {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String emailViejo = user;
				String email = textFieldEmail.getText();;
				db.changeEmail(email, emailViejo);
			}
		};
		return al;
	}
	
	private ActionListener passButton(final DbConnector db) {
		ActionListener al = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String passViejo = pass;
				String pass = textFieldPass.getText();
				db.changePass(pass, passViejo);
			}
		};
		return al;
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
