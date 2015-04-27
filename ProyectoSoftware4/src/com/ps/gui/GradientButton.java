package com.ps.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class GradientButton extends JButton {

	private static final long serialVersionUID = 1L;
	private Color startColor = new Color(90, 90, 90);
	private Color endColor = new Color(76, 76, 76);
	private Color pressedColor = new Color(206, 67, 0);
    private static final int VERTICAL =1;
    private int direction = VERTICAL;
	private int outerRoundRectSize = 10;
	private int innerRoundRectSize = 8;
	private GradientPaint GP;
	
	public GradientButton(String text) {
		super();
		setText(text);
		setContentAreaFilled(false);
		setBorderPainted(false);
		setFont(new Font("Thoma", Font.BOLD, 12));
		setForeground( UIManager.getColor ( "Panel.background" ));
		setFocusable(false);

	}

	public GradientButton(Color startColor, Color endColor,
			Color rollOverColor, Color pressedColor,int adirection) {
		super();
		this.startColor = startColor;
		this.endColor = endColor;
		this.pressedColor = pressedColor;
		direction =adirection;
		setForeground(Color.WHITE);
		setFocusable(false);
		setContentAreaFilled(false);
		setBorderPainted(false);

	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int h = getHeight();
		int w = getWidth();
		
		g2d.setPaint(new GradientPaint(0, 0, startColor, 0, h, endColor,true));
		GradientPaint p1;
		GradientPaint p2;
		if (getModel().isPressed()) {
						
			p1 = new GradientPaint(0, 0, new Color(0, 0, 0), 0, h - 1,new Color(100, 100, 100));
			p2 = new GradientPaint(0, 1, new Color(0, 0, 0, 50), 0, h - 3,new Color(255, 255, 255, 100));
		} else {
			GP = new GradientPaint(0, 0, startColor, 0, h, endColor, true);
			p1 = null;
			p2 = null;
		}
		RoundRectangle2D.Float r2d = new RoundRectangle2D.Float(0, 0, w - 1,
				h - 1, outerRoundRectSize, outerRoundRectSize);
		Shape clip = g2d.getClip();
		g2d.clip(r2d);
		g2d.fillRect(0, 0, w, h);
		g2d.setClip(clip);
		g2d.setPaint(p1);
		g2d.drawRoundRect(0, 0, w - 1, h - 1, outerRoundRectSize,
				outerRoundRectSize);
		g2d.setPaint(p2);
		g2d.drawRoundRect(1, 1, w - 3, h - 3, innerRoundRectSize,
				innerRoundRectSize);
		g2d.dispose();

		super.paintComponent(g);
	}

	public void setStartColor(Color color) {
		startColor = color;
	}

	public void setEndColor(Color pressedColor) {
		endColor = pressedColor;
	}

	public Color getStartColor() {
		return startColor;
	}

	public Color getEndColor() {
		return endColor;
	}

	public static void main(String args[]) {

		JFrame frame = new JFrame("Custom Panels Demo");
		frame.setLayout(new FlowLayout());
		GradientButton standardButton = new GradientButton("Standard Button");
		
		frame.add(standardButton.getButtonsPanel());
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBackground(Color.WHITE);
		frame.setSize(700, 85);
		frame.setVisible(true);
	}

	public int getDirection() {
		return direction;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}
	public JPanel getButtonsPanel(){
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		GradientButton standardButton = new GradientButton("Standard Button");
		standardButton.setPreferredSize(new Dimension(130, 28));
		standardButton.setDirection(GradientButton.VERTICAL);

		panel.add(standardButton);

		return panel;
	}
}
