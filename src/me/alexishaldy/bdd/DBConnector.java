package me.alexishaldy.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
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
	private static Connection _connection;
	
	public static Connection getConnect() throws Exception {
		if (_connection==null) {
			new DBConnector();
		}
		return _connection;
	}
	
	private DBConnector() throws Exception {
		String host = "localhost";
		String port = "3307";
		String user = "root";
		String pass = "";
		try {
			// System.out.println("Creating connection " + CONN_PREFIX + host + ":" + port);
			_connection = DriverManager.getConnection(CONN_PREFIX + host + ":" + port + "/Library?verifyServerCertificate=false&useSSL=true", user, pass);
		} catch (SQLException e) {
			if (e.getErrorCode() == 2026) {
				System.err.println("Maximum number of connections exceeded (" + e.getMessage() + ")");
				throw new Exception("Max number of connections exceeded ");
			}
			throw new Exception(e.getMessage());
		}
	}
}
