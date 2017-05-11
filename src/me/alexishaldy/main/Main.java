package me.alexishaldy.main;

import me.alexishaldy.classes.Library;
import me.alexishaldy.classes.User;
import me.alexishaldy.util.Reader;
import me.alexishaldy.util.Utils;

public class Main {

	public static void main(String[] args) {		
		Library lib = Library.getLibrary();
		String actualCmd = "";
		while (!actualCmd.equalsIgnoreCase("exit")) {
			actualCmd = Reader.readString();
			String a[] = actualCmd.split(" ");
			// Affichages des commandes
			if (a[0].equalsIgnoreCase("help") || a[0].equalsIgnoreCase("h")) {
				if (a.length>1) {
					Utils.displayHelp(a[1]);
				} else
					Utils.displayHelp();
			}
			
			if (a[0].equalsIgnoreCase("mkuser") || a[0].equalsIgnoreCase("mku")) {
				if (a.length>1) {
					String n="";
					String ln = "";
					int count = 0;
					for (int i=1;i<a.length;i++) {
						if (i%2==1)
							n=a[i];
						else
							ln=a[i];
						if (!n.isEmpty() && !ln.isEmpty()) {
							count++;
							lib.addUser(new User(n, ln));
							Utils.display("Utilisateur "+count+" créé !");
							n="";
							ln="";
						}
					}
				} else {
					Utils.display("Création en cours d'un nouvel utilisateur\nSi vous voulez annuler taper '-close'");
					String name = Reader.readString("Donnez lui un prénom\n");
					if (!name.equalsIgnoreCase("-exit")) {
						String lastname = Reader.readString("Donnez lui un nom\n");
						if (!lastname.equalsIgnoreCase("-exit")) {
							lib.addUser(new User(name, lastname));
							Utils.display("Utilisateur créé !");
						}
					}
						
				}
			}
			
			
		}
		Utils.display("Fermeture de la librairie...");
		Reader.scan.close();
	}
}
