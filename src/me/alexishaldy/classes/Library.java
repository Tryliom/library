package me.alexishaldy.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Library {
	private static Library instance;
	private String name; // adresse CIF
	private String adress;
	private String CIF;
	private HashMap<String, Book> bookList = new HashMap<String, Book>();
	private ArrayList<User> userList = new ArrayList<>();
	private String lastId;
	
	/**
	 * @return Create un singleton of Library
	 * 
	*/
	public static Library getLibrary() {
		if (instance==null)
			instance = new Library("Library", "null", "null");
		return instance;
	}
	


	private Library(String name, String adress, String cIF) {
		this.name = name;
		this.adress = adress;
		lastId = "";
		CIF = cIF;
	}
	
	public static void initLibrary(String name, String adress, String cIF) {
		instance = new Library(name, adress, cIF);
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Blabla
	 * @param type Can choose a type of sorting for books
	 * 
	*/
	public String listBook(SortType...type) {
		if (type.length>0) {
			String s = "Book list sorted by "+type[0].name()+":";
			if (type[0].equals(SortType.Number)) {
				int i = 1;
				for (Book b : bookList.values()) {
						s+="\n\tN°"+i+":\n\tTitle: "+b.getTitle()+"\n\tAuthor: "+b.getAuthor()+"\n\tYear: "+b.getDate();
						i++;
				}		
			}	
			if (type[0].equals(SortType.Year)) {
				List<Book> tmpBook = new ArrayList<Book>(bookList.values());

			    Collections.sort(tmpBook, new Comparator<Book>() {

			        public int compare(Book o1, Book o2) {
			        	if (o1.getDate() == o2.getDate())
			        		return 0;
			        	if (o1.getDate() < o2.getDate())
			        		return -1;
			        	return 1;
			        }


			    });
			    int i = 1;
				for (Book b : tmpBook) {
						s+="\n\tN°"+i+":\n\tTitle: "+b.getTitle()+"\n\tAuthor: "+b.getAuthor()+"\n\tYear: "+b.getDate();
						i++;
				}	
			}
			return s;
		} else {
			String s = "Book list:";
			int i = 1;
			for (Book b : bookList.values()) {
					s+="\n\tN°"+i+":\n\tTitle: "+b.getTitle()+"\n\tAuthor: "+b.getAuthor()+"\n\tYear: "+b.getDate();
					i++;
			}		
			return s;
		}
	}

	/**
	 * @param book Add the book to the Library
	 * 
	*/
	public void addBook(Book book) {
		this.bookList.put(book.getId(), book);
		lastId=book.getId();
	}
	/**
	 * @param title If not title specified the last book created is deleted
	 * 
	*/
	public void removeBook(String... title) {
		if (title.length>0)
			for (int i=0;i<title.length;i++)
				for (Book b : bookList.values()) {
					if (b.getTitle().equalsIgnoreCase(title[i])) {
						bookList.remove(b.getId());
						return;
					}
				}
		else {
			bookList.remove(lastId);
		}
			
	}
	
	public void addUser(User u) {
		u.generateId();
		userList.add(u);
	}
	
	/**
	 * @param name Search by name or last name the user
	 * 
	*/
	public User getUserByUsername(String name) {
		for (int i=0;i<userList.size();i++) {
			if (userList.get(i).getUsername().equals(name)) {
				return userList.get(i);
			}
		}
		
		return null;
	}
	
	/**
	 * @param name Search by id
	 * 
	*/
	public User getUserById(String id) {
		for (int i=0;i<userList.size();i++) {
			if (userList.get(i).getIdentityId().equalsIgnoreCase(id)) {
				return userList.get(i);
			}
		}
		
		return null;
	}
	
	/**
	 * @param name Search by id
	 * 
	*/
	public boolean removeUser(User u) {
		if (userList.remove(u))
			return true;		
		return false;
	}
	
	/**
	 * @return Display User in String format and his books if he owned them
	 * 
	*/
	public String listUserAndBooks() {
		String s = "User list:";
		for (int i=0;i<userList.size();i++) {
			s+="\n\nUser "+userList.get(i).getIdentityId()+":\n";
			s+="Name: "+userList.get(i).getName()+"\nLast Name: "+userList.get(i).getLastName();
			for (Book b : bookList.values()) {
				if (b.getUserId()!=null && b.getUserId().equals(userList.get(i).getIdentityId())) {
					s+="\n\tBook taken:\n\t\tTitle: "+b.getTitle()+"\n\t\tAuthor: "+b.getAuthor()+"\n\t\tYear: "+b.getDate();
				}
			}
		}
		
		return s;
	}

	public HashMap<String, Book> getBookList() {
		return bookList;
	}
}
