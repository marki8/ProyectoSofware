package com.ps.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ps.common.Book;

/**
 * Title: Testdb Description: simple hello world db example of a standalone
 * persistent db application
 * 
 * every time it runs it adds four more rows to sample_table it does a query and
 * prints the results to standard out
 * 
 * Author: Karl Meissner karl@meissnersd.com
 */
public class DbConnector {

	Connection conn; // our connnection to the db - presist for life of program

	// we dont want this garbage collected until we are done
	public DbConnector(String db_file_name_prefix) throws Exception { // note
																		// more
																		// general
																		// exception

		// Load the HSQL Database Engine JDBC driver
		// hsqldb.jar should be in the class path or made part of the current
		// jar
		Class.forName("org.hsqldb.jdbcDriver");

		// connect to the database. This will load the db files and start the
		// database if it is not alread running.
		// db_file_name_prefix is used to open or create files that hold the
		// state
		// of the db.
		// It can contain directory names relative to the
		// current working directory
		conn = DriverManager.getConnection("jdbc:hsqldb:file:db/"
				+ db_file_name_prefix, // filenames
				"sa", // username
				""); // password
	}

	public void shutdown() throws SQLException {

		Statement st = conn.createStatement();

		// db writes out to files and performs clean shuts down
		// otherwise there will be an unclean shutdown
		// when program ends
		st.execute("SHUTDOWN");
		conn.close(); // if there are no other open connection
	}

	public synchronized void query(String expression) throws SQLException {

		Statement st = null;
		ResultSet rs = null;

		st = conn.createStatement(); // statement objects can be reused with

		// repeated calls to execute but we
		// choose to make a new one each time
		rs = st.executeQuery(expression); // run the query

		// do something with the result set.
		dump(rs);
		st.close(); // NOTE!! if you close a statement the associated ResultSet
					// is

		// closed too
		// so you should copy the contents to some other object.
		// the result set is invalidated also if you recycle an Statement
		// and try to execute some other query before the result set has been
		// completely examined.
	}

