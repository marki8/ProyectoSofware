package databaseTests.CajaNegra;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ps.db.DbConnector;

public class ModificarLibro {
	public DbConnector db;
	
	/*
	 * Prueba añadir titulo nulo
	 */
	@Test
	public void ModTitulo() throws Exception{
		db=new DbConnector("db/test/dbTest");
		db.addBook("titulo", "autor", "path", "editorial", 0.0,"descripcion","genero");
		boolean test=db.addBook("Mod", "autor", "path", "editorial", 0.0,"descripcion","genero");
		assertTrue(test==true);
		
	}
	/*
	 * Prueba añadir autor nulo
	 */
	@Test
	public void ModAutor() throws Exception{
		db=new DbConnector("db/test/dbTest");
		db.addBook("titulo", "autor", "path", "editorial", 0.0,"descripcion","genero");
		boolean test=db.addBook("titulo", "Mod", "path", "editorial", 0.0,"descripcion","genero");
		assertTrue(test==true);
	}
	/*
	 * Prueba añadir path nulo
	 */
	@Test
	public void ModPath() throws Exception{
		db=new DbConnector("db/test/dbTest");
		db.addBook("titulo", "autor", "path", "editorial", 0.0,"descripcion","genero");
		boolean test=db.addBook("titulo", "autor", "Mod", "editorial", 0.0,"descripcion","genero");
		assertTrue(test==true);
	}
	/*
	 * Prueba añadir Editorial nulo
	 */
	@Test
	public void ModEditorial() throws Exception{
		db=new DbConnector("db/test/dbTest");
		db.addBook("titulo", "autor", "path", "editorial", 0.0,"descripcion","genero");
		boolean test=db.addBook("titulo", "autor", "path", "Mod", 0.0,"descripcion","genero");
		assertTrue(test==true);
	}
	
	/*
	 * Prueba añadir Precio nulo
	 */
	@Test
	public void ModPrecio() throws Exception{
		db=new DbConnector("db/test/dbTest");
		db.addBook("titulo", "autor", "path", "editorial", 0.0,"descripcion","genero");
		boolean test=db.addBook("titulo", "autor", "path", "editorial", 1.0,"descripcion","genero");
		assertTrue(test==true);
	}
	/*
	 * Prueba añadir descripcion nulo
	 */
	@Test
	public void ModDescripcion() throws Exception{
		db=new DbConnector("db/test/dbTest");
		db.addBook("titulo", "autor", "path", "editorial", 0.0,"descripcion","genero");
		boolean test=db.addBook("titulo", "autor", "path", "editorial", 0.0,"Mod","genero");
		assertTrue(test==true);
	}
	/*
	 * Prueba añadir Precio nulo
	 */
	@Test
	public void ModGenero() throws Exception{
		db=new DbConnector("db/test/dbTest");
		db.addBook("titulo", "autor", "path", "editorial", 0.0,"descripcion","genero");
		boolean test=db.addBook("titulo", "autor", "path", "editorial", 0.0,"descripcion","Mod");
		assertTrue(test==true);
	}

}
