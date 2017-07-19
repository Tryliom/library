package me.alexishaldy.db.connection;

import java.util.Vector;

import me.alexishaldy.db.pool.DBConnectionPool;
import me.alexishaldy.exception.DBException;

/**
 * This class is used in order to extract information from the database. Its methods simply
 * get a connection from the connection pool and then delegate to the DBConnection implementation
 * class.
 * @see DBConnection
 * @see DBConnectionAdapterTest
 * @author Ronald T. Fernandez
 * @version 1.0
 */
public class DBExecutor {
	
	/**
	 * This method execute a sql insert, update and delete.
	 * @exception 		DBException Throws an exception in case a problem with the DB has occurred
	 * @exception		Exception	A generic exception has occurred
	 */
	public static boolean execQuery(String sql) throws DBException, Exception {
		// Get a connection from the pool
		DBConnectionPool connectionPool = DBConnectionPool.getInstance();
		DBConnection connection = connectionPool.popConnection();
		
		Boolean b = false;
		try {
			// Execute query
			b = connection.execQuery(sql);
		} catch (DBException dbe) {
			// Put back the connection to the pool
			connectionPool.pushConnection(connection);
			throw dbe;
		}
		// Put back the connection to the pool
		connectionPool.pushConnection(connection);
		
		return b;
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
	    Vector<String> result = null;
	    try {
	    	result = connection.selectQuery(sql);
		} catch (DBException dbe) {
			// Put back the connection to the pool
			connectionPool.pushConnection(connection);
			throw dbe;
		}
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
	    Vector<String> result = null;
	    try {
	    	result = connection.selectQuery("SELECT * FROM "+table);
		} catch (DBException dbe) {
			// Put back the connection to the pool
			connectionPool.pushConnection(connection);
			throw dbe;
		}
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
	    Vector<String> result = null;
	    try {
	    	result = connection.selectQuery("SELECT * FROM "+table+" WHERE id = "+id);
		} catch (DBException dbe) {
			// Put back the connection to the pool
			connectionPool.pushConnection(connection);
			throw dbe;
		}
	    
		// Put back the connection to the pool
		connectionPool.pushConnection(connection);
		
		return result;
	}
	

}

