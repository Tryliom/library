package me.alexishaldy.classes;

import java.util.ArrayList;
import java.util.HashMap;

public class Library {
	private static Library instance;
	private String name;
	private HashMap<Integer, Book> bookList = new HashMap<Integer, Book>();
	private ArrayList<User> userList = new ArrayList<>();
	private int actualCount;
	
	public static Library getLibrary() {
		if (instance==null)
			instance = new Library("Library");
		return instance;
	}
	
	private Library(String name) {
		this.setName(name);
		actualCount=0;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String listBook(SortType... type) {
		if (type.length>0) {
			String s = "Book list sorted by "+type.toString()+":";
			if (type.equals(SortType.Number)) {
				int i = 1;
				for (Book b : bookList.values()) {
						s+="\nN°"+i+":\nTitle: "+b.getTitle()+"\nAuthor: "+b.getAuthor()+"\nYear: "+b.getDate();
						i++;
				}		
			}	
			//TODO
			if (type.equals(SortType.Year)) {				
				int i = 1;
				for (Book b : bookList.values()) {
						s+="\nN°"+i+":\nTitle: "+b.getTitle()+"\nAuthor: "+b.getAuthor()+"\nYear: "+b.getDate();
						i++;
				}		
			}
			return s;
		} else {
			String s = "Book list:";
			int i = 1;
			for (Book b : bookList.values()) {
					s+="\nN°"+i+":\nTitle: "+b.getTitle()+"\nAuthor: "+b.getAuthor()+"\nYear: "+b.getDate();
					i++;
			}		
			return s;
		}
	}

	public void addBook(Book book) {
		for (Book b : bookList.values()) {
			if (b.getTitle().equals(book.getTitle()) && b.getAuthor().equals(book.getAuthor()) && b.getDate()==book.getDate()) {
				bookList.get(b).addNumber();
				return;
			}
		}
		this.bookList.put(this.bookList.size(), book);
	}
	/*
	 * If not id specified the last book created is deleted
	*/
	public void removeBook(String... title) {
		if (title.length>0)
			for (int i=0;i<title.length;i++)
				for (Book b : bookList.values()) {
					if (b.getTitle().equalsIgnoreCase(title[i]))
						bookList.remove(b);
				}
		else
			this.bookList.remove(this.bookList.get(actualCount));
	}
	
	public void addUser(User u) {
		userList.add(u);
	}

	public ArrayList<User> getUserList() {
		return userList;
	}
}
