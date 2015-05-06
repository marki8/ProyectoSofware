package com.ps.common;

import javax.swing.JFrame;
import javax.swing.JWindow;

import com.ps.db.DbConnector;
import com.ps.util.Login;
import com.ps.util.SplashScreen;

public class Main {
	/**
	 * @param args
	 * @throws SiguienteException
	 */

	private static DbConnector db = null;
	public static final int Ancho=840;
	public static final int Alto=700;

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

			
			JWindow ventana= new SplashScreen(1000);
			JFrame  Login= new JFrame();
			Login.setSize(Ancho, Alto);
			Login.setLocationRelativeTo(null);
			Login.add(new Login(db,Login));
			Login.setVisible(true);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
