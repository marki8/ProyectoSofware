package com.ps.gui.gfx;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;


public class JIconTextField extends JTextField {

	private String[] menu = { "Buscar en todo el cat�logo", "Por titulo",
			"Por autor", "Por editorial", "Por puntuaci�n" };

	private static final long serialVersionUID = 1L;
	private Icon icon;
	private Rectangle iconBounds;
	private JLabel lb;
	private int selected;

	public JIconTextField() {
		super();
		this.icon = null;
		lb = new JLabel(icon);
		addMouseListener(mouseListener());
		addMouseMotionListener(mouseListener());
		add(lb);
	}

	public void setIcon(Icon icon) {
		this.icon = icon;
		iconBounds = new Rectangle(0, 0, icon.getIconWidth() + 8,
				icon.getIconHeight() + 8);
	}

	public Icon getIcon() {
		return this.icon;
	}

	public void setSelected(int i) {
		this.selected = i;
	}

	public int getSelected() {
		return this.selected;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int textX = 2;

		if (this.icon != null) {
			int iconWidth = icon.getIconWidth();
			int iconHeight = icon.getIconHeight();
			int x = 5;
			textX = x + iconWidth + 2;
			int y = (this.getHeight() - iconHeight) / 2;
			icon.paintIcon(this, g, x, y);
		}

		setMargin(new Insets(2, textX, 2, 2));
	}

	private MouseAdapter mouseListener() {
		MouseAdapter ma = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (iconBounds.contains(e.getPoint())) {
					JPopupMenu popup = new JPopupMenu();
					JMenuItem m;
					for (int i = 0; i < menu.length; i++) {
						if (selected == i) {
							m = new JMenuItem(menu[i], new ImageIcon(getClass().getResource("/selected.png")));
							popup.add(m);
						} else {
							m = new JMenuItem(menu[i], null);
							popup.add(m);
						}
						m.setBounds(0, 0, 0, 0);
						m.addActionListener(menuListener());
					}
					popup.show(JIconTextField.this, 0, icon.getIconHeight());
				}
			}
			
			@Override
			public void mouseMoved(MouseEvent arg0) {
			    int mx = arg0.getX();
			    int my = arg0.getY();
			    if (mx > iconBounds.x && mx < iconBounds.x + iconBounds.width && my > iconBounds.y
			            && my < iconBounds.y + iconBounds.height) {
			    	//Icon icon = new ImageIcon("img/search_icon.png");
			    } else {
			    	//Icon icon = new ImageIcon("img/search_icon1.png");
			    }		
			}
		};
		return ma;
	}

	private ActionListener menuListener() {
		ActionListener ml = new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				for (int i = 0; i < menu.length; i++) {
					if (menu[i].equals(event.getActionCommand())) {
						selected = i;
						break;
					}
				}
			}
		};
		return ml;
	}
}