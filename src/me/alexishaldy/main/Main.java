package me.alexishaldy.main;

import java.sql.Connection;

import me.alexishaldy.bdd.Bdd;
import me.alexishaldy.bdd.DBConnector;
import me.alexishaldy.classes.Library;
import me.alexishaldy.enumerator.Reason;
import me.alexishaldy.util.CmdManager;
import me.alexishaldy.util.Reader;
import me.alexishaldy.util.Utils;

public class Main {

	public static void main(String[] args) {		
		Library lib = null;
		try {
			Connection db = DBConnector.getConnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String actualCmd = "";
		new Bdd().sendCmd(Reason.colib, "nagra");
		boolean b = false;
		if (b) {
			String resp = Reader.readBoolean("new exist", "Nouvelle librairie ou déjà existante ? (new/exist)\n");
			if (resp.equalsIgnoreCase("new")) {
				String name = Reader.readString("Donnez son nom\n");
				String adress = Reader.readString("Donnez son adresse\n");
				new Bdd().sendCmd(Reason.newlib, (name+"§"+adress).split("§"));
			} else {
				String name = Reader.readString("Donnez son nom pour s'y connecter\n");
				new Bdd().sendCmd(Reason.colib, name);
			}
		}
		lib = Library.getLibrary();
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
