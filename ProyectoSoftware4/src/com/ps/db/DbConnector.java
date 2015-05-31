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
	public DbConnector(String db_file_name_prefix) throws Exception {

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
	 * @return 
	 */
	public synchronized boolean addUser(String email, String pass) {

		try {
			if(email==null) return false;
			else if(pass==null) return false;
			else{
			update("INSERT INTO users(email,password) VALUES('" + email + "','"
					+ pass + "')");
			return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Annadir libros a la base de datos.
	 */
	public synchronized boolean addBook(String title, String autor, String path,
			String editorial, double precio, String descripcion, String genero) {

		try {
			
			if(title==null){System.out.println("Titulo nulo"); return false;}
			else if(autor==null){System.out.println("Autor nulo"); return false;}
			else if(path==null){System.out.println("Ruta Imagen nula"); return false;}
			else if(editorial==null){System.out.println("Editorial nula"); return false;}
			else if(descripcion==null){System.out.println("Descripcion nula"); return false;}
			else if(genero==null){System.out.println("genero nulo"); return false;}
			else{
				update("INSERT INTO book(title, autor, path, editorial, precio, descripcion, genero) "
						+ "VALUES('"
						+ title + "','"
						+ autor	+ "','"
						+ path + "','"
						+ editorial + "','" 
						+ precio + "','" 
						+ descripcion + "','" 
						+ genero + "')");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
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
	 * Metodo para actualizar puntuacion de un libro
	 * 
	 * @param user
	 *            , @param password
	 */
	public synchronized void updatePuntuacion(String user, String titulo,
			String autor, int puntuacion) {
		Statement st = null;
		try {
			st = conn.createStatement();
			st.executeQuery("UPDATE posee Set puntuacion=" + puntuacion
					+ " Where email='" + user + "' and title='" + titulo
					+ "' and autor='" + autor + "';");
			st.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo para obtener la puntuacion media de un libro
	 * 
	 * @param user
	 *            , @param password
	 */
	public synchronized double getMedia(String titulo,
			String autor) {
		Statement st = null;
		ResultSet rs = null;
		double media=0;
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT AVG(puntuacion) FROM posee WHERE title='"+titulo+"' and autor='"+autor+"'");
			st.close();
			rs.next();
			media=rs.getDouble(1);
		} catch (SQLException e) {
			media=0;
		}
		return media;
	}

	/**
	 * Metodo de eliminacion de libros.
	 */
	public synchronized boolean deleteBook(String title, String autor, String path) {

		try {
			if(title==null) return false;
			else{
			update("DELETE FROM book WHERE title='" + title + "' AND autor='"
					+ autor + "'");
			return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
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
			rs = st.executeQuery("SELECT b.title, b.autor, b.path, b.editorial , "
					+ "b.precio, b.descripcion, b.genero "
					+ "FROM book b WHERE UPPER(b.autor) LIKE '%" + autor + "%'");
			st.close();

			bookList = new ArrayList<Book>();
			for (; rs.next();) {
				bookList.add(new Book(rs.getString(1), rs.getString(2), rs
						.getString(3), rs.getString(4), rs.getDouble(5), rs
						.getString(6), rs.getString(7)));
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
			rs = st.executeQuery("SELECT b.title, b.autor, b.path, b.editorial,"
					+ "b.precio, b.descripcion, b.genero"
					+ " FROM book b WHERE UPPER(b.title) LIKE '%"
					+ titulo
					+ "%'");
			st.close();

			bookList = new ArrayList<Book>();
			for (; rs.next();) {
				bookList.add(new Book(rs.getString(1), rs.getString(2), rs
						.getString(3), rs.getString(4), rs.getDouble(5), rs
						.getString(6), rs.getString(7)));
			}

			// ALTERNATIVA
			// st = conn.createStatement();
			// rs = st.executeQuery("SELECT * FROM book");
			// st.close();
			//
			// bookList = new ArrayList<Book>();
			// for (; rs.next();) {
			// if (rs.getString(1).toUpperCase().contains(titulo))
			// bookList.add(new Book(rs.getString(1), rs.getString(2), rs
			// .getString(3), rs.getString(4), rs.getInt(5), rs.getString(6)));
			// }

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
			rs = st.executeQuery("SELECT b.title,b.autor,b.path, b.editorial ,b.precio,b.descripcion, b.genero "
					+ "FROM book b WHERE UPPER(b.editorial) LIKE '%"
					+ editorial + "%'");
			st.close();

			bookList = new ArrayList<Book>();
			for (; rs.next();) {
				bookList.add(new Book(rs.getString(1), rs.getString(2), rs
						.getString(3), rs.getString(4), rs.getDouble(5), rs
						.getString(6), rs.getString(7)));
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
			rs = st.executeQuery(("SELECT b.title, b.autor, b.path, b.editorial, "
					+ "b.precio, b.descripcion, b.genero "
					+ "FROM book b WHERE UPPER(b.autor) LIKE '%"
					+ cadena
					+ "%' "
					+ "OR UPPER(b.title) LIKE '%"
					+ cadena
					+ "%' "
					+ "OR UPPER(editorial) LIKE '%" + cadena + "%'"));
			st.close();

			bookList = new ArrayList<Book>();
			for (; rs.next();) {
				bookList.add(new Book(rs.getString(1), rs.getString(2), rs
						.getString(3), rs.getString(4), rs.getDouble(5), rs
						.getString(6), rs.getString(7)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bookList;
	}
	
	/**
	 * 
	 */
	public synchronized List<Book> getBooksAdvance(String title, String autor,
			String editorial, String genero, double pmin, double pmax) {
		Statement st = null;
		ResultSet rs = null;
		List<Book> bookList = new ArrayList<Book>();
		String titleSearch = "";
		String autorSearch = "";
		String editorialSearch = "";
		String generoSearch = "";
		String scoreSearch = "";
		
		if (title != null) 
			titleSearch = " AND UPPER(b.title) LIKE '%" + title.toUpperCase() + "%'";
		if (autor != null) 
			autorSearch = " AND UPPER(b.autor) LIKE '%" + autor.toUpperCase() + "%'";
		if (editorial != null) 
			editorialSearch = " AND UPPER(b.editorial) LIKE '%" + editorial.toUpperCase() + "%'";
		if (genero != null) 
			generoSearch = " AND UPPER(b.genero) LIKE '%" + genero.toUpperCase() + "%'";
		//if (score != -1) scoreSearch = " AND b.score LIKE '%" + score + "%'";

		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT b.title, b.autor, b.path, b.editorial,"
					+ "b.precio, b.descripcion, b.genero "
					+ "FROM book b WHERE b.precio >= " + pmin
					+ "AND b.precio <= " + pmax
					+ titleSearch + autorSearch + editorialSearch
					+ generoSearch + scoreSearch);
			st.close();

			bookList = new ArrayList<Book>();
			for (; rs.next();) {
				bookList.add(new Book(rs.getString(1), rs.getString(2), rs
						.getString(3), rs.getString(4), rs.getDouble(5), rs
						.getString(6), rs.getString(7)));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bookList;
	}
	
	/**
	 * 
	 * @return
	 */
	public synchronized List<String> getGeneros() {
		Statement st = null;
		ResultSet rs = null;
		List<String> generos = null;

		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM book");
			st.close();

			generos = new ArrayList<String>();
			for (; rs.next();) {
				generos.add(rs.getString(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return generos;
	}

	/**
	 * Metodo para comprobar si el usuario [user] con password [pass] existe en la BD.
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
	
	
	/**
	 * Metodo para comprobar si el usuario [user] existe en la BD.
	 */
	public synchronized boolean userExist2(String user) {
		Statement st = null;
		ResultSet rs = null;
		boolean respuesta = false;

		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM users WHERE email='" + user + "'");
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
						.getString(3), rs.getString(4), rs.getDouble(5), rs
						.getString(6), rs.getString(7)));
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
			rs = st.executeQuery("SELECT b.title, b.autor, b.path, b.editorial,"
					+ " b.precio, b.descripcion, b.genero "
					+ "FROM book b, users u, posee p "
					+ "WHERE u.email=p.email AND b.title=p.title "
					+ "AND b.autor=p.autor AND p.email='" + user + "'");
			st.close();

			bookList = new ArrayList<Book>();
			for (; rs.next();) {
				bookList.add(new Book(rs.getString(1), rs.getString(2), rs
						.getString(3), rs.getString(4), rs.getDouble(5), rs
						.getString(6), rs.getString(7)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bookList;
	}

	public synchronized boolean changeEmail(String email, String emailViejo) {
		Statement st = null;
		try {
			if(email==null) return false;
			else if(emailViejo==null) return false;
			else{
			st = conn.createStatement();
			st.executeQuery("UPDATE users SET email = '" + email
					+ "' WHERE email = '" + emailViejo + "';");
			
			st.close();
			return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public synchronized boolean changePass(String user, String pass) {
		Statement st = null;
		try {
			if(user==null)return false;
			else if (pass==null) return false;
			else{
			st = conn.createStatement();
			st.executeQuery("UPDATE users SET password = '" + pass
					+ "' WHERE email = '" + user + "';");
			st.close();
			return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public synchronized String getPass(String user) {
		Statement st = null;
		ResultSet rs = null;
		String pass = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT password FROM users WHERE email='"
					+ user + "'");
			if (rs.next())
				pass = rs.getString(1);

			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pass;
	}

	public synchronized boolean userHaveBook(String user, String title,
			String autor) {
		Statement st = null;
		ResultSet rs = null;
		try {
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * FROM posee WHERE email='" + user
					+ "'" + "AND title='" + title + "'" + "AND autor='" + autor
					+ "'");
			if (rs.next())
				return true;
			st.close();
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public synchronized boolean updateBook(String title, String autor,
			String path, String editorial, double precio, String descripcion,	
			String genero, String oldtitle, String oldautor) {
		Statement st = null;
		try {
			if(title==null)return false;
			else if(autor==null) return false;
			else if(path==null) return false;
			else if(editorial==null) return false;
			else if(descripcion==null) return false;
			else if(genero==null) return false;
			else if(oldtitle==null)return false;
			else if(oldautor==null)return false;
			else{
			st = conn.createStatement();
			st.executeQuery("UPDATE book SET title = '" + title
					+ "', autor = '" + autor 
					+ "', path = '" + path
					+ "', editorial ='" + editorial 
					+ "', precio ='" + precio
					+ "', descripcion ='" + descripcion
					+ "', genero = '" + genero 
					+ "' WHERE title = '" + oldtitle + "' AND autor = '" + oldautor + "';");
			st.close();
			return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
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
	public static void main(String [] args) {

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

		try {
			
			// Insertamos libros
			db.addBook(
					"La espada del destino",
					"Andrzej Sapkowski",
					"img/book0.jpg",
					"2002 Bibliopolis",
					11.99,
					"La vida de un brujo cazador de monstruos no es f�cil. Tan pronto puede uno tener que meterse hasta el cuello en un estercolero para eliminar a la bestia carro�era que amenaza la ciudad, intentado no atrapar una infecci�n incurable, como se puede encontrar unido a la cacer�a de uno de los �ltimos dragones, en la que la cuesti�n no es si los cazadores conseguir�n matar a la pobre bestia, sino qu� pasar� cuando tengan que repartirse el bot�n. Magos, pr�ncipes, estarostas, voievodas, druidas, vexlings, dr�adas, juglares y criaturas de todo pelaje pueblan esta tierra, enzarzados en conflictos de supervivencia, codicia y amor, y entre ellos avanza, solitario, el brujo Geralt de Rivia.",
					"A");
			db.addBook(
					"Destiny of the sword",
					"Jeremy Twigg",
					"img/book1.jpg",
					"Legend paperbacks",
					15.99,
					"Wally Smith, having died on Earth, finds himself reincarnated as a swordsman in another world and entrusted by the presiding goddess with a mission that has no appeal for him at all. Can he bring together all the swordsmen to finally defeat the sorcerors and their terrible technology? Wally is not quite convinced he should, but goddesses can be very persuasive",
					"B");
			db.addBook(
					"Nathe the great and the Sticky Case",
					"Ugo Sanchez",
					"img/book2.jpg",
					"RANDOM HOUSE-Children Bks",
					10.99,
					"A stegosaurus stamp belonging to Nates friend Claude disappears, and the indomitable Nate the Great is called in on the case. At first, even Nate is stumped -- the stamp has just vanished without a trace! But with clues from the weather and his ever-faithful dog, Sludge, Nate is soon on his way to wrapping up his stickiest case yet.",
					"AAA");
			db.addBook(
					"The Iron Hell",
					"Jack London",
					"img/book3.jpg",
					"Macmillan Publishers",
					14.99,
					"The Iron Heel is interesting as an example of a dystopian novel which anticipates and influenced George Orwells Nineteen Eighty-Four. Jack Londons (1876-1916) socialist politics are explicitly on display here. Its description of the capitalist class forming an organised, totalitarian, violent oligarchy to crush the working-class forewarned in some detail the Fascist dictatorships of Europe. Given it was written in 1908, this prediction was some-what uncanny, as Trotsky noted while commenting on the book in the 30s.",
					"Fantasia");
			db.addBook(
					"The arrow of gold",
					"Joseph Conrad",
					"img/book4.jpg",
					"ReadHowYouWant",
					13.99,
					"An entrancing story of adventure and love, Joseph Conrads The Arrow of Gold (1919) is the tale of a sailor whose youth and passion are exploited by the Carlists. Having fallen for the charming Spanish beauty Dona Rita, the captain ensnares himself in the Carlist gun-smuggling expedition.",
					"Fuegooo");
			db.addBook(
					"Bold Pursuit",
					"Zabrina Faiere",
					"img/book5.jpg",
					"Warner Books",
					12.99,
					"Love left her at the chapel, so Livia began a breathless, daring chase after romance.A young man and woman are deserted at the altar by their fiancees, who have shockingly eloped together! Disguised as a young boy, braving adventures on the road and the ever increasing danger of losing her heart to the infuriating, fascinating Sir Justin Ware who had also been left at the altar. At what was to be a double wedding, Sir Justin Warre and Livia Pemberton are left at the altar when their partners elope . . . with each other . . . so the two set off in pursuit!",
					"A saber...");

			// Insertamos usuarios
			db.addUser("650010@unizar.es", "a");
			db.addUser("admin", "nimda");
			
			db.shutdown();

		} catch (SQLException ex3) {
			System.out.println("Error 2");
			ex3.printStackTrace();
		}
	}
}
