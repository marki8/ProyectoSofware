package com.ps.common;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import com.ps.gui.*;

public class Main {
	/**
	 * @param args
	 * @throws SiguienteException 
	 */
	public static void main(String[] args) throws SiguienteException {
       try {
    	     String usuario = (String)JOptionPane.showInputDialog(null,
    	    			"Usuario:","Login",JOptionPane.PLAIN_MESSAGE);
    	     JPasswordField jpf = new JPasswordField();
    	     JLabel titulo = new JLabel ("Contrasena:");
    	     JOptionPane.showConfirmDialog (null, new Object[]{titulo, jpf}, "contrasena", JOptionPane.PLAIN_MESSAGE);
    	     char p[] = jpf.getPassword();
    	     String pass = new String(p);

    	     new MainGUI().setVisible(true);;
       }
      catch (Exception e) { 
    	  e.printStackTrace(); 
      }
    }
        
}
