package me.alexishaldy.bdd;




import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mysql.jdbc.Statement;

import me.alexishaldy.classes.Library;
import me.alexishaldy.enumerator.Reason;
import me.alexishaldy.util.Utils;

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
			co = DBConnector.getConnect();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		switch (reason) {
		case newlib:
			// Args: name, adresse
			try {
				String sql = "INSERT INTO Library(name, adress) VALUES ('"+s[0]+"', '"+s[1]+"')";
				PreparedStatement stat = co.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int i = stat.executeUpdate();
				if (i!=0) {
					ResultSet rs = stat.getGeneratedKeys();
					if (rs != null && rs.next()) {
					    long key = rs.getLong(1);
					    Library.initLibrary(s[0], s[1], (int) key);
					}
					Utils.display("Librairie crée et connectée !");
				} else {
					Utils.display("Erreur à la création de la base");
				}
				
			} catch (Exception e) {
				Utils.display("Erreur newlib: "+e.getMessage());
			}
			break;
		case colib:
			// Args: name
			try {
				String sql = "SELECT id, adress FROM Library WHERE name = '"+s[0]+"'";
				PreparedStatement stat = co.prepareStatement(sql);
				ResultSet rs = stat.executeQuery();;
				long id = 0;
				String a = "";
				if (rs != null && rs.next()) {
				    id = rs.getLong(1);
				    a = rs.getString(2);
				    Library.initLibrary(s[0], a, (int) id);
				}
				Utils.display("Librairie connectée !");			
				
			} catch (Exception e) {
				Utils.display("Erreur colib: "+e.getMessage());
			}
			break;
			
			
			
		default:
			break;
		
		
		}
	}
}
