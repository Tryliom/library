package me.alexishaldy.classes;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import me.alexishaldy.bdd.Bdd;
import me.alexishaldy.enumerator.Reason;
import me.alexishaldy.enumerator.SortType;
import me.alexishaldy.util.Utils;

@Path("/library")
public class Library {
	private static Library instance;
	private String name; // adresse CIF
	private String adress;
	private int id;
	private String lastId;
	
	/**
	 * @return Create un singleton of Library
	 * 
	*/
	public static Library getLibrary() {
		if (instance==null)
			instance = new Library("Library", "Adresse manquante", -1);
		return instance;
	}
	


	private Library(String name, String adress, int id) {
		this.name = name;
		this.adress = adress;
		this.id=id;
		lastId = "";
	}
	
	public static void initLibrary(String name, String adress, int id) {
		instance = new Library(name, adress, id);
	}



	public int getId() {
		return id;
	}

	@GET
	@Produces("text/plain")
	@Path("/adress")
	public String getAdress() {
		return this.adress;
	}

	@GET
	@Produces("text/plain")
	@Path("/name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * List book in renter list
	 * @param type Can choose a type of sorting for books
	 * 
	*/
	public String listRenterBook() {
		String s = "Renter list:\n";
			String a[] = new Bdd().sendCmd(Reason.listrenter).split("\n");
			for (int i=0;i<a.length;i++) {
				String r[] = a[i].split("\t");				
				if (r.length>1) {
					s+="N°"+(i+1)+":\nTitle: "+r[0]+"\tAuthor: "+r[1]+"\nYear: "+r[2]+"\tDescription: "+r[3]+"\nNuméro d'édition: "+r[4]+"\tÉditional: "+r[5]+"\n"+"Date d'emprunt: "+r[6]+"\tDate de retour: "+r[7]+"\nRetour du livre le "+r[8]+"\n\n";
				} else {
					s+="Aucun emprunts répértoriés";
				}
			}	
		
		return s;
	}
	
	/**
	 * List book
	 * @param type Can choose a type of sorting for books
	 * 
	*/
	public String listAllBook(SortType...type) {
		String s = "";
		if (type.length>0) {
			s = "Book list sorted by "+type[0].name()+":\n";
			if (type[0].equals(SortType.year)) {				
			    String a[] = new Bdd().sendCmd(Reason.listbookyear).split("\n");
				for (int i=0;i<a.length;i++) {
					String r[] = a[i].split("\t");
					s+="N°"+(i+1)+":\nTitle: "+r[0]+"\tAuthor: "+r[1]+"\nYear: "+r[2]+"\tDescription: "+r[3]+"\nNuméro d'édition: "+r[4]+"\tÉditional: "+r[5]+"\n\n";
				}
				return s;
			}			
		}
		if (s.isEmpty())
			s = "Book list:";
		String a[] = new Bdd().sendCmd(Reason.listbook).split("\n");
		for (int i=0;i<a.length;i++) {
			String r[] = a[i].split("\t");
			if (!a[i].isEmpty())
				s+="N°"+(i+1)+":\nTitle: "+r[0]+"\tAuthor: "+r[1]+"\nYear: "+r[2]+"\tDescription: "+r[3]+"\nNuméro d'édition: "+r[4]+"\tÉditional: "+r[5]+"\n\n";
		}			
		return s;
	}
	
	/**
	 * @param name Search by Username
	 * 
	*/
	public int getUserIdByUsername(String name) {
		return Integer.parseInt(new Bdd().sendCmd(Reason.searchuser, name));
	}
	
	/**
	 * @param user The user we will be delete
	 * 
	*/
	public void removeUser(int id) {
		new Bdd().sendCmd(Reason.rmuser, ""+id);
	}
	
	/**
	 * @return Display User in String format and his books if he owned them
	 * 
	*/
	public String listUserAndBooks() {
		String s[] = new Bdd().sendCmd(Reason.listuser).split("\n");
		String res = "~[Liste des utilisateurs]~\n";
		if (s.length==0) {
			Utils.display("Erreur: Aucuns utilisateurs");
		} else {
			for (int i=0;i<s.length;i++) {
				String r[] = s[i].split("\t");
				if (!s[i].isEmpty()) {
					res+="Username: "+r[0]+"\nName: "+r[1]+"\tLast Name: "+r[2]+"\nEmail: "+r[3]+"\tTel: "+r[4]+"\n\n";
					String a[] = new Bdd().sendCmd(Reason.listbookbyuser, r[5]).split("\n");
					for (int j=0;j<a.length;j++) {
						String t[] = a[j].split("\t");
						if (!a[j].isEmpty())
							res+="\tN°"+(j+1)+":\n\tTitle: "+t[0]+"\t\tAuthor: "+t[1]+"\n\tYear: "+t[2]+"\t\tDescription: "+t[3]+"\n\tNuméro d'édition: "+t[4]+"\t\tÉditional: "+t[5]+"\n\n";
					}
				}
			}
		}	
		
		return res;
	}
}
