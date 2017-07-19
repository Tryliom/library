package me.alexishaldy.exception;

/**
 * This class is used to indicate that some problem has occurred with the database connection or
 * transaction. It is equivalent to the java.sql.SQLException in MySQL. However, it has been created
 * in order to offer more flexibility when using databases (we might use a different database,
 * e.g. Oracle)
 * @author Ronald T. Fernandez
 * @version 1.0
 */
@SuppressWarnings("serial")
public class DBException extends Exception {
	/**
	 * Message that provoked the exception being thrown.
	 */
	private String _message = "";
	/**
	 * Error code related to the cause that provoked the exception being thrown.
	 */
	private int _errorCode = -1;

	/**
	 * Constructor of the class
	 * @param message	Message that reflects the problem that has occurred
	 */
	public DBException(final String message) {
		this._message = message;
	}

	/**
	 * Constructor of the class
	 * @param message	Message that reflects the problem that has occurred
	 * @param errorCode	Error code related to the cause that provoked the exception
	 */
	public DBException(final String message, final int errorCode) {
		this._message = message;
		this._errorCode = errorCode;
	}

	/**
	 * This method allows getting the error message related to the exception.
	 * @return A string indicating the error message
	 */
	public final String getMessage() { 
		return _message; 
	}
}
