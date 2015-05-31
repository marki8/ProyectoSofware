package databaseTests.CajaNegra;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import com.ps.db.DbConnector;

public class ModificarUsuario {

	public DbConnector db;
	/*
	 * Prueba añadir Usuario 
	 */
	
	
	@Test
	public void ModificarEmailTest() throws Exception{
		db=new DbConnector("db_test");
		try {
			// Creacion de la segunda tabla, esta vez de usuarios.
			db.update("CREATE TABLE users "
					+ "(email VARCHAR(256), password VARCHAR(256), "
					+ "PRIMARY KEY (email))");

		} catch (SQLException ex1) {
			System.out.println("Error 1");
		}	
		
		db.addUser("correo", "contraseña");
		boolean test=db.changeEmail("correoMod", "correo");
		assertTrue(test==true);
		
	}	
	
	@Test
	public void ModificarEmailNuloTest() throws Exception{
		db=new DbConnector("db_test");
		try {
			// Creacion de la segunda tabla, esta vez de usuarios.
			db.update("CREATE TABLE users "
					+ "(email VARCHAR(256), password VARCHAR(256), "
					+ "PRIMARY KEY (email))");

		} catch (SQLException ex1) {
			System.out.println("Error 1");
		}	
		
		db.addUser("correo", "contraseña");
		boolean test=db.changeEmail(null, "correo");
		assertTrue(test==false);
		
	}	
	@Test
	public void ModificarconstTest() throws Exception{
		db=new DbConnector("db_test");
		try {
			// Creacion de la segunda tabla, esta vez de usuarios.
			db.update("CREATE TABLE users "
					+ "(email VARCHAR(256), password VARCHAR(256), "
					+ "PRIMARY KEY (email))");

		} catch (SQLException ex1) {
			System.out.println("Error 1");
		}	
		
		db.addUser("correo", "contraseña");
		boolean test=db.changePass("correo", "modpass");
		assertTrue(test==true);
		
	}	
	
	@Test
	public void ModificarContrNuloTest() throws Exception{
		db=new DbConnector("db_test");
		try {
			// Creacion de la segunda tabla, esta vez de usuarios.
			db.update("CREATE TABLE users "
					+ "(email VARCHAR(256), password VARCHAR(256), "
					+ "PRIMARY KEY (email))");

		} catch (SQLException ex1) {
			System.out.println("Error 1");
		}	
		
		db.addUser("correo", "contraseña");
		boolean test=db.changePass("correo", null);
		assertTrue(test==false);
		
	}	

}
