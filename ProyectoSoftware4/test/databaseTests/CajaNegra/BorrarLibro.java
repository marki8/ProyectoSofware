package databaseTests.CajaNegra;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import com.ps.db.DbConnector;

public class BorrarLibro {	
public DbConnector db;


	/*
	 * Prueba añadir titulo nulo
	 */
	@Test
	public void BorrarLibroTest() throws Exception{
		db=new DbConnector("db_test");
		db.addBook("titulo", "autor", "path", "editorial", 0.0,"descripcion","genero");
		boolean test=db.deleteBook("titulo", "autor", "path");
		assertTrue(test==true);
		
	}
	
	
	/*
	 * Prueba añadir titulo nulo
	 */
	@Test
	public void BorrarLibroTituloNulo() throws Exception{
		db=new DbConnector("db_test");
		db.addBook("titulo", "autor", "path", "editorial", 0.0,"descripcion","genero");
		boolean test=db.deleteBook(null, "autor", "path");
		assertTrue(test==false);
		
	}
	/*
	 * Prueba añadir autor nulo
	 */
	@Test
	public void BorrarLibroAutorNulo() throws Exception{
		db=new DbConnector("db_test");
		db.addBook("titulo", "autor", "path", "editorial", 0.0,"descripcion","genero");
		boolean test=db.deleteBook("titulo", null, "path");
		assertTrue(test==true);
	}
	/*
	 * Prueba añadir path nulo
	 */
	@Test
	public void BorrarLibroPathNulo() throws Exception{
		db=new DbConnector("db_test");
		try {
			// Creacion de la primera tabla, guardaremos los libros de la base
			// de datos.
			db.update("CREATE TABLE book "
					+ "(title VARCHAR(256), autor VARCHAR(256), path VARCHAR(256),"
					+ " editorial VARCHAR(256), precio NUMERIC(5, 2), "
					+ "descripcion VARCHAR(1000), genero VARCHAR(100),"
					+ "PRIMARY KEY (title, autor))");

			// Creacion de la segunda tabla, esta vez de usuarios.
			db.update("CREATE TABLE users "
					+ "(email VARCHAR(256), password VARCHAR(256), "
					+ "PRIMARY KEY (email))");

			// Creacio de la tercera tabla, en la cual disponemos los libros
			// comprados por cada usuario y la posible puntuacion de estos.
			db.update("CREATE TABLE posee "
					+ "(email VARCHAR(256), title VARCHAR(256), autor VARCHAR(256), "
					+ "puntuacion NUMERIC(5, 2), "
					+ "foreign key (email) references users(email), "
					+ "foreign key(title,autor) references book(title,autor)"
					+ " ON DELETE CASCADE)");
			// db.update(expression);;

		} catch (SQLException ex1) {
			System.out.println("Error 1");
		}
		
		
		
		
		
		db.addBook("titulo", "autor", "path", "editorial", 0.0,"descripcion","genero");
		boolean test=db.deleteBook("titulo", "autor", null);
		assertTrue(test==true);
	}
}