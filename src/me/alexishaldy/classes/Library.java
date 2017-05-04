package me.alexishaldy.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Library {
	private static Library instance;
	private String name;
	private HashMap<Integer, Book> bookList = new HashMap<Integer, Book>();
	private ArrayList<User> userList = new ArrayList<>();
	private int lastId;
	
	public static Library getLibrary() {
		if (instance==null)
			instance = new Library("Library");
		return instance;
	}
	
	private Library(String name) {
		this.setName(name);
		lastId = 0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

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
			//TODO
			if (type[0].equals(SortType.Year)) {
				List<Book> tmpBook = new ArrayList<Book>(bookList.values());
				Collections.sort(tmpBook, new Comparator<Book>() {
					@Override
					public int compare(Book a, Book b) {
						//TODO: Compare
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

	public void addBook(Book book) {
		book.generateId();
		this.bookList.put(book.getId(), book);
		lastId=book.getId();
	}
	/*
	 * If not title specified the last book created is deleted
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
	
	public User getUserByNameOrLastname(String name) {
		for (int i=0;i<userList.size();i++) {
			if (userList.get(i).getName().equals(name) || userList.get(i).getLastName().equals(name)) {
				return userList.get(i);
			}
		}
		
		return null;
	}
	
	public String listUserAndBooks() {
		String s = "User list:";
		for (int i=0;i<userList.size();i++) {
			s+="\n\nUser N°"+userList.get(i).getId()+":\n";
			// Add info of user
			s+="Name: "+userList.get(i).getName()+"\nLast Name: "+userList.get(i).getLastName();
			for (Book b : bookList.values()) {
				if (b.isTaken() && b.getOwner().equals(userList.get(i))) {
					s+="\n\tBook taken:\n\t\tTitle: "+b.getTitle()+"\n\t\tAuthor: "+b.getAuthor()+"\n\t\tYear: "+b.getDate();
				}
			}
		}
		
		return s;
	}

	public HashMap<Integer, Book> getBookList() {
		return bookList;
	}
}
