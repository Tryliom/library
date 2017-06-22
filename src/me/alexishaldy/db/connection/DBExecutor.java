package me.alexishaldy.db.connection;

import java.util.Map;
import java.util.Vector;

import me.alexishaldy.db.pool.DBConnectionPool;
import me.alexishaldy.db.table.Column;
import me.alexishaldy.exception.DBException;

/**
 * This class is used in order to extract information from the database. Its methods simply
 * get a connection from the connection pool and then delegate to the DBConnection implementation
 * class.
 * @see DBConnection
 * @see DBConnectionAdapter
 * @author Ronald T. Fernandez
 * @version 1.0
 */
public class DBExecutor {
	/**
	 * Class constructor
	 */
	private DBExecutor() {
	}
	
	/**
	 * This method retrieves the list of database names, excluding the system databases
	 * @return			List of database names
	 * @exception 		DBException Throws an exception in case a problem with the DB has occurred
	 * @exception		Exception	A generic exception has occurred
	 */
	public static Vector<String> getDBs() throws DBException, Exception {
		// Get a connection from the pool
		DBConnectionPool connectionPool = DBConnectionPool.getInstance();
		DBConnection connection = connectionPool.popConnection();
		
		// Exclude all databases which are from the system
		Map<String, Boolean> systemDBs = connection.getSystemDBs();
		
		// Filter system databases from the list
		Vector<String> list = connection.getList("SHOW DATABASES");
		Vector<String> filteredList = new Vector<String>();
		for (String db : list) {
			if (systemDBs.get(db) == null || !systemDBs.get(db).booleanValue())
				filteredList.addElement(db);
		}
	
		// Put back the connection to the pool
		connectionPool.pushConnection(connection);
		
		return filteredList;
	}
	
	/**
	 * This method execute a sql insert, update and delete.
	 * @exception 		DBException Throws an exception in case a problem with the DB has occurred
	 * @exception		Exception	A generic exception has occurred
	 */
	public static boolean execQuery(String sql) throws DBException, Exception {
		// Get a connection from the pool
		DBConnectionPool connectionPool = DBConnectionPool.getInstance();
		DBConnection connection = connectionPool.popConnection();
		
		// Execute query
		Boolean b = connection.execQuery(sql);
		
	
		// Put back the connection to the pool
		connectionPool.pushConnection(connection);
		
		return b;
	}

	/**
	 * This method retrieves the list of tables which are in a specific database name
	 * @param 			db		Database name
	 * @return 			tables	Table names in the database
	 * @exception 		DBException Throws an exception in case a problem with the DB has occurred
	 * @exception		Exception	A generic exception has occurred
	 */
	public static Vector<String> getTables(final String db) throws DBException, Exception {
		// Get a connection from the pool
		DBConnectionPool connectionPool = DBConnectionPool.getInstance();
		DBConnection connection = connectionPool.popConnection();
		
	    // Get the list of tables
	    Vector<String> result = connection.getList("SHOW TABLES from " + db);
	    
		// Put back the connection to the pool
		connectionPool.pushConnection(connection);
		
		return result;
	}
	
	/**
	 * This method retrieves the list of items which are in a specific table name
	 * @param 			table		Table name
	 * @return 			item		Item in the table
	 * @exception 		DBException Throws an exception in case a problem with the DB has occurred
	 * @exception		Exception	A generic exception has occurred
	 */
	public static Vector<String> selectQuery(final String sql) throws DBException, Exception {
		// Get a connection from the pool
		DBConnectionPool connectionPool = DBConnectionPool.getInstance();
		DBConnection connection = connectionPool.popConnection();
		
	    // Get the list of tables
	    Vector<String> result = connection.selectQuery(sql);
	    
		// Put back the connection to the pool
		connectionPool.pushConnection(connection);
		
		return result;
	}

	/**
	 * This method retrieves the list of items which are in a specific table name
	 * @param 			table		Table name
	 * @return 			item		Item in the table
	 * @exception 		DBException Throws an exception in case a problem with the DB has occurred
	 * @exception		Exception	A generic exception has occurred
	 */
	public static Vector<String> selectAll(final String table) throws DBException, Exception {
		// Get a connection from the pool
		DBConnectionPool connectionPool = DBConnectionPool.getInstance();
		DBConnection connection = connectionPool.popConnection();
		
	    // Get the list of tables
	    Vector<String> result = connection.selectQuery("SELECT * FROM "+table);
	    
		// Put back the connection to the pool
		connectionPool.pushConnection(connection);
		
		return result;
	}
	
	/**
	 * This method retrieves the list of tables which are in a specific database name
	 * @param 			db		Database name
	 * @return 			tables	Table names in the database
	 * @exception 		DBException Throws an exception in case a problem with the DB has occurred
	 * @exception		Exception	A generic exception has occurred
	 */
	public static Vector<String> selectById(final String table, final String id) throws DBException, Exception {
		// Get a connection from the pool
		DBConnectionPool connectionPool = DBConnectionPool.getInstance();
		DBConnection connection = connectionPool.popConnection();
		
	    // Get the list of tables
	    Vector<String> result = connection.selectQuery("SELECT * FROM "+table+" WHERE id = "+id);
	    
		// Put back the connection to the pool
		connectionPool.pushConnection(connection);
		
		return result;
	}
	
	/**
	 * This method retrieves the content of a table from a database
	 * @param 			db		Database name
	 * @param 			tableName	Name of the table
	 * @return			Table content retrieved from the database
	 * @exception 		DBException Throws an exception in case a problem with the DB has occurred
	 * @exception		Exception	A generic exception has occurred
	 */
	public static Column getTableInfo(final String db, final String tableName) throws DBException, Exception {
		// Get a connection from the pool
		DBConnectionPool connectionPool = DBConnectionPool.getInstance();
		DBConnection connection = connectionPool.popConnection();
		
	    // Get the table contents
	    Column result = connection.getTable(tableName);
		
		// Put back the connection to the pool
		connectionPool.pushConnection(connection);
		
		return result;
		
	}
}

