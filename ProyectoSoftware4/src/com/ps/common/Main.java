package com.ps.common;

//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPasswordField;

import java.util.Date;

import com.ps.db.DbConnector;
import com.ps.gui.*;

public class Main {
	/**
	 * @param args
	 * @throws SiguienteException
	 */

	private static DbConnector db = null;

	public static void main(String[] args) throws SiguienteException {
		try {
			try {
				db = new DbConnector("db_file");
			} catch (Exception e) {
				e.printStackTrace();
			}
			// String usuario = (String)JOptionPane.showInputDialog(null,
			// "Usuario:","Login",JOptionPane.PLAIN_MESSAGE);
			// JPasswordField jpf = new JPasswordField();
			// JLabel titulo = new JLabel ("Contrasena:");
			// JOptionPane.showConfirmDialog (null, new Object[]{titulo, jpf},
			// "contrasena", JOptionPane.PLAIN_MESSAGE);
			// char p[] = jpf.getPassword();
			// String pass = new String(p);

			String user = "admin";
			String pass = "nimda";
			
			if (db.userExist(user, pass)) {
				if (user.equals("admin")) {
					new MainGUI(1).setVisible(true);
					;
				} else {
					new MainGUI(0).setVisible(true);
					;
				}
			} else {
				System.out.println("MAAAL");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