	/**
	 * Annadir un usario a la base de datos.
	 */
	public synchronized void addUser(String email, String pass) {

		try {
			update("INSERT INTO users(email,password) VALUES('"
					+ email + "','" + pass + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Annadir libros a la base de datos.
	 */
	public synchronized void addBook(String title, String autor, String path,
			String editorial) {

		try {
			update("INSERT INTO book(title,autor,path,editorial) VALUES('"
					+ title + "','" + autor + "','" + path + "','" + editorial
					+ "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo para annadir usuarios
	 * 
	 * @param user
	 *            , @param password
	 */
	public synchronized void addBookBuy(String user, String titulo,
			String autor, int puntuacion) {

		try {
			update("INSERT INTO posee(email ,title , autor , puntuacion) VALUES('"
					+ user
					+ "','"
					+ titulo
					+ "','"
					+ autor
					+ "','"
					+ puntuacion + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo de eliminacion de libros.
	 */
	public synchronized void deleteBook(String title, String autor, String path) {

		try {
			update("DELETE FROM book WHERE title='" + title + "' AND autor='"
					+ autor + "' AND path='" + path + "'");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// CONSULTAS DE BUSQUEDA DE LIBROS.

	/**
	 * Busqueda de libros por autor @param autor en la base de datos, en caso de
	 * no encontrar ninguno, la busqueda devolvera un libro con todos los
	 * parametros a -1.
	 */
	public synchronized List<Book> getBookAutor(String autor) {
		Statement st = null;
		ResultSet rs = null;
		List<Book> bookList = new ArrayList<Book>();
		autor = autor.toUpperCase();

		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT b.title,b.autor,b.path, b.editorial ,b.precio,b.descripcion "
					+ "FROM book b WHERE UPPER(b.autor) LIKE '%" + autor + "%'");
			st.close();

			bookList = new ArrayList<Book>();
			for (; rs.next();) {
				bookList.add(new Book(rs.getString(1), rs.getString(2), rs
						.getString(3), rs.getString(4), rs.getInt(5), rs
						.getString(6)));
			}
			

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bookList;
	}

	/**
	 * Busqueda de libros por titulo en la base de datos, en caso de no
	 * encontrar ninguno, la busqueda devolvera un libro con todos los
	 * parametros a -1.
	 */
	public synchronized List<Book> getBookTitulo(String titulo) {
		Statement st = null;
		ResultSet rs = null;
		List<Book> bookList = new ArrayList<Book>();
		titulo = titulo.toUpperCase();

		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT b.title,b.autor,b.path, b.editorial ,b.precio,b.descripcion"
					+ " FROM book b WHERE UPPER(b.title) LIKE '%" + titulo + "%'");
			st.close();

			bookList = new ArrayList<Book>();
			for (; rs.next();) {
				bookList.add(new Book(rs.getString(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getInt(5), rs.getString(6)));
				}
			
			
			// ALTERNATIVA
//			st = conn.createStatement();
//			rs = st.executeQuery("SELECT * FROM book");
//			st.close();
//			
//			bookList = new ArrayList<Book>();
//			for (; rs.next();) {
//				if (rs.getString(1).toUpperCase().contains(titulo))
//					bookList.add(new Book(rs.getString(1), rs.getString(2), rs
//						.getString(3), rs.getString(4), rs.getInt(5), rs.getString(6)));
//			}
			
			

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bookList;
	}

	/**
	 * Busqueda de libros por editorial en la base de datos, en caso de no
	 * encontrar ninguno, la busqueda devolvera un libro con todos los
	 * parametros a -1.
	 */
	public synchronized List<Book> getBookEditorial(String editorial) {
		Statement st = null;
		ResultSet rs = null;
		List<Book> bookList = new ArrayList<Book>();
		editorial = editorial.toUpperCase();

		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT b.title,b.autor,b.path, b.editorial ,b.precio,b.descripcion "
					+ "FROM book b WHERE UPPER(b.editorial) LIKE '%" + editorial + "%'");
			st.close();

			bookList = new ArrayList<Book>();
			for (; rs.next();) {
				bookList.add(new Book(rs.getString(1), rs.getString(2), rs
						.getString(3), rs.getString(4), rs.getInt(5), rs
						.getString(6)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bookList;
	}

	/**
	 * Busqueda de libros por mediante una cadena de caracteres, si esta cadena
	 * esta en algun de los siguientes parametros de libros en la base de datos
	 * devolvera el libro. En caso contrario devolvera un libro con parametros a
	 * -1
	 */
	public synchronized List<Book> getBookString(String cadena) {
		Statement st = null;
		ResultSet rs = null;
		cadena = cadena.toUpperCase();
		List<Book> bookList = new ArrayList<Book>();

		try {
			st = conn.createStatement();
			rs = st.executeQuery(("SELECT b.title, b.autor, b.path, b.editorial, b.precio, b.descripcion "
					+ "FROM book b WHERE UPPER(b.autor) LIKE '%" + cadena + "%' "
					+ "OR UPPER(b.title) LIKE '%" + cadena + "%' "
					+ "OR UPPER(editorial) LIKE '%" + cadena + "%'"));
			st.close();

			bookList = new ArrayList<Book>();
			for (; rs.next();) {
				bookList.add(new Book(rs.getString(1), rs.getString(2), rs
						.getString(3), rs.getString(4), rs.getInt(5), rs
						.getString(6)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bookList;
	}

	/**
	 * Metodo para comprobar si el usuario existe en la BD.
	 */
	public synchronized boolean userExist(String user, String pass) {
		Statement st = null;
		ResultSet rs = null;
		boolean respuesta = false;

		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM users WHERE email='" + user
					+ "' AND password='" + pass + "'");
			st.close();
			rs.next();
			if (rs.getString(1).isEmpty()) {
				respuesta = false;
			} else {
				respuesta = true;
			}
		} catch (SQLException e) {
		}
		return respuesta;
	}

	public synchronized List<Book> getBooks() {
		Statement st = null;
		ResultSet rs = null;
		List<Book> bookList = null;

		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM book");
			st.close();

			bookList = new ArrayList<Book>();
			for (; rs.next();) {
				bookList.add(new Book(rs.getString(1), rs.getString(2), rs
						.getString(3), rs.getString(4), rs.getInt(5), rs
						.getString(6)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bookList;
	}

	public synchronized List<Book> getBooksBuy(String user) {
		Statement st = null;
		ResultSet rs = null;
		List<Book> bookList = null;

		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT b.title,b.autor,b.path, b.editorial ,b.precio,b.descripcion FROM book b,users u,posee p WHERE u.email=p.email AND b.title=p.title AND b.autor=p.autor AND p.email='"+user+"'");
			st.close();

			bookList = new ArrayList<Book>();
			for (; rs.next();) {
				bookList.add(new Book(rs.getString(1), rs.getString(2), rs
						.getString(3), rs.getString(4), rs.getInt(5), rs
						.getString(6)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bookList;
	}

	public synchronized void changeEmail(String email, String emailViejo) {
		Statement st = null;
		try {
			st = conn.createStatement();
			st.executeQuery("UPDATE users SET email = '" + email
					+ "' WHERE email = '" + emailViejo + "';");
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void changePass(String pass, String passViejo) {
		Statement st = null;
		try {
			st = conn.createStatement();
			st.executeQuery("UPDATE users SET password = '" + pass
					+ "' WHERE password = '" + passViejo + "';");
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// use for SQL commands CREATE, DROP, INSERT and UPDATE
	public synchronized void update(String expression) throws SQLException {

		Statement st = null;

		st = conn.createStatement(); // statements

		int i = st.executeUpdate(expression); // run the query

		if (i == -1) {
			System.out.println("db error : " + expression);
		}

		st.close();
	} // void update()

	public static void dump(ResultSet rs) throws SQLException {

		// the order of the rows in a cursor
		// are implementation dependent unless you use the SQL ORDER statement
		ResultSetMetaData meta = rs.getMetaData();
		int colmax = meta.getColumnCount();
		int i;
		Object o = null;

		// the result set is a cursor into the data. You can only
		// point to one row at a time
		// assume we are pointing to BEFORE the first row
		// rs.next() points to next row and returns true
		// or false if there is no next row, which breaks the loop
		for (; rs.next();) {
			for (i = 0; i < colmax; ++i) {
				o = rs.getObject(i + 1); // Is SQL the first column is indexed

				// with 1 not 0
				System.out.print(o.toString() + " ");
			}

			System.out.println(" ");
		}
	} // void dump( ResultSet rs )

	/**
	 * Metodo main, creacion de las bases de datos de libros.
	 */
	public static void main(String[] args) {

		DbConnector db = null;

		try {
			db = new DbConnector("db_file"); // Archivo de las bases de datos.
		} catch (Exception ex1) {
			ex1.printStackTrace(); // could not start db

			return; // bye bye
		}
		try {
			// Creacion de la primera tabla, guardaremos los libros de la base
			// de datos.
			db.update("CREATE TABLE book (title VARCHAR(256), autor VARCHAR(256), path VARCHAR(256), editorial VARCHAR(256),precio NUMERIC(5, 2),descripcion VARCHAR(500), PRIMARY KEY (title, autor))");
		} catch (SQLException ex2) {
			System.out.println("Error 1");
		}

		try {
			// Creacion de la segunda tabla, esta vez de usuarios.
			db.update("CREATE TABLE users (email VARCHAR(256), password VARCHAR(256), PRIMARY KEY (email))");
		} catch (SQLException ex3) {
			System.out.println("Error 2");
		}

		try {
			// Creacio de la tercera tabla, en la cual disponemos los libros
			// comprados por cada usuario y la posible puntuacion de estos.
			db.update("CREATE TABLE posee (email VARCHAR(256), title VARCHAR(256), autor VARCHAR(256), puntuacion NUMERIC(5, 2), foreign key (email) references users(email),foreign key(title,autor) references book(title,autor))");
		} catch (SQLException ex3) {
			System.out.println("Error 3");

		}

		try {

			// Insertamos libros
			 db.update("INSERT INTO book(title,autor,path,editorial,precio,descripcion) VALUES('La espada del destino', 'Andrzej Sapkowski', '/book0.jpg', 'mevaisacomerlapolla',(11.99),'VIVA')");
			 db.update("INSERT INTO book(title,autor,path,editorial,precio,descripcion) VALUES('Destiny of the sword', 'Jeremy Twigg', '/book1.jpg', 'mevaisacomerlapolla',(71.99),'VIVA')");
			 db.update("INSERT INTO book(title,autor,path,editorial,precio,descripcion) VALUES('Nathe the great and the Sticky Case', 'Ugo Sanchez', '/book2.jpg', 'mevaisacomerlapolla',(51.99),'VIVA')");
			 db.update("INSERT INTO book(title,autor,path,editorial,precio,descripcion) VALUES('The Iron Hell', 'Jack London', '/book3.jpg', 'mevaisacomerlapolla',(14.99),'VIVA')");
			 db.update("INSERT INTO book(title,autor,path,editorial,precio,descripcion) VALUES('The arrow of gold', 'Joseph Conrad', '/book4.jpg', 'mevaisacomerlapolla',(13.99),'VIVA')");
			 db.update("INSERT INTO book(title,autor,path,editorial,precio,descripcion) VALUES('Bold Pursuit', 'Zabrina Faiere', '/book5.jpg', 'mevaisacomerlapolla',(12.99),'VIVA')");

			// Insertamos usuarios
			 db.update("INSERT INTO users(email,password) VALUES('650010@unizar.es', 'a')");
			 db.update("INSERT INTO users(email,password) VALUES('admin', 'nimda')");

			// Probatinas probatinas
			// db.query(" DELETE FROM sample_table WHERE str_col='Ford'");
			// db.query("SELECT * FROM book");
			// at end of program
			db.shutdown();
		} catch (SQLException ex3) {
			System.out.println("Error 4");
			ex3.printStackTrace();

		}
	}

}