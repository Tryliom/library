package me.alexishaldy.bdd;

import java.sql.Connection;
import java.util.Vector;

import me.alexishaldy.enumerator.Reason;

public class Bdd {
	Connection co;
	/**
	 * Send a command to the BDD
	 * @param reason What do you will do
	 * @param vector If args in the command, set they here
	 * 
	 */
	public void sendCmd(Reason reason, String...s) {
		try {
			Connection co = DBConnector.getConnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		switch (reason) {
		case newlib:
			// Args: name, adresse
			String sql = "INSERT INTO Library(name, adress) VALUES ('"+s[0]+"'. '"+s[1]+"')";
			break;
		
		}
	}
}
