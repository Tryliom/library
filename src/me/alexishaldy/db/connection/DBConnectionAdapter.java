package me.alexishaldy.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import me.alexishaldy.db.table.Column;
import me.alexishaldy.exception.DBException;


/**
 * This class implements the methods defined in the DBConnection interface.
 * These methods are implemented for working with a MySQL database.
 * The class is part of the Adapter design pattern.
 * @see DBConnection
 * @author Ronald T. Fernandez
 * @version 1.0
 */
public class DBConnectionAdapter extends DBConnection {

	/**
	 * Driver name
	 */
	public static final String DRIVER_NAME = "com.mysql.jdbc.Driver";
	/**
	 * Connection prefix (used only to connect to the MySQL database)
	 */
	public static final String CONN_PREFIX = "jdbc:mysql://";
	
	/**
	 * Initialize the driver
	 */
	static {
		try {
			Class.forName(DRIVER_NAME);
		} catch (ClassNotFoundException eiie) {
			throw new ExceptionInInitializerError("[ERROR] Driver class not found (" + DRIVER_NAME + "): " + eiie.getMessage());
		}
	}
	
	/**
	 * MySQL database connection
	 */
	private Connection _connection;
	
	/**
	 * Class constructor
	 * @param host		Host where the database service is running
	 * @param port		Port where the database service is running
	 * @param user		User login used to get connected to the database
	 * @param pass		Password used to authenticate the user
	 * @exception DBException	Throws an exception in case a problem with the DB has occurred
	 */
	public DBConnectionAdapter(String host, String port, String user, String pass) throws DBException {
		super(host, port, user, pass);
		try {
			System.out.println("Creating connection " + CONN_PREFIX + host + ":" + port);
			_connection = DriverManager.getConnection(CONN_PREFIX + host + ":" + port + "/library?verifyServerCertificate=false&useSSL=true&autoReconnect=true&maxReconnects=1", user, pass);
		} catch (SQLException e) {
			if (e.getErrorCode() == 2026) {
				System.err.println("Maximum number of connections exceeded (" + e.getMessage() + ")");
				throw new DBException("Max number of connections exceeded ", e.getErrorCode());
			}
			throw new DBException(e.getMessage());
		}
	}

	/**
	 * This method allows getting the information contained in a table or as a result of a query. The
	 * only constraint for this method is that the result can have one single column (otherwise, only
	 * the first column will be retrieved).
	 * The method derives from the base class <code>DBConnection</code>
	 * @see DBConnection
	 * @param query		Query used to get the table with a single column
	 * @return result	Information retrieved after running the query
	 * @exception DBException Throws an exception in case a problem with the DB has occurred
	 */
	@Override
	public Vector<String> getList(String query) throws DBException {
		
		Statement statement;
		ResultSet resultSet;
		
		try {
			// If the database has been defined, we set it
//			if (!db.isEmpty())
//				_connection.setSchema(db);
			
			// Create the statement and run the query
			statement = _connection.createStatement();
			resultSet = statement.executeQuery(query);
			Vector<String> result = new Vector<String>();
			
			// Retrieve the results
			while (resultSet.next()) {
				// Get the unique column (ofsset starts in 1 - not in 0 as Java)
				result.addElement(resultSet.getString(1));
			}
			
			return result;
		} catch (SQLException sqle) {
			throw new DBException(sqle.getMessage(), sqle.getErrorCode());
		}
	}
	
	@Override
	public boolean execQuery(String query) throws DBException {
		
		Statement statement;
		int rs;
		
		try {
			// Create the statement and run the query
			statement = _connection.createStatement();
			rs = statement.executeUpdate(query);
			return rs==1 ? true : false;
		} catch (SQLException sqle) {
			throw new DBException(sqle.getMessage(), sqle.getErrorCode());
		}
	}
	
	@Override
	public Vector<String> selectQuery(String query) throws DBException {
		
		Statement statement;
		ResultSet resultSet;
		int rs;
		
		try {
			// If the database has been defined, we set it

			
			statement = _connection.createStatement();
			resultSet = statement.executeQuery(query);
			Vector<String> result = new Vector<String>();
			while (resultSet.next()) {
				int i = 0;
				while (true) {
					i++;
					try {
						result.add(resultSet.getString(i));
					} catch (Exception e) {
						break;
					}
				}
			}
			
			return result;
		} catch (SQLException sqle) {
			throw new DBException(sqle.getMessage(), sqle.getErrorCode());
		}
	}

	/**
	 * This method allows retrieving the information contained in a table from the database
	 * The method derives from the base class <code>DBConnection</code>
	 * @see DBConnection
	 * @param db		Name of the database
	 * @param tableName	Name of the table whose information is going to be retrieved
	 * return			Table content retrieved from the database
	 */
	@Override
	public Column getTable(String tableName) throws DBException {
		Statement statement;
		ResultSet resultSet;
		
		try {
			statement = _connection.createStatement();
			resultSet = statement.executeQuery("DESC " + tableName);
			Column output = null;
			Column neighbor = null;
			
			int numColumns = 0;
			while (resultSet.next()) {
				if (numColumns == 0) {
					output = new Column(resultSet.getString(1));
					neighbor = output;
				} else {
					Column next = new Column(resultSet.getString(1));
					neighbor.setNeighbor(next);
					neighbor = next;
				}
				++numColumns;
			}
			
			// Then, we fill the output structure with the table contents
			statement = _connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM " + tableName);
			while (resultSet.next()) {
				neighbor = output;
				for (int i = 1; i <= numColumns; ++i) {
					neighbor.addValue(resultSet.getString(i));
					neighbor = neighbor.getNeighbor();
				}
			}
			
			return output;
		} catch (SQLException sqle) {
			System.err.println(sqle.getMessage());
			throw new DBException(sqle.getMessage(), sqle.getErrorCode());
		}
	}
	
	/**
	 * This method allows retrieving the database names that belong to the database manager.
	 * The method derives from the base class <code>DBConnection</code>
	 * @see DBConnection
	 * @return A map with the database names as the index and "true" as the value.
	 * @exception DBException Throws an exception in case a problem with the DB has occurred
	 */
	@Override
	public Map<String, Boolean> getSystemDBs() throws DBException {
		HashMap<String, Boolean> systemDBs = new HashMap<String, Boolean>();
		systemDBs.put("information_schema", Boolean.TRUE);
		systemDBs.put("mysql", Boolean.TRUE);
		systemDBs.put("performance_schema", Boolean.TRUE);
		systemDBs.put("sys", Boolean.TRUE);
		
		return systemDBs;
	}

}
