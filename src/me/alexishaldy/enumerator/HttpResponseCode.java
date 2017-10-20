package me.alexishaldy.enumerator;

/**
 * Used to sort response code
 * @author haldy
 *
 */
public enum HttpResponseCode {
	OK(200), 
	NOK(400);
	
	private int code;
	
	/**
	 * Class Constructor
	 * @param code	Http status value
	 */
	HttpResponseCode(int code) {
		this.code = code;
	}
	
	/**
	 * Get the associated status
	 * @return Http status
	 */
	public int getValue() {
		return this.code;
	}
}
