package me.alexishaldy.main;

import me.alexishaldy.classes.Book;
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
			if (a[0].equalsIgnoreCase("mkbook") || a[0].equalsIgnoreCase("mkb")) {
				if (a.length>1) {
					String n1="";
					String n2 = "";
					int n3=-1;
					int count = 0;
					for (int i=1;i<a.length;i++) {
						if (i%3==1)
							n1=a[i];
						else if (i%3==2)
							n2=a[i];
						else if (Utils.isInt(a[i])) {
							n3=Integer.parseInt(a[i]);
						} else {
							break;
						}
							
						if (!n1.isEmpty() && !n2.isEmpty() && n3>-1) {
							count++;
							lib.addBook(new Book(n1, n2, n3));
							Utils.display("Livre "+count+" créé !");
							n1="";
							n2="";
							n3=-1;
						}
					}
				} else {
					Utils.display("Création en cours d'un nouveau livre\nSi vous voulez annuler taper '-close'");
					String n1 = Reader.readString("Donnez lui un titre\n");
					if (!n1.equalsIgnoreCase("-exit")) {
						String n2 = Reader.readString("Donnez lui un auteur qui l'a écrit\n");
						if (!n2.equalsIgnoreCase("-exit")) {
							int year = Reader.readInt("Donnez lui une année de création\n");
							int id = Reader.readInt("Donnez lui un id d'user si vous voulez que ce livre lui appartienne dès sa création ou mettez -1 pour ignorer cette option\n");
							if (id<=-1) {
								lib.addBook(new Book(n1, n2, year));
								Utils.display("Livre créé !");
							} else {
								lib.addBook(new Book(n1, n2, year, lib.getUserById(id)));
								Utils.display("Livre créé !");
							}
						}
					}
						
				}
			}
			
			
		}
		Utils.display("Fermeture de la librairie...");
		Reader.scan.close();
	}
}
