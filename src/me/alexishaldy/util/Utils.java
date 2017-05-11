package me.alexishaldy.util;

public class Utils {
	
	public static void displayHelp(String...s) {
		if (s.length>0) {
			if (s[0].equalsIgnoreCase("mkuser") || s[0].equalsIgnoreCase("mku")) {
				display("mkuser : Entame les �tapes de cr�ation d'un utilisateur");
				display("mkuser <name> <lastname> <name2> ... : Cr�� un ou plusieurs utilisateurs dans la librairie");
			}
			if (s[0].equalsIgnoreCase("mkbook") || s[0].equalsIgnoreCase("mkb")) {
				display("mkbook : Entame les �tapes de cr�ation d'un livre");
				display("mkbook <title> <author> <year> <title2> ... : Cr�� un ou plusieurs livres dans la librairie");
				display("mkbook <title> <author> <year> <userid> <title2> ... : Cr�� un ou plusieurs livres qui appartiennent d�j� � un utilisateur dans la librairie");
			}
			if (s[0].equalsIgnoreCase("rmuser") || s[0].equalsIgnoreCase("rmu")) {
				display("rmuser : Entame les �tapes de destruction d'un utilisateur");
				display("rmuser <id:name:lastname> ... : Suprimme un ou plusieurs utilisateurs dans la librairie");
			}
			if (s[0].equalsIgnoreCase("rmbook") || s[0].equalsIgnoreCase("rmb")) {
				display("rmbook : Entame les �tapes de destruction d'un livre");
				display("rmbook <Type id:title:author:year> <arg> ... : Suprimme un ou plusieurs livres dans la librairie");
			}
			if (s[0].equalsIgnoreCase("editbook") || s[0].equalsIgnoreCase("eb")) {
				display("editbook : Entame les �tapes de modification d'un livre");
				display("editbook <Type id:title:author:year> <arg> <new title> <new author> <new year> ... : Modifie un ou plusieurs livres dans la librairie. Laisser vide les '<new *>' si vous voulez laisser comme de base");
			}
			if (s[0].equalsIgnoreCase("edituser") || s[0].equalsIgnoreCase("eu")) {
				display("edituser : Entame les �tapes de modification d'un utilisateur");
				display("edituser <id:name:lastname> <new name> <new lastname> ... : Modifie un ou plusieurs utilisateurs dans la librairie. Laisser vide les '<new *>' si vous voulez laisser comme de base");
			}
			if (s[0].equalsIgnoreCase("displayuser") || s[0].equalsIgnoreCase("dpu")) {
				display("displayuser : Affiche tous les utilisateurs");
				display("displayuser <name:lastname:id> : Affiche seulement les utilisateurs avec le crit�re indiqu�");
			}
			if (s[0].equalsIgnoreCase("displaybook") || s[0].equalsIgnoreCase("dpb")) {
				display("displaybook : Affiche tous les livres");
				display("displaybook <Type id:title:author:year> <arg> : Affiche seulement les livres avec le crit�re indiqu�");
			}
			if (s[0].equalsIgnoreCase("help") || s[0].equalsIgnoreCase("h")) {
				display("help : Affiche l'aide");
				display("help <commande> : Affiche l'aide d�taill�e d'une commande");
			}
		} else {
			display("Commandes Library:");
			display("help (h)");
			display("mkuser (mku)");
			display("mkbook (mkb)");
			display("rmuser (rmu)");
			display("rmbook (rmb)");
			display("editbook (eb)");
			display("edituser (eu)");
			display("displayuser (dpu)");
			display("displaybook (dpb)");
			display("Taper 'help <commande>' pour plus de d�tails");
		}
		display("\n");
	}
	
	public static void display(String s) {
		System.out.println(s);
	}
}
