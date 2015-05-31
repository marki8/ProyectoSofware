package databaseTests.CajaNegra;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import com.ps.db.DbConnector;

public class AñadirUsuario {

	public DbConnector db;
	
	/*
	 * Prueba añadir Usuario 
	 */
	@Test
	public void AñadirUsuarioTest() throws Exception{
		db=new DbConnector("db_test");
		try {
			// Creacion de la segunda tabla, esta vez de usuarios.
			db.update("CREATE TABLE users "
					+ "(email VARCHAR(256), password VARCHAR(256), "
					+ "PRIMARY KEY (email))");

		} catch (SQLException ex1) {
			System.out.println("Error 1");
		}
		
		
		
		boolean test=db.addUser("correo", "contraseña");
		assertTrue(test==true);
		
	}	
	
	
	/*
	 * Prueba añadir Usuario correo nulo
	 */
	@Test
	public void AñadirUsuarioCorreoNulo() throws Exception{
		db=new DbConnector("db/test/dbTest");
		try {
			// Creacion de la primera tabla, guardaremos los libros de la base
			// de datos.
			db.update("CREATE TABLE users "
					+ "(email VARCHAR(256), password VARCHAR(256), "
					+ "PRIMARY KEY (email))");

		} catch (SQLException ex1) {
			System.out.println("Error 1");
		}
		
		boolean test=db.addUser(null, "contraseña");
		assertTrue(test==false);
		
	}
	
	/*
	 * Prueba añadir Usuario contraseña nula
	 */	
	@Test
	public void AñadirUsuarioContrNula() throws Exception{
		db=new DbConnector("db/test/dbTest");
		try {
			db.update("CREATE TABLE users "
					+ "(email VARCHAR(256), password VARCHAR(256), "
					+ "PRIMARY KEY (email))");

		} catch (SQLException ex1) {
			System.out.println("Error 1");
		}
		boolean test=db.addUser("correo",null);
		assertTrue(test==false);
		
	}

}
