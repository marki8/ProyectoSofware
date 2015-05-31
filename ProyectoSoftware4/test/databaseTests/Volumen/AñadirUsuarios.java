package databaseTests.Volumen;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import com.ps.db.DbConnector;

public class AñadirUsuarios {
	public DbConnector db;
	
	//hasta usuario999999
	@Test
	public void AñadirUsuariosTest() throws Exception{
		db=new DbConnector("db_test");
		db.shutdown();
		db=new DbConnector("db_test");
		try {
			// Creacion de la segunda tabla, esta vez de usuarios.
						db.update("CREATE TABLE users "
								+ "(email VARCHAR(256), password VARCHAR(256), "
								+ "PRIMARY KEY (email))");
		
		} catch (SQLException ex1) {
		System.out.println("Error 1");
		}
		int i=0;
		while(true) {
			db.addUser("usuario"+i, "pass"+i);
			System.out.println(i);
			i++;
			
		}	
	}	
}
