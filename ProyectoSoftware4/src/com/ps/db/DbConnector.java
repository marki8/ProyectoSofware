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
 * Title:        Testdb
 * Description:  simple hello world db example of a
 *               standalone persistent db application
 *
 *               every time it runs it adds four more rows to sample_table
 *               it does a query and prints the results to standard out
 *
 * Author: Karl Meissner karl@meissnersd.com
 */
public class DbConnector {

    Connection conn;                                                //our connnection to the db - presist for life of program

    // we dont want this garbage collected until we are done
    public DbConnector(String db_file_name_prefix) throws Exception {    // note more general exception

        // Load the HSQL Database Engine JDBC driver
        // hsqldb.jar should be in the class path or made part of the current jar
        Class.forName("org.hsqldb.jdbcDriver");

        // connect to the database.   This will load the db files and start the
        // database if it is not alread running.
        // db_file_name_prefix is used to open or create files that hold the state
        // of the db.
        // It can contain directory names relative to the
        // current working directory
        conn = DriverManager.getConnection("jdbc:hsqldb:file:db/"
                                           + db_file_name_prefix,    // filenames
                                           "sa",                     // username
                                           "");                      // password
    }

    public void shutdown() throws SQLException {

        Statement st = conn.createStatement();

        // db writes out to files and performs clean shuts down
        // otherwise there will be an unclean shutdown
        // when program ends
        st.execute("SHUTDOWN");
        conn.close();    // if there are no other open connection
    }

    public synchronized void query(String expression) throws SQLException {

        Statement st = null;
        ResultSet rs = null;

        st = conn.createStatement();         // statement objects can be reused with

        // repeated calls to execute but we
        // choose to make a new one each time
        rs = st.executeQuery(expression);    // run the query

        // do something with the result set.
        dump(rs);
        st.close();    // NOTE!! if you close a statement the associated ResultSet is

        // closed too
        // so you should copy the contents to some other object.
        // the result set is invalidated also  if you recycle an Statement
        // and try to execute some other query before the result set has been
        // completely examined.
    }
    
    public synchronized void addBook(String title, String autor, String path) {
    	try {
			update("INSERT INTO books(title,autor,path) VALUES('" + title + "','" + title + "','" + path + "')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    public synchronized List<Book> getBooks() {
        Statement st = null;
        ResultSet rs = null;
        List<Book> bookList = null;

        try {
			st = conn.createStatement();
	        rs = st.executeQuery("SELECT * FROM books"); 
	        st.close();

	        bookList = new ArrayList<Book>();
	        for (; rs.next(); ) {
	        	bookList.add(new Book(rs.getString(2), rs.getString(3), rs.getString(4)));
	        }
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        return bookList;
    }


//use for SQL commands CREATE, DROP, INSERT and UPDATE
    public synchronized void update(String expression) throws SQLException {

        Statement st = null;

        st = conn.createStatement();    // statements

        int i = st.executeUpdate(expression);    // run the query

        if (i == -1) {
            System.out.println("db error : " + expression);
        }

        st.close();
    }    // void update()

    public static void dump(ResultSet rs) throws SQLException {

        // the order of the rows in a cursor
        // are implementation dependent unless you use the SQL ORDER statement
        ResultSetMetaData meta   = rs.getMetaData();
        int               colmax = meta.getColumnCount();
        int               i;
        Object            o = null;

        // the result set is a cursor into the data.  You can only
        // point to one row at a time
        // assume we are pointing to BEFORE the first row
        // rs.next() points to next row and returns true
        // or false if there is no next row, which breaks the loop
        for (; rs.next(); ) {
            for (i = 0; i < colmax; ++i) {
                o = rs.getObject(i + 1);    // Is SQL the first column is indexed

                // with 1 not 0
                System.out.print(o.toString() + " ");
            }

            System.out.println(" ");
        }
    }                                       //void dump( ResultSet rs )

    public static void main(String[] args) {

        DbConnector db = null;

        try {
            db = new DbConnector("db_file");
        } catch (Exception ex1) {
            ex1.printStackTrace();    // could not start db

            return;                   // bye bye
        }

        try {

            //make an empty table
            //
            // by declaring the id column IDENTITY, the db will automatically
            // generate unique values for new rows- useful for row keys
            //db.update(
               // "CREATE TABLE sample_table ( id INTEGER IDENTITY, str_col VARCHAR(256), num_col INTEGER)");
            db.update(
                "CREATE TABLE books ( id INTEGER IDENTITY, title VARCHAR(256), autor VARCHAR(256), path VARCHAR(256))");
        } catch (SQLException ex2) {

            //ignore
            //ex2.printStackTrace();  // second time we run program
            //  should throw execption since table
            // already there
            //
            // this will have no effect on the db
        }

        try {

            // add some rows - will create duplicates if run more then once
            // the id column is automatically generated
//            db.update(
//                "INSERT INTO books(title,autor,path) VALUES('La espada del destino', 'Andrzej Sapkowski', '/book0.jpg')");
//            db.update(
//            	"INSERT INTO books(title,autor,path) VALUES('Destiny of the sword', 'Jeremy Twigg', '/book1.jpg')");
//            db.update(
//                "INSERT INTO books(title,autor,path) VALUES('Nathe the great and the Sticky Case', 'Ugo Sanchez', '/book2.jpg')");
//            db.update(
//                "INSERT INTO books(title,autor,path) VALUES('The Iron Hell', 'Jack London', '/book3.jpg')");
//            db.update(
//                "INSERT INTO books(title,autor,path) VALUES('The arrow of gold', 'Joseph Conrad', '/book4.jpg')");
//            db.update(
//                "INSERT INTO books(title,autor,path) VALUES('Bold Pursuit', 'Zabrina Faiere', '/book5.jpg')");


            // do a query
            //db.query(" DELETE FROM sample_table WHERE str_col='Ford'");
            db.query("SELECT * FROM books");

            // at end of program
            db.shutdown();
        } catch (SQLException ex3) {
            ex3.printStackTrace();
        }
    }    // main()
}    // class Testdb