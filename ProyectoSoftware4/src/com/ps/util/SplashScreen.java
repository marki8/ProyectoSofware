package com.ps.util;

import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;

public class SplashScreen extends JWindow {
	
	private ImageIcon logo;
	private int duration;
	
	public SplashScreen(int duration){
		this.duration=duration;
		LoadImage();
		//LoadIcon();
		pack();
	    setLocationRelativeTo(null);
	    setVisible(true);
	    try {Thread.sleep(this.duration);} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	    
	    dispose();    
	}
	
	private void LoadImage(){
			this.logo=new ImageIcon("assets/fondo.gif");
			JLabel logolabel= new JLabel(this.logo);
			this.getContentPane().add(logolabel,BorderLayout.CENTER);
	}
}
