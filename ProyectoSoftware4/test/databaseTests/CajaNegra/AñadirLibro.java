package databaseTests.CajaNegra;

import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import com.ps.db.DbConnector;

public class AñadirLibro {
	public DbConnector db;
	
	
	/*
	 * Prueba añadir titulo nulo
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
		boolean test=db.addBook("titulo21", "autor2", "path", "editorial", 0.0,"descripcion","genero");
		assertTrue(test==true);
		db.shutdown();
		
	}
	
	
	/*
	 * Prueba añadir titulo nulo
	 */
	@Test
	public void AñadirTituloNulo() throws Exception{
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
		boolean test=db.addBook(null, "autor", "path", "editorial", 0.0,"descripcion","genero");
		assertTrue(test==false);
		db.shutdown();

		
	}
	/*
	 * Prueba añadir autor nulo
	 */
	@Test
	public void AñadirAutorNulo() throws Exception{
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
		boolean test=db.addBook("titulo",null, "path", "editorial", 0.0,"descripcion","genero");
		assertTrue(test==false);
		db.shutdown();

	}
	/*
	 * Prueba añadir path nulo
	 */
	@Test
	public void AñadirPathNulo() throws Exception{
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
		boolean test=db.addBook("autor", "autor", null, "editorial", 0.0,"descripcion","genero");
		assertTrue(test==false);
		db.shutdown();

	}
	/*
	 * Prueba añadir Editorial nulo
	 */
	@Test
	public void AñadirEditorialNulo() throws Exception{
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
		boolean test=db.addBook("titulo", "autor", "path", null, 0.0,"descripcion","genero");
		assertTrue(test==false);
		db.shutdown();

	}
	
	/*
	 * Prueba añadir Precio nulo
	 */
	@Test
	public void AñadirPrecioNulo() throws Exception{
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
		boolean test=db.addBook("titulo", "autor", "path", "editorial", 0,"descripcion","genero");
		assertTrue(test==false);
		db.shutdown();

	}
	/*
	 * Prueba añadir descripcion nulo
	 */
	@Test
	public void AñadirDescripcionNulo() throws Exception{
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
		boolean test=db.addBook("titulo", "autor", "path", "editorial", 0.0,null,"genero");
		assertTrue(test==false);
		db.shutdown();

	}
	/*
	 * Prueba añadir Precio nulo
	 */
	@Test
	public void AñadirGeneroNulo() throws Exception{
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
		boolean test=db.addBook("titulo", "autor", "path", "editorial", 0.0,"descripcion",null);
		assertTrue(test==false);
		db.shutdown();

	}

}
