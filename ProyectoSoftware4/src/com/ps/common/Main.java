package com.ps.common;

import javax.swing.JFrame;

import com.ps.db.DbConnector;
import com.ps.gui.PanelLogin;
import com.ps.util.SplashScreen;

public class Main {


	private static DbConnector db = null;
	public static final int Ancho = 420;
	public static final int Alto = 350;

	/**
	 * 
	 * @param args
	 * @throws SiguienteException
	 */
	public static void main(String[] args) throws SiguienteException {
		try {
			try {
				db = new DbConnector("db_file");
			} catch (Exception e) {
				e.printStackTrace();
			}

			new SplashScreen(1000);
			JFrame Login = new JFrame();
			Login.setSize(Ancho, Alto);
			Login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Login.setLocationRelativeTo(null);
			Login.add(new PanelLogin(db, Login));
			Login.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
