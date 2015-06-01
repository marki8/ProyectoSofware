package com.ps.common;

import java.awt.Toolkit;

import javax.swing.JFrame;
import com.ps.db.DbConnector;
import com.ps.gui.PanelLogin;
import com.ps.util.SplashScreen;

public class Main {


	private static DbConnector db = null;
	public static final int Ancho = 400;
	public static final int Alto = 170;

	/**
	 * 
	 * @param args
	 * @throws SiguienteException
	 */
	public static void main(String[] args) throws SiguienteException {
		try {
			//DbConnector.main();
			try {
				db = new DbConnector("db_file");
			} catch (Exception e) {
				System.exit(0);
			}

			try {
			    //UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
			    JFrame.setDefaultLookAndFeelDecorated(false);

			} catch (Exception e) {
			    e.printStackTrace();
			}
			new SplashScreen(1000);
			JFrame Login = new JFrame("Easy Books - Login");
			Login.setSize(Ancho, Alto);
			Login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Login.setLocationRelativeTo(null);
			Login.setResizable(false);
			Login.setIconImage(Toolkit.getDefaultToolkit().getImage(Login.getClass().getResource("/EB.png")));
			Login.add(new PanelLogin(db, Login));
			Login.setVisible(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
