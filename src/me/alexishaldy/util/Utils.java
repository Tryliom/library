package me.alexishaldy.util;

public class Utils {
	
	public static void displayHelp(String...s) {
		if (s.length>0) {
			if (s[0].equalsIgnoreCase("mkuser") || s[0].equalsIgnoreCase("mku")) {
				display("mkuser : Entame les étapes de création d'un utilisateur");
				display("mkuser <username> <name> <lastname> <email> <phone number> <name2> ... : Créé un ou plusieurs utilisateurs dans la librairie");
			}
			if (s[0].equalsIgnoreCase("mkbook") || s[0].equalsIgnoreCase("mkb")) {
				display("mkbook : Entame les étapes de création d'un livre");
				display("mkbook <title> <author> <year> <num edition> <editional> <title2> ... : Créé un ou plusieurs livres dans la librairie");
			}
			if (s[0].equalsIgnoreCase("rmuser") || s[0].equalsIgnoreCase("rmu")) {
				display("rmuser : Entame les étapes de destruction d'un utilisateur");
				display("rmuser <Type [id:username]> <arg> ... : Suprimme un ou plusieurs utilisateurs dans la librairie");
			}
			if (s[0].equalsIgnoreCase("rmbook") || s[0].equalsIgnoreCase("rmb")) {
				display("rmbook : Entame les étapes de destruction d'un livre");
				display("rmbook <Type [title:author:year:title_author:title_author_numedition:desc]> <arg> ... : Suprimme un ou plusieurs livres dans la librairie");
			}
			if (s[0].equalsIgnoreCase("edituser") || s[0].equalsIgnoreCase("eu")) {
				display("edituser : Entame les étapes de modification d'un utilisateur");
				display("edituser <id:username> <new mail> <new tel> ... : Modifie un ou plusieurs utilisateurs dans la librairie. Laisser vide les '<new *>' si vous voulez laisser comme de base");
			}
			if (s[0].equalsIgnoreCase("displayuser") || s[0].equalsIgnoreCase("dpu")) {
				display("displayuser : Affiche tous les utilisateurs");
				display("displayuser <name:lastname:id> : Affiche seulement les utilisateurs avec le critère indiqué");
			}
			if (s[0].equalsIgnoreCase("displaybook") || s[0].equalsIgnoreCase("dpb")) {
				display("displaybook : Affiche tous les livres");
				display("displaybook <Type id:title:author:year> <arg> : Affiche seulement les livres avec le critère indiqué");
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
}


















