package me.alexishaldy.main;

import me.alexishaldy.classes.Book;
import me.alexishaldy.classes.Library;
import me.alexishaldy.classes.SortType;
import me.alexishaldy.classes.User;

public class Main {
	private static int displayTot=0; 
	public static void main(String[] args) {		
		Library lib = Library.getLibrary();
		lib.addUser(new User("Alexis", "Haldy"));
		lib.addUser(new User("John", "Hulu"));
		lib.addUser(new User("Paul", "Jira"));
		lib.addBook(new Book("Hello world", "John", 1999));
		lib.addBook(new Book("Hello worlds", "Johna", 1995, lib.getUserByNameOrLastname("Paul")));
		display(lib.listBook(SortType.Number));			
		
		lib.removeBook("Hello worlds");
		display(lib.listBook());
		
		lib.addBook(new Book("Hello worlds", "Johna", 1997));
		lib.removeBook();
		display(lib.listBook());
		
		lib.addBook(new Book("April fools", "Marie", 1888, lib.getUserByNameOrLastname("Alexis")));
		lib.addBook(new Book("The Java", "Paul", 2015));
		lib.addBook(new Book("All of Kali", "Jean-Pierre", 2015));
		lib.addBook(new Book("Guisness World Record 2015", "Tom Card", 2015));
		// Finir le reste avant de commencer le sortedBy
		display(lib.listUserAndBooks());
		display(lib.listBook());
		display(lib.getUserByNameOrLastname("Alexis").returnBook("April fools"));
		display(lib.listUserAndBooks());
		display(lib.getUserByNameOrLastname("Alexis").takenBook("Paul"));
		display(lib.listUserAndBooks());
		display(lib.getUserByNameOrLastname("Alexis").takenBook("2015"));
		display(lib.listUserAndBooks());
	}
	
	
	public static void display(String s) {
		displayTot++;
		System.out.println("Display "+displayTot+":\n"+s+"\n\n");
	}
}
