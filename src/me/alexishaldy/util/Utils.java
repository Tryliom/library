package me.alexishaldy.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

public class Utils {
	
	public final static String SEP = System.getProperty("file.separator");
	public static final String DB_CONF = "conf" + SEP + "db.properties";
	public static final String REST_CONF = "conf" + SEP + "rest.properties";
	public static final String SWAGGER_FILE = "conf" + SEP + "swagger.json";
	
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
	
	public static void displayHelp(String...s) {
		if (s.length>0) {
			if (s[0].equalsIgnoreCase("mkuser") || s[0].equalsIgnoreCase("mku")) {
				display("mkuser : Entame les étapes de création d'un utilisateur");
				display("mkuser <username> <name> <lastname> <email> <phone number> : Créé un ou plusieurs utilisateurs dans la librairie");
			}
			if (s[0].equalsIgnoreCase("mkbook") || s[0].equalsIgnoreCase("mkb")) {
				display("mkbook : Entame les étapes de création d'un livre");
				display("mkbook <title> <author> <year> <desc> <num edition> <editional> : Créé un ou plusieurs livres dans la librairie");
			}
			if (s[0].equalsIgnoreCase("rmuser") || s[0].equalsIgnoreCase("rmu")) {
				display("rmuser : Entame les étapes de destruction d'un utilisateur");
				display("rmuser <Type [id:username]> <arg> : Suprimme un ou plusieurs utilisateurs dans la librairie");
			}
			if (s[0].equalsIgnoreCase("rmbook") || s[0].equalsIgnoreCase("rmb")) {
				display("rmbook : Entame les étapes de destruction d'un livre");
				display("rmbook <Type [title:author:year:title_author:title_author_numedition:desc]> <arg> : Suprimme un ou plusieurs livres dans la librairie");
			}
			if (s[0].equalsIgnoreCase("edituser") || s[0].equalsIgnoreCase("eu")) {
				display("edituser : Entame les étapes de modification d'un utilisateur");
				display("edituser <Type [id:username]> <arg> <new mail> <new tel> : Modifie un ou plusieurs utilisateurs dans la librairie. Laisser vide les '<new *>' si vous voulez laisser comme de base");
			}
			if (s[0].equalsIgnoreCase("displayuser") || s[0].equalsIgnoreCase("dpu")) {
				display("displayuser : Affiche tous les utilisateurs");
				display("displayuser <name:lastname:id> : Affiche seulement les utilisateurs avec le critère indiqué");
			}
			if (s[0].equalsIgnoreCase("displaybook") || s[0].equalsIgnoreCase("dpb")) {
				display("displaybook : Affiche tous les livres");
				display("displaybook <Type [id:title:author:year]> <arg> : Affiche seulement les livres avec le critère indiqué");
			}
			if (s[0].equalsIgnoreCase("takebook") || s[0].equalsIgnoreCase("tb")) {
				display("takebook : Suit les étapes d'obtention d'un livre");
				display("takebook <Type [id:username]> <arg user> <Type [title:author:year:title_author:title_author_numedition:desc]> <arg book> : Prend un livre recherché");
			}			
			if (s[0].equalsIgnoreCase("returnbook") || s[0].equalsIgnoreCase("rb")) {
				display("returnbook : Suit les étapes de retour d'un livre");
				display("returnbook <Type [id:username]> <arg user> <Type [title:author:year:title_author:title_author_numedition:desc]> <arg book> : Retourne le livre recherché");
			}
			if (s[0].equalsIgnoreCase("help") || s[0].equalsIgnoreCase("h")) {
				display("help : Affiche l'aide");
				display("help <commande> : Affiche l'aide détaillée d'une commande");
			}
		} else {
			display("Commandes Library:");
			display("help (h)");
			display("mkuser (mku)");
			display("mkbook (mkb)");
			display("rmuser (rmu)");
			display("rmbook (rmb)");
			display("edituser (eu)");
			display("takebook (tb)");
			display("returnbook (rb)");
			display("displayuser (dpu)");
			display("displaybook (dpb)");
			display("Taper 'help <commande>' pour plus de détails");
		}
		display("\n");
	}
	
	public static void display(String s) {
		System.out.println(s);
	}
	
	public static boolean isInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public static int getInt(String i) {
		try {
			int result = Integer.parseInt(i);
			return result;
		} catch (Exception e) {
			return -1;
		}
	}
	
	public static boolean containString(String s[], String match) {
		for (int i = 0;i<s.length;i++) {
			if (s[i].equals(match))
				return true;
		}
		return false;
	}
	
	public static String encryptPassword(String password)
	{
	    String sha1 = "";
	    try
	    {
	        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
	        crypt.reset();
	        crypt.update(password.getBytes("UTF-8"));
	        sha1 = byteToHex(crypt.digest());
	    }
	    catch(NoSuchAlgorithmException e)
	    {
	        e.printStackTrace();
	    }
	    catch(UnsupportedEncodingException e)
	    {
	        e.printStackTrace();
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
}


















