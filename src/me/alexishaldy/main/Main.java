package me.alexishaldy.main;

import me.alexishaldy.classes.Book;
import me.alexishaldy.classes.Library;
import me.alexishaldy.classes.User;

public class Main {
	private static int displayTot=0; 
	public static void main(String[] args) {		
		Library lib = Library.getLibrary();
		lib.addBook(new Book("Hello world", "John", 1999));
		lib.addBook(new Book("Hello worlds", "Johna", 1995));
		display(lib.listBook());
		
		lib.removeBook("Hello worlds");
		display(lib.listBook());
		
		lib.addBook(new Book("Hello worlds", "Johna", 1995));
		lib.removeBook();
		display(lib.listBook());
		
		lib.addBook(new Book("April fools", "Marie", 1888));
		lib.addBook(new Book("The Java", "Paul", 2015));
		// Finir le reste avant de commencer le sortedBy
		display(lib.listBook());
		lib.addUser(new User("Alexis", "Haldy"));
	}
	
	
	public static void display(String s) {
		displayTot++;
		System.out.println("Display "+displayTot+":\n"+s+"\n\n");
	}
}
