package me.alexishaldy.main;

import me.alexishaldy.classes.Library;
import me.alexishaldy.util.CmdManager;
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
			new CmdManager().sendCommand(a);						
		}
		Utils.display("Fermeture de la librairie...");
		Reader.scan.close();
	}
}
