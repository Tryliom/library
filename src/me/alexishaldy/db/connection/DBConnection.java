package me.alexishaldy.db.connection;

import java.util.Map;
import java.util.Vector;

import me.alexishaldy.db.table.Column;
import me.alexishaldy.exception.DBException;


/**
 * This class is used as an interface which defines the operations to be done in the database.
 * It is defined as part of the Adapter design pattern.
 * @author Ronald T. Fernandez
 * @version 1.0
 */
public abstract class DBConnection {

	/**
	 * Host where the database service is running. It has the format host:port
	 */
	protected String host;
	/**
	 * Port where the database service is running. It has the format host:port
	 */
	protected String port;
	/**
	 * User login used to get connected to the database.
	 */
	protected String user;
	/**
	 * User password used to authenticate the given user.
	 */
	protected String pass;
	
	/**
	 * Class constructor. The constructor is simply defined in order to initialize the attributes.
	 * Because the class is abstract, it will be never called directly except in the derived classes.
	 * @param host		Host where the database service is running
	 * @param port		Port where the database service is running
	 * @param user		User login in order to get connected to the database
	 * @param pass		User password used to authenticate the given user
	 */
	protected DBConnection(final String host, final String port, final String user, final String pass) {
		this.host = host;
		this.port = port;
		this.user = user;
		this.pass = pass;
	}
	
	/**
	 * This method allows getting the information contain in a table
	 * @param tableName	Name of the table whose information is being requested
	 * @return			Table content retrieved from the database
	 * @exception 		DBException Throws an exception in case a problem with the DB has occurred
	 */
	public abstract Column getTable(final String tableName) throws DBException;

	/**
	 * This method allows getting the information contained in a table or as a result of a query. The
	 * only constraint for this method is that the result can have one single column (otherwise, only
	 * the first column will be retrieved).
	 * @param query		Query used to get the table with a single column
	 * @return			Information retrieved after running the query
	 * @exception		DBException Throws an exception in case a problem with the DB has occurred
	 */
	public abstract Vector<String> getList(final String query) throws DBException;
	
	/**
	 * This method allows retrieving the database names that belong to the database manager.
	 * @return Return a map with the database names as the index and "true" as the value.
	 * @exception 		DBException Throws an exception in case a problem with the DB has occurred
	 */
	public abstract Map<String, Boolean> getSystemDBs() throws DBException;

	/**
	 * This method is used for do anything in sql request
	 * @param query			The request
	 * @return				Result of query
	 * @throws DBException	DBException Throws an exception in case a problem with the DB has occurred
	 */
	public abstract boolean execQuery(String query) throws DBException;
	
	/**
	 * This method is used for SELECT in sql request
	 * @param query			The select request
	 * @return				The result of query
	 * @throws DBException	DBException Throws an exception in case a problem with the DB has occurred
	 */
	public abstract Vector<String> selectQuery(String query) throws DBException;

	
}
