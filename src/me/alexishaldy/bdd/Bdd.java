package me.alexishaldy.bdd;




import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mysql.jdbc.Statement;

import me.alexishaldy.classes.Library;
import me.alexishaldy.enumerator.Reason;
import me.alexishaldy.enumerator.SortType;
import me.alexishaldy.util.Utils;

public class Bdd {
	Connection co;
	/**
	 * Send a command to the BDD
	 * @param reason What do you will do
	 * @param vector If args in the command, set they here
	 * 
	 */
	public String sendCmd(Reason reason, String...s) {
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
				    Utils.display("Librairie connectée !");
				} else {
					Utils.display("Erreur: Librairie inexistante");
				}			
				
			} catch (Exception e) {
				Utils.display("Erreur colib: "+e.getMessage());
			}
			break;
			
		case adduser:
			// Args: username, name, lastname, email, tel, lib id
			try {
				String sql = "INSERT INTO User(name, lastname, username, email, tel, library_id) VALUES ('"+s[0]+"', '"+s[1]+"', '"+s[2]+"', '"+s[3]+"', '"+s[4]+"', "+s[5]+")";
				PreparedStatement stat = co.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int i = stat.executeUpdate();
				if (i!=0) {
					Utils.display("Utilisateur créé !");
				} else {
					Utils.display("Erreur à la création de l'utilisateur");
				}
				
			} catch (Exception e) {
				Utils.display("Erreur adduser: "+e.getMessage());
			}
			break;
			
		case addbook:
			// Args: username, name, lastname, email, tel, lib id
			try {
				String sql = "INSERT INTO Book(title, author, date, description, edition, editeur, library_id) VALUES ('"+s[0]+"', '"+s[1]+"', "+s[2]+", '"+s[3]+"', "+s[4]+", '"+s[5]+"', "+s[6]+")";
				PreparedStatement stat = co.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int i = stat.executeUpdate();
				if (i!=0) {
					Utils.display("Livre créé !");
				} else {
					Utils.display("Erreur à la création du livre");
				}
				
			} catch (Exception e) {
				Utils.display("Erreur addbook: "+e.getMessage());
			}
			break;
			
		case searchuser:
			// Args: username
			try {
				String sql = "SELECT id FROM User WHERE username = '"+s[0]+"'"+" AND library_id = "+Library.getLibrary().getId();
				PreparedStatement stat = co.prepareStatement(sql);
				ResultSet rs = stat.executeQuery();;
				long id = 0;
				String a = "";
				if (rs != null && rs.next()) {
				    id = rs.getLong(1);
				    return ""+id;
				} else {
					return "-1";
				}
			} catch (Exception e) {
				Utils.display("Erreur searchuser: "+e.getMessage());
			}
			break;
			
		case rmuser:
			// Args: id
			try {
				String sql = "DELETE FROM User WHERE id = "+s[0]+" AND library_id = "+Library.getLibrary().getId();
				PreparedStatement stat = co.prepareStatement(sql);
				stat.executeUpdate();
			} catch (Exception e) {
				Utils.display("Erreur rmuser: "+e.getMessage());
			}
			break;
			
		case rmbook:
			// Args: id
			try {
				String sql = "DELETE FROM Book WHERE id = "+s[0]+" AND library_id = "+Library.getLibrary().getId();
				PreparedStatement stat = co.prepareStatement(sql);
				stat.executeUpdate();
			} catch (Exception e) {
				Utils.display("Erreur rmbook: "+e.getMessage());
			}
			break;
			
		case searchbook:
			// Args: id
			try {
				String sql = "";
				switch (SortType.valueOf(s[0])) {
				case Number:
					break;
				case author:
					sql = "SELECT id FROM Book WHERE author = '"+s[1]+"'"+" AND library_id = "+Library.getLibrary().getId();
					break;
				case desc:
					sql = "SELECT id FROM Book WHERE description = '"+s[1]+"'"+" AND library_id = "+Library.getLibrary().getId();
					break;
				case title:
					sql = "SELECT id FROM Book WHERE title = '"+s[1]+"'"+" AND library_id = "+Library.getLibrary().getId();
					break;
				case title_author:
					sql = "SELECT id FROM Book WHERE title = '"+s[1]+"' AND author = '"+s[2]+"'"+" AND library_id = "+Library.getLibrary().getId();
					break;
				case title_author_numedition:
					sql = "SELECT id FROM Book WHERE title = '"+s[1]+"' AND author = '"+s[2]+"' AND edition = "+s[3]+" AND library_id = "+Library.getLibrary().getId();
					break;
				case year:
					sql = "SELECT id FROM Book WHERE date = "+s[1]+" AND library_id = "+Library.getLibrary().getId();
					break;				
				}
				PreparedStatement stat = co.prepareStatement(sql);
				ResultSet rs = stat.executeQuery();;
				long id = 0;
				String a = "";
				if (rs != null && rs.next()) {
				    id = rs.getLong(1);
				    return ""+id;
				} else {
					return "-1";
				}
			} catch (Exception e) {
				Utils.display("Erreur searchbook: "+e.getMessage());			
			}
			break;
		case edituser:
			// Args: id, email, tel
			try {
				String sql = "UPDATE User SET email = '"+s[1]+"', tel = '"+s[2]+"' WHERE id = "+s[0]+" AND library_id = "+Library.getLibrary().getId();
				PreparedStatement stat = co.prepareStatement(sql);
				ResultSet rs = stat.executeQuery();
			} catch (Exception e) {
				Utils.display("Erreur edituser: "+e.getMessage());
			}
			break;
			
		case listuser:
			try {
				String sql = "SELECT username, name, lastname, email, tel FROM user WHERE library_id = "+Library.getLibrary().getId();
				PreparedStatement stat = co.prepareStatement(sql);
				ResultSet rs = stat.executeQuery();;
				String a = "";
				while (rs != null && rs.next()) {
				    a+=rs.getString(1)+"\t";
				    a+=rs.getString(2)+"\t";
				    a+=rs.getString(3)+"\t";
				    a+=rs.getString(4)+"\t";
				    a+=rs.getString(5)+"\t";
				    a+="\n";
				}
				return a;
			} catch (Exception e) {
				Utils.display("Erreur listuser: "+e.getMessage());
			}
			break;
			
		case listbook:
			try {
				String sql = "SELECT title, author, date, description, edition, editeur FROM book WHERE user_id IS NULL AND library_id = "+Library.getLibrary().getId();
				PreparedStatement stat = co.prepareStatement(sql);
				ResultSet rs = stat.executeQuery();;
				String a = "";
				while (rs != null && rs.next()) {
				    a+=rs.getString(1)+"\t";
				    a+=rs.getString(2)+"\t";
				    a+=rs.getInt(3)+"\t";
				    a+=rs.getString(4)+"\t";
				    a+=rs.getInt(5)+"\t";
				    a+=rs.getString(6)+"\t";
				    a+="\n";
				}
				return a;
			} catch (Exception e) {
				Utils.display("Erreur listbook: "+e.getMessage());
			}
			break;
			
		case listbookyear:
			try {
				String sql = "SELECT title, author, date, description, edition, editeur FROM book ORDER BY date WHERE user_id IS NULL AND library_id = "+Library.getLibrary().getId();
				PreparedStatement stat = co.prepareStatement(sql);
				ResultSet rs = stat.executeQuery();;
				String a = "";
				while (rs != null && rs.next()) {
				    a+=rs.getString(1)+"\t";
				    a+=rs.getString(2)+"\t";
				    a+=rs.getInt(3)+"\t";
				    a+=rs.getString(4)+"\t";
				    a+=rs.getInt(5)+"\t";
				    a+=rs.getString(6)+"\t";
				    a+="\n";
				}
				return a;
			} catch (Exception e) {
				Utils.display("Erreur listbookyear: "+e.getMessage());
			}
			break;
			
		case listrenter:
			try {
				String sql = "SELECT title, author, date, description, edition, editeur, user_id, id FROM book WHERE user_id IS NOT NULL AND library_id = "+Library.getLibrary().getId();
				PreparedStatement stat = co.prepareStatement(sql);
				ResultSet rs = stat.executeQuery();
				String a = "";
				while (rs != null && rs.next()) {
				    a+=rs.getString(1)+"\t";
				    a+=rs.getString(2)+"\t";
				    a+=rs.getInt(3)+"\t";
				    a+=rs.getString(4)+"\t";
				    a+=rs.getInt(5)+"\t";
				    a+=rs.getString(6)+"\t";
				    String sql2 = "SELECT taken_date, max_return_date FROM renter WHERE user_id = "+rs.getLong(7)+" AND book_id = "+rs.getLong(8)+" AND library_id = "+Library.getLibrary().getId();
					PreparedStatement stat2 = co.prepareStatement(sql);
					ResultSet rs2 = stat.executeQuery();
					if (rs2 != null && rs2.next()) {
					    a+=rs2.getDate(1)+"\t";
					    a+=rs2.getDate(2)+"\t";
					}
				    a+="\n";
				}
				return a;
			} catch (Exception e) {
				Utils.display("Erreur listbookyear: "+e.getMessage());
			}
			break;
			
		case takebook:
			// Args: username, name, lastname, email, tel, lib id
			try {
				String sql = "INSERT INTO Renter(book_id, user_id, taken_date, max_return_date, library_id) VALUES ("+s[0]+", "+s[1]+", NOW(), NOW()+"+(3600000*48)+", "+Library.getLibrary().getId()+")";
				PreparedStatement stat = co.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int i = stat.executeUpdate();
				if (i!=0) {
					Utils.display("Le livre a été pris");
				} else {
					Utils.display("Erreur lors de la résérvation du livre");
				}
				
			} catch (Exception e) {
				Utils.display("Erreur takebook: "+e.getMessage());
			}
			break;
			
		default:
			break;
		
		
		}
		return "";
	}
}
