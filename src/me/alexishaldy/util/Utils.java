package me.alexishaldy.util;

import java.security.MessageDigest;
import java.util.Formatter;
import java.util.HashMap;

/**
 * This class is used for done anything speed up
 * @author Alexis Haldy
 * 
 *
 */
public class Utils {
	
	public final static String SEP = System.getProperty("file.separator");
	public static final String DB_CONF = "conf" + SEP + "db.properties";
	public static final String REST_CONF = "conf" + SEP + "rest.properties";
	public static final String SWAGGER_FILE = "web" + SEP + "swagger.json";
	public static HashMap<String, String> listAuthors = new HashMap<String, String>();
	public static Thread currThread = null;
	
//	public static String readFile(String fileName) throws IOException {
//		FileInputStream fis = new FileInputStream(new File(fileName));
//		InputStreamReader isr = new InputStreamReader(fis);
//		BufferedReader br = new BufferedReader(isr);
//		String result = new String();
//		String line;
//		
//		while ((line = br.readLine()) != null)
//			result += line + "\n";
//		br.close();
//		isr.close();
//		fis.close();
//		
//		return result;
//	}
	
	/**
	 * This method is used to encrypt password in sha1
	 * @param password 	Password to hash
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
	
//	public static void startGetAuthors(final File f) {
//		currThread = new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
//				Scanner sc;
//				try {
//					sc = new Scanner(f, "UTF-8");
//					while (sc.hasNext()) {
//						String l = sc.nextLine();
//						if (l.startsWith("/type/author")) {
//							String s[] = l.split("\t");		
//							try {
//								listAuthors.put(s[1], s[4]);
//							} catch (Exception e) {
//								System.out.println(l);
//							}
//							if (listAuthors.size()%1000==0)
//								System.out.println("Authors: "+listAuthors.size());
//						}
//					}
//					sc.close();
//				} catch (FileNotFoundException e) {}
//			}
//		});
//		currThread.start();
//	}
}


















