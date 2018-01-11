package me.alexishaldy.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * This class is used for done anything speed up
 * @author Alexis Haldy
 * 
 *
 */
public class Utils {
	
	public final static String SEP = System.getProperty("file.separator");
	public static final String DB_CONF = "config" + SEP + "db.properties";
	public static final String SWAGGER_FILE = "web" + SEP + "swagger.json";
	
	/**
	 * Get the DB connection properties
	 * @return DB connection properties
	 * @throws Exception Throws an exception in case a problem during parsing
	 * the file happened or the file does not exist.
	 */
	public static Properties getDBProperties(String path) throws Exception{
		return parseFile(path + DB_CONF);
	}
	
	/**
	 * This method is used to encrypt password in sha1
	 * @param password 	Password to hash.
	 * @return			Password hashed
	 */
	public static String encryptPassword(String password)
	{
	    String sha1 = "";
	    try {
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(password.getBytes("UTF-8"));
	        sha1 = byteToHex(crypt.digest());
	    } catch (Exception e) {
	    	System.out.println(e.getMessage());
	    }
	    return sha1;
	}
	
	/**
	 * This method is used to convert byte to hex code
	 * @param hash	Table of hash
	 * @return		Hex code
	 */
	public static String byteToHex(final byte[] hash)
	{
	    Formatter formatter = new Formatter();
	    for (byte b : hash)
	    {
	        formatter.format("%02x", b);
	    }
	    String result = formatter.toString();
	    formatter.close();
	    return result;
	}
	
	/**
	 * This method is used to get a hashmap with all case gave
	 * @param s	Case gave, ex: [0] "id", [1] "name"
	 * @return	Hashmap used for json
	 */
	public static HashMap<Integer, String> putInMap(String[] s) {
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		for (int i=0;i<s.length;i++) {
			hm.put(i, s[i]);
		}
		return hm;
	}
	
	/**
	 * Read and parses a properties file
	 * @param confName	Properties file name
	 * @return Return the parsed properties
	 * @throws Exception Throws an exception in case of any problem
	 */
	private static Properties parseFile(final String confName) throws Exception {
		BufferedReader br = null;
		InputStreamReader isr = null;
		FileInputStream fis = null;
		Properties properties = new Properties();
		Exception exception = null;

		try {
			fis = new FileInputStream(new File(confName));
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			String line;
			// Read and parse the file
			while ((line = br.readLine()) != null) {
				line = line.toString();
				// Skip comments or blank lines
				if (line.startsWith("#") || line.isEmpty())
					continue;
				Vector<String> tokens = new Vector<String>();
				StringTokenizer st = new StringTokenizer(line, ":");
				while (st.hasMoreTokens())
					tokens.addElement(st.nextToken().trim());
				if (tokens.size() < 2) {
					exception = new Exception("Invalid configuration file: it must contain lines of type \"key : value\"");
					break;
				}
				properties.put(tokens.elementAt(0), tokens.elementAt(1));
				tokens.clear();
			}
		} 
		// Capture exceptions
		catch (FileNotFoundException fnfe) {
			exception = new Exception("Configuration file does not exist: " + confName);
		} catch (IOException ioe) {
			exception = new Exception("Error reading configuration file: " + confName);
		}

		// Close the streams
		if (br != null)
			br.close();
		if (isr != null)
			isr.close();
		if (fis != null)
			fis.close();
		
		if (exception != null)
			throw exception;
		return properties;
	}
	/**
	 * Verify if the String s is a int or not
	 * @param s		String to verify
	 * @return		Is int or not
	 */
	public static boolean isInt(String s) {
		try {
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Try to convert String s to int, if this is not an int, the defaultValue is return
	 * @param s				String to convert
	 * @param defaultValue	Value return if String s is not an int
	 * @return				Int value
	 */
	public static Integer getInt(String s, int defaultValue) {
		try {
			return Integer.parseInt(s);
		} catch (Exception e) {
			return defaultValue;
		}
	}
}


















