package databaseTests.Volumen;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import com.ps.db.DbConnector;

public class ModificarUsuarios {
	public DbConnector db;
	@Test
	public void testModMailUsuarios() throws Exception {
		db=new DbConnector("db_test");
		try {
			// Creacion de la segunda tabla, esta vez de usuarios.
			db.update("CREATE TABLE users "
					+ "(email VARCHAR(256), password VARCHAR(256), "
					+ "PRIMARY KEY (email))");

		} catch (SQLException ex1) {
			System.out.println("Error 1");
		}	
		
		
		
			for(int i=0;i<999999;i++) db.addUser("email"+i,"pass"+i);
			boolean error=false;
			int j=0;
			while(!error || j<999999) error=db.changeEmail("emailMod"+j, "email"+j);
			db.shutdown();
			assertTrue(error);
		}
	
	
	
	public void testModConstUsuarios() throws Exception {
		db=new DbConnector("db_test");
		try {
			// Creacion de la segunda tabla, esta vez de usuarios.
			db.update("CREATE TABLE users "
					+ "(email VARCHAR(256), password VARCHAR(256), "
					+ "PRIMARY KEY (email))");

		} catch (SQLException ex1) {
			System.out.println("Error 1");
		}	
		
		
		
			for(int i=0;i<999999;i++) db.addUser("email"+i,"pass"+i);
			boolean error=false;
			int j=0;
			while(!error || j<999999) error=db.changePass("email"+j, "passmod"+j);
			db.shutdown();
			assertTrue(error);
		}
	

}
