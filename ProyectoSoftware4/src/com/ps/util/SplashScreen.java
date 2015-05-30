package com.ps.util;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

public class SplashScreen extends JWindow {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ImageIcon logo;
	private int duration;
	
	public SplashScreen(int duration){
		this.duration = duration;
		LoadImage();
		pack();
	    setLocationRelativeTo(null);
	    setVisible(true);
	    try {
	    	Thread.sleep(this.duration);	
	    } catch (InterruptedException e) {
			e.printStackTrace();
		}		
	    
	    dispose();    
	}
	
	private void LoadImage(){
			this.logo = new ImageIcon("assets/fondo.gif");
			JLabel logolabel = new JLabel(this.logo);
			this.getContentPane().add(logolabel, BorderLayout.CENTER);
	}
}
