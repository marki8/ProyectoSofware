package databaseTests.Volumen;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import com.ps.db.DbConnector;

public class ModificarLibros {
	public DbConnector db;

	public void testModUsuarios() throws Exception {
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
		
		
			for(int i=0;i<999999;i++) db.addBook("title"+i, "autor"+i, "path"+i, "editorial"+i, 0.0, "descripcion"+i, "genero"+i);
			boolean error=false;
			int j=0;
			while(!error || j<999999) error=db.updateBook("title"+j, "autormod"+j, "pathmod"+j, "editorialmod"+j, 2.0,
					"descripcionmod"+j, "generomod"+j, "title"+j, "autor"+j);
			assertTrue(error);
		}
}
