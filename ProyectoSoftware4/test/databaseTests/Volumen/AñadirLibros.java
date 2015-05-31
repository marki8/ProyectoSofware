package databaseTests.Volumen;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import com.ps.db.DbConnector;

public class AñadirLibros {

	public DbConnector db;
	
	/*
	 * Prueba añadir Usuario 
	 */
	@Test
	public void AñadirLibroTest() throws Exception{
		db=new DbConnector("db_test");
		try {
			// Creacion de la primera tabla, guardaremos los libros de la base
			// de datos.
			db.update("CREATE TABLE book "
					+ "(title VARCHAR(256), autor VARCHAR(256), path VARCHAR(256),"
					+ " editorial VARCHAR(256), precio NUMERIC(5, 2), "
					+ "descripcion VARCHAR(1000), genero VARCHAR(100),"
					+ "PRIMARY KEY (title, autor))");
		} catch (SQLException ex1) {
			System.out.println("Error 1");
		}
		
		int i=0;
		while(true) {
			db.addBook("title"+i, "autor"+i, "path"+i, "edi"+i, 0.0, "desc"+i, "genero");
			System.out.println("libro añadido: "+i);
			i++;
		}	
	}	
}
