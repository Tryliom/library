package me.alexishaldy.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Scanner;

public class Utils {
	
	public final static String SEP = System.getProperty("file.separator");
	public static final String DB_CONF = "conf" + SEP + "db.properties";
	public static final String REST_CONF = "conf" + SEP + "rest.properties";
	public static final String SWAGGER_FILE = "web" + SEP + "swagger.json";
	
	public static String readFile(String fileName) throws IOException {
		FileInputStream fis = new FileInputStream(new File(fileName));
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);
		String result = new String();
		String line;
		
		while ((line = br.readLine()) != null)
			result += line + "\n";
		br.close();
		isr.close();
		fis.close();
		
		return result;
	}
	
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
	
	public static HashMap<Integer, String> putInMap(String[] s) {
		HashMap<Integer, String> hm = new HashMap<Integer, String>();
		for (int i=0;i<s.length;i++) {
			hm.put(i, s[i]);
		}
		return hm;
	}
	
	public static String findInFile(String path, String arg) {
		try {
			Scanner sc;
			if (path.startsWith("http")) {
				sc = new Scanner(new URL(path).openStream(), "UTF-8");
			} else {
				sc = new Scanner(new File(path), "UTF-8");
			}
			while (sc.hasNext()) {
				String l = sc.nextLine();
				if (l.startsWith(arg)) {
					sc.close();
					return l;
				}
			}
			sc.close();
		} catch (Exception e) {}
		return "Not found !";
	}	
}


















