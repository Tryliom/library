package me.alexishaldy.main;

import me.alexishaldy.classes.Library;
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
				
			}
		}
	}
}
